package com.andres.notasusat.ui.presentation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.andres.notasusat.R;
import com.andres.notasusat.ui.business.Activity;

import com.andres.notasusat.ui.data.DatabaseHelper;

import java.util.List;

public class RecyclerActivity extends RecyclerView.Adapter<RecyclerActivity.ViewHolder> implements View.OnClickListener {

    DatabaseHelper conn;
    private View.OnClickListener listener;

    @Override
    public void onClick(View v) {
        if(listener != null){
            listener.onClick(v);
        }
    }

    @NonNull
    @Override
    public RecyclerActivity.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_tipos_nota, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerActivity.ViewHolder holder, int position) {
        holder.descripcion.setText("DESCRIPCIÓN: " + actividadLista.get(position).getDescription());
        holder.peso.setText("PESO: "+ actividadLista.get(position).getWeight().toString());
    }
    @Override
    public int getItemCount() {
        return actividadLista.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView descripcion, peso;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            descripcion = (TextView)itemView.findViewById(R.id.lblDescriptionActivity);
            peso = (TextView)itemView.findViewById(R.id.lblWeightActivity);

        }
    }

    public List<Activity> actividadLista;

    public RecyclerActivity(List<Activity> actividadLista){
        this.actividadLista = actividadLista;
    }
}
