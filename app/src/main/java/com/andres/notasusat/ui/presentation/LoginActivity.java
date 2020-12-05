package com.andres.notasusat.ui.presentation;

import android.content.Intent;
import android.content.SharedPreferences;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.andres.notasusat.LoginQuery;
import com.andres.notasusat.R;
import com.andres.notasusat.data.ApolloKt;
import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Executor;

public class LoginActivity extends AppCompatActivity {
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button loginButton = findViewById(R.id.btnIngresar);
        loginButton.setOnClickListener(this::login);

        preferences = getSharedPreferences("Preferences", MODE_PRIVATE);

        if(preferences.getInt("userID", 0) > 0){
            openMainActivity();
        }
        //biometricSecurity();
    }


    public void login(View view){
        EditText nickname = findViewById(R.id.txtNickname);
        EditText password = findViewById(R.id.txtPassword);

        LoginQuery loginQuery = new LoginQuery(nickname.getText().toString(), password.getText().toString());
        ApolloKt.apolloClient().query(loginQuery)
                .enqueue(new ApolloCall.Callback<LoginQuery.Data>() {
                    @Override
                    public void onResponse(@NotNull Response<LoginQuery.Data> response) {
                        runOnUiThread(() -> {
                            if (response.getData() == null) {
                                Toast.makeText(LoginActivity.this, response.getErrors().get(0).getMessage(), Toast.LENGTH_LONG).show();
                            } else {
                                LoginQuery.User user = response.getData().login().user();
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putInt("userID", Integer.parseInt(user.id()));
                                editor.putString("nickname", user.nickname());
                                editor.putString("token", response.getData().login().token());
                                editor.apply();
                                Toast.makeText(LoginActivity.this, "Hola " + user.name() + " " + user.lastname(), Toast.LENGTH_LONG).show();
                            }
                        });
                        openMainActivity();
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
        principal.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(principal);
    }

    public void openSingUp(View view){
        Intent singUp = new Intent(this, SingUpActivity.class);
        startActivity(singUp);
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    public void biometricSecurity(){
        BiometricManager biometricManager = BiometricManager.from(this);

        switch (biometricManager.canAuthenticate()){
            case BiometricManager.BIOMETRIC_SUCCESS:
                Toast.makeText(LoginActivity.this,"USE EL SENSOR DACTILAR PARA ACCEDER" , Toast.LENGTH_LONG).show();
                break;
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                Toast.makeText(LoginActivity.this,"EL DISPOSITIVO NO CUENTA CON SENSOR DACTILAR" , Toast.LENGTH_LONG).show();
                break;
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                Toast.makeText(LoginActivity.this,"SENSOR DACTILAR NO DISPONIBLE" , Toast.LENGTH_LONG).show();
                break;
            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                Toast.makeText(LoginActivity.this,"SENSOR DACTILAR NO CONFIGURADO" , Toast.LENGTH_LONG).show();
                break;
        }

        Executor executor = ContextCompat.getMainExecutor(this);

        BiometricPrompt biometricPrompt = new BiometricPrompt(LoginActivity.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(LoginActivity.this,"AUNTENTICACIÓN CORRECTA!!!" , Toast.LENGTH_LONG).show();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
            }
        });

        BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Acceder")
                .setDescription("Toque el sensor dactilar para acceder")
                .setNegativeButtonText("Cancelar")
                .build();

        biometricPrompt.authenticate(promptInfo);
    }
}