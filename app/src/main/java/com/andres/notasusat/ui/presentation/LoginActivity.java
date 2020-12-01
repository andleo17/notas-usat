package com.andres.notasusat.ui.presentation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.andres.notasusat.R;
import com.andres.notasusat.ui.data.ApolloKt;
import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.example.LoginQuery;
import org.jetbrains.annotations.NotNull;
import java.util.concurrent.Executor;



public class LoginActivity extends AppCompatActivity {


    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        preferences = getSharedPreferences("Preferences", MODE_PRIVATE);

        if(preferences.getInt("userID", 0) > 0){
            openMainActivity();
        }
        biometricSecurity();
    }


    public void login(View view){
        EditText nickname = (EditText)findViewById(R.id.txtNickname);
        EditText password = (EditText)findViewById(R.id.txtPassword);


        ApolloKt.apolloClient(this).query(new LoginQuery(nickname.getText().toString(), password.getText().toString()))
                .enqueue(new ApolloCall.Callback<LoginQuery.Data>() {
                    @Override
                    public void onResponse(@NotNull Response<LoginQuery.Data> response) {
                        try {
                            Log.e("Apollo", "Usuario: " + response.getData().login().user().name());

                            runOnUiThread(new Runnable() {
                                public void run() {
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.putInt("userID", Integer.parseInt(response.getData().login().user().id()));
                                    editor.putString("nickname", response.getData().login().user().nickname());
                                    editor.commit();
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
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
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

    public  void biometricSecurity(){
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