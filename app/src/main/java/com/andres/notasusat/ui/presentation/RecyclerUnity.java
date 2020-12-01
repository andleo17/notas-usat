package com.andres.notasusat.ui.presentation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.andres.notasusat.R;

import com.andres.notasusat.ui.business.Unity;
import com.andres.notasusat.ui.data.DatabaseHelper;

import java.util.List;

public class RecyclerUnity  extends RecyclerView.Adapter<RecyclerUnity.ViewHolder> implements View.OnClickListener{

    DatabaseHelper conn;
    private View.OnClickListener listener;

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
}
