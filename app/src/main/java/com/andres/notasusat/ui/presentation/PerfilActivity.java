package com.andres.notasusat.ui.presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.andres.notasusat.R;
import com.andres.notasusat.ui.data.ApolloKt;
import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.example.UserQuery;

import org.jetbrains.annotations.NotNull;

import java.util.Date;

public class PerfilActivity extends AppCompatActivity {

    private  Integer id;
    private  String nickname;
    private  String name;
    private  String lastName;
    private String email;
    private String birthday;
    private String faculty;
    private String school;
    private Boolean genre;
    private Boolean genreSelected;

    private EditText txtNickname;
    private EditText txtName;
    private EditText txtLastName;
    private EditText txtEmail;
    private EditText txtBirthday;
    private TextView lblFaculty;
    private TextView lblCompleteName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        txtNickname = findViewById(R.id.inputNickPerfil);
        txtName = findViewById(R.id.inputNamePerfil);
        txtLastName = findViewById(R.id.inputLastNamePerfil);
        txtEmail = findViewById(R.id.inputEmailPerfil);
        txtBirthday = findViewById(R.id.inputBirthdayPerfil);
        lblFaculty = findViewById(R.id.lblFacultyPerfil);
        lblCompleteName = findViewById(R.id.lblCompleteNamePerfil);


        SharedPreferences preferences = getSharedPreferences("Preferences", MODE_PRIVATE);
        id = preferences.getInt("userID", 0);

        if (id > 0 ){
            setPerfil(id);
        }
    }

    private void setPerfil(Integer userID){
        ApolloKt.apolloClient(this).query(new UserQuery(userID))
                .enqueue(new ApolloCall.Callback<UserQuery.Data>() {
                    @Override
                    public void onResponse(@NotNull Response<UserQuery.Data> response) {
                       try{
                           nickname = response.getData().user().nickname();
                           name = response.getData().user().name();
                           lastName = response.getData().user().lastname();
                           email = response.getData().user().email();
                           birthday = response.getData().user().birthDate().toString();
                           faculty = response.getData().user().school().faculty().name();
                           school = response.getData().user().school().name();
                           genre = response.getData().user().genre();


                           runOnUiThread(new Runnable() {
                               @SuppressLint("SetTextI18n")
                               public void run() {
                                   txtNickname.setText(nickname);
                                   txtName.setText(name);
                                   txtLastName.setText(lastName);
                                   txtEmail.setText(email);
                                   txtBirthday.setText(birthday.toString());
                                   lblFaculty.setText(faculty.toUpperCase() + " - "+ school);
                                   lblCompleteName.setText(name.toUpperCase() + " " + lastName.toUpperCase());
                                   setGenre(genre);
                               }
                           });
                       } catch (ApolloException e){
                           Log.e("Apollo", "Usuario: " + response.errors().get(0).message());
                           runOnUiThread(new Runnable() {
                               public void run() {
                                   Toast.makeText(PerfilActivity.this, response.errors().get(0).message(), Toast.LENGTH_LONG).show();
                               }
                           });
                       }
                    }

                    @Override
                    public void onFailure(@NotNull ApolloException e) {
                        Log.e("Apollo", "Error", e);
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(PerfilActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
                    }
        });
    }


    private void setGenre(Boolean genre){
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
        spGenre.setSelection(genre ? 0 : 1);
    }

}