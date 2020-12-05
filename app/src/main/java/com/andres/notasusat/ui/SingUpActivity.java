package com.andres.notasusat.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.andres.notasusat.ApolloKt;
import com.andres.notasusat.R;
import com.andres.notasusat.SignUpMutation;
import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.andres.notasusat.SchoolsQuery;
import com.andres.notasusat.SemestersQuery;
import com.andres.notasusat.type.UserInput;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class SingUpActivity extends AppCompatActivity  {
    ApolloClient apolloClient;
    String semester;
    Integer schoolId;
    Boolean genreSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up);
        apolloClient = ApolloKt.apolloClient();

        runOnUiThread(() -> {
            getSemesters();
            getSchools();
            setGenre();
        });
    }

    private void getSemesters() {
        SemestersQuery semestersQuery = new SemestersQuery();
        apolloClient.query(semestersQuery).enqueue(new ApolloCall.Callback<SemestersQuery.Data>() {
            @Override
            public void onResponse(@NotNull Response<SemestersQuery.Data> response) {
                Spinner spSemesters = findViewById(R.id.spinnerSemester);
                ArrayAdapter<SemestersQuery.Semester> adapter = new ArrayAdapter<SemestersQuery.Semester>(SingUpActivity.this, android.R.layout.simple_spinner_dropdown_item, response.getData().semesters()) {
                    @NonNull
                    @Override
                    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                        SemestersQuery.Semester semester = getItem(position);
                        if (convertView == null) {
                            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
                        }
                        TextView label = (TextView) super.getView(position, convertView, parent);
                        label.setText(semester.name());
                        return convertView;
                    }

                    @Override
                    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                        SemestersQuery.Semester semester = getItem(position);
                        if (convertView == null) {
                            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
                        }
                        TextView label = (TextView) super.getDropDownView(position, convertView, parent);
                        label.setText(semester.name());
                        return convertView;
                    }
                };
                spSemesters.setAdapter(adapter);
                spSemesters.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        SemestersQuery.Semester semesterSelected = (SemestersQuery.Semester) parent.getItemAtPosition(position);
                        semester = semesterSelected.name();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            @Override
            public void onFailure(@NotNull ApolloException e) {
                Log.i("Error", e.toString());
            }
        });
    }

    private void getSchools() {
        SchoolsQuery schoolsQuery = new SchoolsQuery();
        apolloClient.query(schoolsQuery).enqueue(new ApolloCall.Callback<SchoolsQuery.Data>() {
            @Override
            public void onResponse(@NotNull Response<SchoolsQuery.Data> response) {
                Spinner spSemesters = findViewById(R.id.spinnerschool);
                ArrayAdapter<SchoolsQuery.School> adapter = new ArrayAdapter<SchoolsQuery.School>(SingUpActivity.this, android.R.layout.simple_spinner_dropdown_item, response.getData().schools()) {
                    @NonNull
                    @Override
                    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                        SchoolsQuery.School school = getItem(position);
                        if (convertView == null) {
                            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
                        }
                        TextView label = (TextView) super.getView(position, convertView, parent);
                        label.setText(school.name());
                        return convertView;
                    }

                    @Override
                    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                        SchoolsQuery.School school = getItem(position);
                        if (convertView == null) {
                            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
                        }
                        TextView label = (TextView) super.getDropDownView(position, convertView, parent);
                        label.setText(school.name());
                        return convertView;
                    }
                };
                spSemesters.setAdapter(adapter);
                spSemesters.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        SchoolsQuery.School semesterSelected = (SchoolsQuery.School) parent.getItemAtPosition(position);
                        schoolId = Integer.parseInt(semesterSelected.id());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            @Override
            public void onFailure(@NotNull ApolloException e) {
                Log.i("Error", e.toString());
            }
        });
    }

    private void setGenre(){
        Spinner spGenre = findViewById(R.id.spinnerGenero);
        spGenre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                genreSelected = position == 0;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        String[] list = {"Masculino", "Femenino"};
        ArrayAdapter<CharSequence> comboGenre = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, list);
        spGenre.setAdapter(comboGenre);
    }

    public void singUp(View view){
        EditText nickname = findViewById(R.id.inputNickname);
        EditText password = findViewById(R.id.inputPassword);
        EditText email = findViewById(R.id.inputEmail);
        EditText name = findViewById(R.id.inputName);
        EditText lastname = findViewById(R.id.inputLastName);
        EditText birthday = findViewById(R.id.inputDate);

        UserInput userInput = UserInput.builder()
                .nickname(nickname.getText().toString())
                .password(password.getText().toString())
                .email(email.getText().toString())
                .name(name.getText().toString())
                .lastname(lastname.getText().toString())
                .birthDate(birthday.getText().toString())
                .photo("fotoperfil.png")
                .genre(genreSelected)
                .semesterId(semester)
                .schoolId(schoolId)
                .build();

        SignUpMutation singUpMutation = new SignUpMutation(userInput);

        apolloClient.mutate(singUpMutation).enqueue(new ApolloCall.Callback<SignUpMutation.Data>() {
            @Override
            public void onResponse(@NotNull Response<SignUpMutation.Data> response) {
                SignUpMutation.User user = Objects.requireNonNull(response.getData()).signup().user();
                runOnUiThread(() -> Toast.makeText(SingUpActivity.this, "Hola " + user.name() + " " + user.lastname(), Toast.LENGTH_LONG).show());
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