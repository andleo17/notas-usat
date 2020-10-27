package com.andres.notasusat.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.andres.notasusat.R;
import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.example.LoginQuery;

import org.jetbrains.annotations.NotNull;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void login(View view){
        EditText nickname = (EditText)findViewById(R.id.txtNickname);
        EditText password = (EditText)findViewById(R.id.txtPassword);
        ApolloClient apolloClient = ApolloClient.builder().serverUrl("https://notas-gql.herokuapp.com/graphql/endpoint").build();
        apolloClient.query(new LoginQuery(nickname.getText().toString(), password.getText().toString()))
                .enqueue(new ApolloCall.Callback<LoginQuery.Data>() {
                    @Override
                    public void onResponse(@NotNull Response<LoginQuery.Data> response) {
                        try {
                            Log.e("Apollo", "Usuario: " + response.getData().login().user().name());
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    Toast.makeText(LoginActivity.this, "Hola " +response.getData().login().user().name(), Toast.LENGTH_LONG).show();
                                }
                            });
                            openMainActivity();
                        }catch (Exception e){
                            Log.e("Apollo", "Usuario: " + response.errors().get(0).message());
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    Toast.makeText(LoginActivity.this, response.errors().get(0).message(), Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }
                    @Override
                    public void onFailure(@NotNull ApolloException e) {
                        Log.e("Apollo", "Error", e);
                    }
                });
    }

    private void openMainActivity(){
        Intent principal = new Intent(this, MainActivity.class);
        startActivity(principal);
    }

    public void openSingUp(View view){
        Intent singUp = new Intent(this, SingUpActivity.class);
        startActivity(singUp);
    }
}