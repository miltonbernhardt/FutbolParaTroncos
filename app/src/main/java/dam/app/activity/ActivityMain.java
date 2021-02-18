package dam.app.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import dam.app.R;
import dam.app.AppFirebase;
import dam.app.extras.NotificationReserve;
import rx.Subscription;

public class ActivityMain extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    public AppFirebase _FIREBASE = null;

    protected ActivityMain _CONTEXT;
    protected Subscription _SUBSCRIPTION;

    private boolean backToMenu = false;

    public void createDrawable(ActivityMain context, boolean backMenu){
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer);

        navigationView = findViewById(R.id.navigation_view);
        navigationView.getMenu().clear();
        navigationView.inflateMenu(R.menu.menu_all_options);
        navigationView.setNavigationItemSelectedListener(this);
        View v = navigationView.getHeaderView(0);
        TextView username = v.findViewById(R.id.header_username);
        TextView header_today = v.findViewById(R.id.header_today);
        username.setText("Usuario no logueado");
        header_today.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

        setSupportActionBar(toolbar);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_drawer_open, R.string.nav_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        _CONTEXT = context;
        _FIREBASE = AppFirebase.getInstance(_CONTEXT);
        if(_FIREBASE.isLogged()) _FIREBASE.updateHeaderDrawer(username);//Funciona raro
        backToMenu = backMenu;

        BroadcastReceiver br = new NotificationReserve();
        IntentFilter intent = new IntentFilter();
        intent.addAction(NotificationReserve.idIntent);
        _CONTEXT.getApplication().getApplicationContext().registerReceiver(br, intent);

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.menu_option_login:
                startActivity(new Intent(_CONTEXT, ActivityLogin.class));
                Log.d("on DrawerLayout", getResources().getString(R.string.activity_session));
                finishAffinity();
                break;

            case R.id.menu_option_register:
                startActivity(new Intent(_CONTEXT, ActivityRegisterUser.class));
                Log.d("on DrawerLayout", getResources().getString(R.string.activity_session));
                finishAffinity();
                break;

            case R.id.menu_option_fields:
                startActivity(new Intent(_CONTEXT, ActivityFields.class));
                Log.d("on DrawerLayout", _CONTEXT.getResources().getString(R.string.activity_fields));
                finishAffinity();
                break;

            case R.id.menu_option_reserves:
                if(_FIREBASE.isLogged()) {
                    startActivity(new Intent(_CONTEXT, ActivityReserves.class));
                    Log.d("on DrawerLayout", _CONTEXT.getResources().getString(R.string.activity_reserves));
                    finishAffinity();
                }
                break;

            case R.id.menu_option_close_session:
                Toast.makeText(_CONTEXT, R.string.message_closing_session, Toast.LENGTH_SHORT).show();
                Log.d("on DrawerLayout", getResources().getString(R.string.message_closing_session));
                _FIREBASE.signOut();
                startActivity(new Intent(_CONTEXT, ActivityMenu.class));
                finishAffinity();
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            if(backToMenu)  startActivity(new Intent(_CONTEXT, ActivityMenu.class));
            super.onBackPressed();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if(_SUBSCRIPTION != null && !_SUBSCRIPTION.isUnsubscribed()) _SUBSCRIPTION.unsubscribe();
    }
}