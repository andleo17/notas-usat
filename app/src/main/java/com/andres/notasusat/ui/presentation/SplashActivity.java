package com.andres.notasusat.ui.presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.andres.notasusat.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent login = new Intent(this, LoginActivity.class);
        startActivity(login);
        finish();
    }
}