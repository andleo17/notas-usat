package com.andres.notasusat.ui.presentation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.andres.notasusat.R;

import com.andres.notasusat.model.Unity;
import com.andres.notasusat.data.DatabaseHelper;

import java.util.List;

public class RecyclerUnity  extends RecyclerView.Adapter<RecyclerUnity.ViewHolder> implements View.OnClickListener{

    DatabaseHelper conn;
    private View.OnClickListener listener;
    private AlertDialog.Builder dialogBuilder;
    private  AlertDialog alertDialog;

    @Override
    public void onClick(View v) {
        if(listener != null){
            listener.onClick(v);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listado_unidades, parent,false );
        ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerUnity.ViewHolder holder, int position) {
        holder.descripcion.setText("DESCRIPCIÓN: " +unidadLista.get(position).getDescription());
        holder.peso.setText("PESO: "+unidadLista.get(position).getWeight().toString());
        holder.number.setText("UNIDAD " + (position + 1));
    }

    @Override
    public int getItemCount() {
        return unidadLista.size();
    }

    public  void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView descripcion, peso, number;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            descripcion = (TextView)itemView.findViewById(R.id.lblDescriptionUnity);
            peso = (TextView)itemView.findViewById(R.id.lblWeightUnity);
            number = (TextView)itemView.findViewById(R.id.lblNumberUnity);
        }
    }

    public List<Unity> unidadLista;

    public RecyclerUnity(List<Unity> unidadLista){
        this.unidadLista = unidadLista;
    }

//    public void OpenNewCourse (View view){
//        dialogBuilder = new AlertDialog.Builder(view.getContext());
//        final View activityPopup = inflate(R.layout.popup, null);
//        name = (EditText) activityPopup.findViewById(R.id.txtNameNewCourse);
//        teacher = (EditText) activityPopup.findViewById(R.id.txtTeacherNewCourse);
//        credits = (EditText) activityPopup.findViewById(R.id.txtCreditsNewCourse);
//        group = (EditText) activityPopup.findViewById(R.id.txtGroupNewCourse);
//        code = (EditText) activityPopup.findViewById(R.id.txtCodeNewCourse);
//        saveButton = (ImageView) activityPopup.findViewById(R.id.saveButtonNewUnity);
//
//        saveButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                addCourse(
//                        name.getText().toString(),
//                        teacher.getText().toString(),
//                        Integer.parseInt(credits.getText().toString()),
//                        group.getText().toString(),
//                        code.getText().toString()
//                );
//                setCourses();
//            }
//        });
//
//        dialogBuilder.setView(coursePopup);
//        alertDialog = dialogBuilder.create();
//        alertDialog.show();
//
//    }
}
