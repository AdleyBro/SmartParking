package com.uahcu.parkinginteligente;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.uahcu.parkinginteligente.conexion.ConnectionHandler;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

public class MainMenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        // TOOLBAR
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // DRAWER
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this); // Listener para detectar los botones del menú desplegable
        View headerView = navigationView.getHeaderView(0);
        ((TextView) headerView.findViewById(R.id.navHeaderNombre)).setText(UserInfo.getNombreUsuario());
        ((TextView) headerView.findViewById(R.id.navHeaderEmail)).setText(UserInfo.getEmail());

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();

        // NAV CONTROLLER
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        ConnectionHandler.closestBookingRequest(UserInfo.getId());
        try {
            ConnectionHandler.waitForResponse();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ArrayList<String> respuesta = ConnectionHandler.getFullResponse();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (respuesta.size() > 2) {
            ft.replace(R.id.mapPlaceholder, new CloserBookingFragment(respuesta));
        } else {
            ft.replace(R.id.mapPlaceholder, new NoParkingSlotFragment());
        }
        ft.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch(menuItem.getItemId()) {
            case R.id.nav_cerrar_sesion:
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,
                        new RegisterFragment()).commit();
                break;
            default:
                break;
        }

        return true;
    }
}