package com.andres.notasusat.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.andres.notasusat.R;
import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.example.SchoolsQuery;
import com.example.SemestersQuery;
import com.example.SingUpMutation;
import com.example.type.UserInput;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class SingUpActivity extends AppCompatActivity  {

    ApolloClient apolloClient = ApolloClient.builder().serverUrl("https://notas-gql.herokuapp.com/graphql/endpoint").build();
    String semester;
    Integer schoolId;
    Boolean genreSelected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up);
        ArrayList<String> listSemesters = new ArrayList<>();
        ArrayList<String> listSchool = new ArrayList<>();
        ArrayList<Integer> listSchoolId = new ArrayList<>();
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
                    listSchool.add(response.getData().schools().get(i).name());
                    listSchoolId.add(Integer.parseInt(response.getData().schools().get(i).id()));
                    Log.e("Apollo", "Semestre: " + listSchool);
                }
                runOnUiThread(new Runnable() {
                    public void run() {
                        setSchool(listSchool, listSchoolId);
                    }
                });
            }

            @Override
            public void onFailure(@NotNull ApolloException e) {

            }
        });

        setGenre();
    }

    private void setSemesters(ArrayList<String> list){
        Spinner  spSemesters = (Spinner) findViewById(R.id.spinnerSemester);
        spSemesters.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                semester = parent.getItemAtPosition(position).toString();
                Log.e("Apollo", "Semestre: " +  parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayAdapter<CharSequence> comboSemesters = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, list);
        spSemesters.setAdapter(comboSemesters);
    }

    private void setSchool(ArrayList<String> list, ArrayList<Integer> listId ){
        Spinner  spSchools = (Spinner) findViewById(R.id.spinnerschool);
        spSchools.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                schoolId =  listId.get(position);
                Log.e("Apollo", "School: "+ listId.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayAdapter<CharSequence> comboSchool = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, list);
        spSchools.setAdapter(comboSchool);
    }

    private void setGenre(){
        Spinner spGenre = (Spinner)findViewById(R.id.spinnerGenrePerfil);

        spGenre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                genreSelected = position == 0;
                Log.e("Apollo", "Genero: " + genreSelected);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        String[] list = {"Masulino", "Femenino"};
        ArrayAdapter<CharSequence> comboGenre = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, list);
        spGenre.setAdapter(comboGenre);
    }

    public void singUp(View view){
        EditText nickname = (EditText)findViewById(R.id.inputNickname);
        EditText password = (EditText)findViewById(R.id.inputPassword);
        EditText email = (EditText)findViewById(R.id.inputEmail);
        EditText name = (EditText)findViewById(R.id.inputName);
        EditText lastname = (EditText)findViewById(R.id.inputLastName);
        EditText birthday = (EditText)findViewById(R.id.inputDate);

        UserInput userInput = UserInput.builder()
                .nickname(nickname.getText().toString())
                .password(password.getText().toString())
                .email(email.getText().toString())
                .name(name.getText().toString())
                .lastname(lastname.getText().toString())
                .birthDate(birthday.getText().toString())
                .photo("fotoperfil.png")
                .genre(genreSelected)
                .state(true)
                .semesterId(semester)
                .schoolId(schoolId).build();

        apolloClient.mutate(new SingUpMutation(userInput)).enqueue(new ApolloCall.Callback<SingUpMutation.Data>() {
            @Override
            public void onResponse(@NotNull Response<SingUpMutation.Data> response) {
                Log.e("Apollo", "Usuario: " + response.getData().signup().user().name());
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(SingUpActivity.this, "Hola " +response.getData().signup().user().name(), Toast.LENGTH_LONG).show();
                    }
                });
                openMainActivity();
            }

            @Override
            public void onFailure(@NotNull ApolloException e) {

            }
        });
    }

    private void openMainActivity(){
        Intent principal = new Intent(this, MainActivity.class);
        startActivity(principal);
    }

}