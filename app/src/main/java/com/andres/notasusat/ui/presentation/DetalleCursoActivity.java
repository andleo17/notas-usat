package com.andres.notasusat.ui.presentation;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.andres.notasusat.R;
import com.andres.notasusat.ui.business.Unity;
import com.andres.notasusat.ui.data.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class DetalleCursoActivity extends AppCompatActivity {

    private RecyclerView recyclerViewUnidad;
    private RecyclerUnity adaptadorUnidad;

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog alertDialog;

    private EditText description;
    private EditText weight;
    private ImageView saveButton;
    private Integer idCourse;
    private String nameCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_curso);
        Bundle myBundle = this.getIntent().getExtras();
        TextView lblNameCourse = findViewById(R.id.lblNameCourseDetail);
        if(myBundle != null){
            idCourse = myBundle.getInt("idCourse");
            nameCourse = myBundle.getString("nameCourse");
            lblNameCourse.setText(nameCourse);
        }
        setUnities();

    }

    private  void setUnities(){
        recyclerViewUnidad = (RecyclerView)findViewById(R.id.recyclerUnity);
        recyclerViewUnidad.setLayoutManager(new LinearLayoutManager( this));

        adaptadorUnidad = new RecyclerUnity(getUnities());
        recyclerViewUnidad.setAdapter(adaptadorUnidad);

        adaptadorUnidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public List<Unity> getUnities() {
        List<Unity> unityList = new ArrayList<>();
        try {
            DatabaseHelper conn = new DatabaseHelper(this, "Notas_USAT", null, 1);
            SQLiteDatabase db = conn.getReadableDatabase();
//            db.execSQL("INSERT INTO course (code , name , teacher, credits, grade, state) VALUES ('GAA1', 'Curso 01', 'Juan Perez', 3, 20, 1)");
            Unity unity = null;
            Cursor cursor = db.rawQuery("SELECT * FROM unity WHERE idCourse="+ idCourse, null );
//
            while (cursor.moveToNext()){
                Log.e("Unidad",cursor.getString(0) );
                unity = new Unity();
                unity.setId(cursor.getInt(0));
                unity.setDescription(cursor.getString(1));
                unity.setWeight(cursor.getFloat(2));
                unity.setGrade(cursor.getFloat(3));
                unityList.add(unity);
            }
        }
        catch (Exception e){
            Log.e("Apollo", e.getMessage());
        }

        return unityList;
    }


    public void OpenNewUnity (View view){
        dialogBuilder = new AlertDialog.Builder(this);
        final View coursePopup = getLayoutInflater().inflate(R.layout.popup_unidad, null);
        description = (EditText) coursePopup.findViewById(R.id.txtDescriptionNewUnity);
        weight = (EditText) coursePopup.findViewById(R.id.txtWeightNewUnity);
        saveButton = (ImageView) coursePopup.findViewById(R.id.saveButtonNewUnity);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUnity(
                        description.getText().toString(),
                        Float.parseFloat(weight.getText().toString())
                );
                setUnities();
            }
        });

        dialogBuilder.setView(coursePopup);
        alertDialog = dialogBuilder.create();
        alertDialog.show();

    }

    public void addUnity(String description, float weight){
        DatabaseHelper conn = new DatabaseHelper(this, "Notas_USAT", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("description", description);
        values.put("weight", weight);
        values.put("grade", 0);
        values.put("idCourse", idCourse);
        db.insert("unity", null,values);
        db.close();
    }
}