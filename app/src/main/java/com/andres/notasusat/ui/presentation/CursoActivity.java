package com.andres.notasusat.ui.presentation;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.andres.notasusat.R;
import com.andres.notasusat.ui.business.Course;

import java.util.ArrayList;
import java.util.List;

public class CursoActivity extends AppCompatActivity {

    private RecyclerView recyclerViewCursoo;
    private RecyclerCourses adaptadorCurso;

    private AlertDialog.Builder dialogBuilder;
    private  AlertDialog alertDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curso);

        recyclerViewCursoo = (RecyclerView)findViewById(R.id.recyclerCourses);
        recyclerViewCursoo.setLayoutManager(new LinearLayoutManager( this));

        adaptadorCurso = new RecyclerCourses(getCursos());
        recyclerViewCursoo.setAdapter(adaptadorCurso);

    }

    public List<Course> getCursos() {
        List<Course> courses = new ArrayList<>();

        courses.add(new Course( 1, "97651248", "Curso 1", "Juan Perez", 5, 20,true ));
        courses.add(new Course( 2, "912548712", "Curso 2", "Juan Perez", 5, 10,true ));
        courses.add(new Course( 3, "987456521", "Curso 3", "Juan Perez", 5, 16,true ));
        return courses;
    }

    public void OpenNewCourse (View view){
        dialogBuilder = new AlertDialog.Builder(this);
        final View coursePopup = getLayoutInflater().inflate(R.layout.popup, null);
        dialogBuilder.setView(coursePopup);
        alertDialog = dialogBuilder.create();
        alertDialog.show();

    }
}