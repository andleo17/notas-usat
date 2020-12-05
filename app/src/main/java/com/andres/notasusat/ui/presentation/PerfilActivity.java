package com.andres.notasusat.ui.presentation;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Build;
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
import com.andres.notasusat.data.ApolloKt;
import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.andres.notasusat.ModifyUserMutation;
import com.andres.notasusat.UserQuery;
import com.andres.notasusat.type.UserInput;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;


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
        ApolloKt.apolloClient().query(new UserQuery(userID))
                .enqueue(new ApolloCall.Callback<UserQuery.Data>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onResponse(@NotNull Response<UserQuery.Data> response) {
                       try{
                           UserQuery.User user = Objects.requireNonNull(response.getData()).user();
                           nickname = user.nickname();
                           name = user.name();
                           lastName = user.lastname();
                           email = user.email();
                           birthday = user.birthDate().toString();
                           faculty = user.school().faculty().name();
                           school = user.school().name();
                           genre = user.genre();


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

    public void saveChanges(View view) {
        SharedPreferences preferences = getSharedPreferences("Preferences", MODE_PRIVATE);
        id = preferences.getInt("userID", 0);


        UserInput userInput = UserInput.builder()
                .nickname(txtNickname.getText().toString())
                .name(txtName.getText().toString())
                .lastname(txtLastName.getText().toString())
                .birthDate(txtBirthday.getText().toString())
                .email(txtEmail.getText().toString())
                .photo("fotoperfil.png")
                .genre(genreSelected)
                .build();

        ApolloKt.apolloClient().mutate(new ModifyUserMutation(userInput, id )).enqueue(new ApolloCall.Callback<ModifyUserMutation.Data>() {
            @Override
            public void onResponse(@NotNull Response<ModifyUserMutation.Data> response) {
                try {
                    Log.e("Apollo", "Usuario: " + response.getData().modifyUser().name());

                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(PerfilActivity.this, "Cambios guardados correctamente", Toast.LENGTH_LONG).show();
                        }
                    });

                }catch (Exception e){
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
}