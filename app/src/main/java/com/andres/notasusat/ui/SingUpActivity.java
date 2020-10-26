package com.andres.notasusat.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.andres.notasusat.R;
import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.example.SchoolsQuery;
import com.example.SemestersQuery;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SingUpActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up);
        ArrayList<String> listSemesters = new ArrayList<>();
        ApolloClient apolloClient = ApolloClient.builder().serverUrl("https://notas-gql.herokuapp.com/graphql/endpoint").build();
        apolloClient.query(new SemestersQuery()).enqueue(new ApolloCall.Callback<SemestersQuery.Data>() {
            @Override
            public void onResponse(@NotNull Response<SemestersQuery.Data> response) {
                for(int i = 0; i < response.getData().semesters().size() ; i++){
                    listSemesters.add(response.getData().semesters().get(i).name());
                    Log.e("Apollo", "Semestre: " + listSemesters);
                }
                runOnUiThread(new Runnable() {
                    public void run() {
                        setSemesters(listSemesters);
                    }
                });
            }

            @Override
            public void onFailure(@NotNull ApolloException e) {

            }
        });

        apolloClient.query(new SchoolsQuery()).enqueue(new ApolloCall.Callback<SchoolsQuery.Data>() {
            @Override
            public void onResponse(@NotNull Response<SchoolsQuery.Data> response) {
                for(int i = 0; i < response.getData().schools().size() ; i++){
                    listSemesters.add(response.getData().schools().get(i).name());
                    Log.e("Apollo", "Semestre: " + listSemesters);
                }
                runOnUiThread(new Runnable() {
                    public void run() {
                        setSemesters(listSemesters);
                    }
                });
            }

            @Override
            public void onFailure(@NotNull ApolloException e) {

            }
        });

    }

    private void setSemesters(ArrayList<String> list){
        Spinner  spSemesters = (Spinner) findViewById(R.id.spinnerSemester);
        Spinner  spShools = (Spinner) findViewById(R.id.spinnerschool);

        spSemesters.setOnItemSelectedListener(this);
        spShools.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> comboSemesters = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, list);
        ArrayAdapter<CharSequence> comboSchool = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, list);
        spSemesters.setAdapter(comboSemesters);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}