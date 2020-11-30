package com.andres.notasusat.ui.presentation;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.andres.notasusat.R;
import com.andres.notasusat.ui.business.Course;
import com.andres.notasusat.ui.data.DatabaseHelper;
import com.andres.notasusat.ui.data.Utilities;

import java.util.ArrayList;
import java.util.List;

public class CursoActivity extends AppCompatActivity {

    private RecyclerView recyclerViewCursoo;
    private RecyclerCourses adaptadorCurso;

    private AlertDialog.Builder dialogBuilder;
    private  AlertDialog alertDialog;

    private EditText name;
    private EditText teacher;
    private EditText credits;
    private EditText code;
    private EditText group;
    private ImageView saveButton;
    DatabaseHelper conn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curso);
        setCourses();
    }

    private  void setCourses(){
        recyclerViewCursoo = (RecyclerView)findViewById(R.id.recyclerCourses);
        recyclerViewCursoo.setLayoutManager(new LinearLayoutManager( this));

        adaptadorCurso = new RecyclerCourses(getCursos());
        recyclerViewCursoo.setAdapter(adaptadorCurso);
    }

    public List<Course> getCursos() {
        List<Course> courses = new ArrayList<>();
        try {
            DatabaseHelper conn = new DatabaseHelper(this, "Notas_USAT", null, 1);
            SQLiteDatabase db = conn.getReadableDatabase();
//            db.execSQL("INSERT INTO course (code , name , teacher, credits, grade, state) VALUES ('GAA1', 'Curso 01', 'Juan Perez', 3, 20, 1)");
            Course course = null;
            Cursor cursor = db.rawQuery("SELECT * FROM course", null );
//
            while (cursor.moveToNext()){
                Log.e("Apollo",cursor.getString(0) );
                course = new Course();
                course.setId(cursor.getInt(0));
                course.setCode(cursor.getString(1));
                course.setName(cursor.getString(2));
                course.setTeacher(cursor.getString(3));
                course.setCredits(cursor.getInt(4));
                course.setGrade(cursor.getInt(5));
                course.setState(cursor.getInt(6));
                courses.add(course);
            }
        }
        catch (Exception e){
            Log.e("Apollo", e.getMessage());
        }


//        courses.add(new Course( 1, "97651248", "Curso 1", "Juan Perez", 5, 20,true ));
//        courses.add(new Course( 2, "912548712", "Curso 2", "Juan Perez", 5, 10,true ));
//        courses.add(new Course( 3, "987456521", "Curso 3", "Juan Perez", 5, 16,true ));
        return courses;
    }

    public void OpenNewCourse (View view){
        dialogBuilder = new AlertDialog.Builder(this);
        final View coursePopup = getLayoutInflater().inflate(R.layout.popup, null);
        name = (EditText) coursePopup.findViewById(R.id.txtNameNewCourse);
        teacher = (EditText) coursePopup.findViewById(R.id.txtTeacherNewCourse);
        credits = (EditText) coursePopup.findViewById(R.id.txtCreditsNewCourse);
        group = (EditText) coursePopup.findViewById(R.id.txtGroupNewCourse);
        code = (EditText) coursePopup.findViewById(R.id.txtCodeNewCourse);
        saveButton = (ImageView) coursePopup.findViewById(R.id.saveButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCourse(
                        name.getText().toString(),
                        teacher.getText().toString(),
                        Integer.parseInt(credits.getText().toString()),
                        group.getText().toString(),
                        code.getText().toString()
                );
               setCourses();
            }
        });

        dialogBuilder.setView(coursePopup);
        alertDialog = dialogBuilder.create();
        alertDialog.show();

    }


    public void addCourse(String name, String teacher, Integer credits, String group, String code){
        DatabaseHelper conn = new DatabaseHelper(this, "Notas_USAT", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("code", code);
        values.put("name", name);
        values.put("teacher", teacher);
        values.put("credits", credits);
        values.put("grade", 0);
        db.insert("course", null,values);
        db.close();
    }
}