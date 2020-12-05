package com.andres.notasusat.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.andres.notasusat.R;
import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.andres.notasusat.LoginQuery;
import com.andres.notasusat.ApolloKt;

import org.jetbrains.annotations.NotNull;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button loginButton = findViewById(R.id.btnIngresar);
        loginButton.setOnClickListener(this::login);
    }

    public void login(View view){
        EditText nickname = findViewById(R.id.txtNickname);
        EditText password = findViewById(R.id.txtPassword);

        LoginQuery loginQuery = new LoginQuery(nickname.getText().toString(), password.getText().toString());
        ApolloKt.apolloClient().query(loginQuery)
                .enqueue(new ApolloCall.Callback<LoginQuery.Data>() {
                    @Override
                    public void onResponse(@NotNull Response<LoginQuery.Data> response) {
                        Log.i("Respuesta", response.toString());
                        runOnUiThread(() -> {
                            if (response.getData() == null) {
                                Toast.makeText(LoginActivity.this, response.getErrors().get(0).getMessage(), Toast.LENGTH_LONG).show();
                            } else {
                                LoginQuery.User user = response.getData().login().user();
                                Toast.makeText(LoginActivity.this, "Hola " + user.name() + " " + user.lastname(), Toast.LENGTH_LONG).show();
                                openMainActivity();
                            }
                        });
                    }
                    @Override
                    public void onFailure(@NotNull ApolloException e) {
                        e.printStackTrace();
                        runOnUiThread(() -> Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show());
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