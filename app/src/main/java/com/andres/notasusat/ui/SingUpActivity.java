package com.andres.notasusat.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.andres.notasusat.R;
import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.example.SemestersQuery;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SingUpActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up);

        ApolloClient apolloClient = ApolloClient.builder().serverUrl("https://notas-gql.herokuapp.com/graphql/endpoint").build();
        apolloClient.query(new SemestersQuery()).enqueue(new ApolloCall.Callback<SemestersQuery.Data>() {
            @Override
            public void onResponse(@NotNull Response<SemestersQuery.Data> response) {


            }

            @Override
            public void onFailure(@NotNull ApolloException e) {

            }
        });
    }
    String[] strFrutas;
    List<ArrayList> listaFrutas;

    private void setSemesters(){
        Spinner  spSemesters = (Spinner) findViewById(R.id.spinnerSemester);
        spSemesters.setOnItemSelectedListener(this);

        strFrutas = new  String[] {"Pera", "Manzana", "Fresa", "Sandia", "Mango"};
        listaFrutas = new ArrayList<>();
//        Collections.addAll(strFrutas);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}