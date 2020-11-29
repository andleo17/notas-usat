package com.andres.notasusat.ui.presentation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.andres.notasusat.R;
import com.andres.notasusat.ui.business.Course;

import java.util.List;

public class RecyclerCourses extends RecyclerView.Adapter<RecyclerCourses.ViewHolder> {

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_cursos, parent,false );
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.nombre.setText(cursoLista.get(position).getName());
        holder.nota.setText(cursoLista.get(position).getGrade().toString());

    }

    @Override
    public int getItemCount() {
        return cursoLista.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView nombre, nota;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre = (TextView)itemView.findViewById(R.id.lblNameCourse);
            nota = (TextView)itemView.findViewById(R.id.lblGradeCourse);
        }
    }

    public List<Course> cursoLista;

    public RecyclerCourses(List<Course> cursoLista){
        this.cursoLista = cursoLista;
    }
}
