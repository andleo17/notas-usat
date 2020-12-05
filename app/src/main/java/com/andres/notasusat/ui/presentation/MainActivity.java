package com.andres.notasusat.ui.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.andres.notasusat.R;
import com.andres.notasusat.data.DatabaseHelper;
import com.andres.notasusat.ui.dashboard.DashboardFragment;
import com.andres.notasusat.ui.home.HomeFragment;
import com.andres.notasusat.ui.notifications.NotificationsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);

        navView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    openFragment(new HomeFragment());
                    return true;
                case R.id.navigation_profile:
                    openFragment(new NotificationsFragment());
                    return true;
                case R.id.navigation_dashboard:
                    openFragment(new DashboardFragment());
                    return true;
                default:
                    openFragment(new HomeFragment());
                    return true;
            }
        });
        navView.setSelectedItemId(R.id.navigation_home);

        DatabaseHelper conn = new DatabaseHelper(this, "Notas_USAT", null, 1);
        

    }

    private void openFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        manager.popBackStackImmediate();
        manager
                .beginTransaction()
                .replace(R.id.main_fragment, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack(null)
                .commit();
    }

    public void openPerfil(View view){
        Intent principal = new Intent(this, CursoActivity.class);
        startActivity(principal);
    }

}