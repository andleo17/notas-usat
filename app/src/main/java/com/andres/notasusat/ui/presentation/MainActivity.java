package com.andres.notasusat.ui.presentation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.andres.notasusat.R;
import com.andres.notasusat.data.DatabaseHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.mobile_navigation);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
//        NavigationUI.setupWithNavController(navView, navController);
        SharedPreferences preferences = getSharedPreferences("Preferences", MODE_PRIVATE);


        DatabaseHelper conn = new DatabaseHelper(this, "Notas_USAT", null, 1);
        

    }

    public void openPerfil(View view){
        Intent principal = new Intent(this, CursoActivity.class);
        startActivity(principal);
    }

}