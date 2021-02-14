package dam.app.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import dam.app.R;
import dam.app.AppFirebase;
import rx.Subscription;

public class ActivityMain extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public AppFirebase _FIREBASE = null;
    protected ActivityMain _CONTEXT;

    protected Subscription _SUBSCRIPTION;

    protected Toolbar toolbar;
    protected DrawerLayout drawerLayout;
    protected ActionBarDrawerToggle toggle;
    protected NavigationView navigationView;

    protected FirebaseAuth mAuth;

    private boolean backToMenu = false;

    public void createDrawable(ActivityMain context, boolean backToMenu){
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigation_view);

        setSupportActionBar(toolbar);
        navigationView.setNavigationItemSelectedListener(this);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_drawer_open, R.string.nav_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        _CONTEXT = context;
        _FIREBASE = AppFirebase.getInstance(_CONTEXT);

        mAuth = FirebaseAuth.getInstance();

        this.backToMenu = backToMenu;
    }

    protected void setMenu(int menu){
        navigationView.getMenu().clear();
        navigationView.inflateMenu(menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.menu_option_fields:
                Intent makeFieldsScreen = new Intent(_CONTEXT, ActivityFields.class);
                startActivity(makeFieldsScreen);
                Log.d("on DrawerLayout", _CONTEXT.getResources().getString(R.string.activity_fields));
                finish();
                break;
            case R.id.menu_option_reserves:
                Intent makeReviewScreen = new Intent(_CONTEXT, ActivityReserves.class);
                startActivity(makeReviewScreen);
                Log.d("on DrawerLayout", _CONTEXT.getResources().getString(R.string.activity_reserves));
                finish();
                break;
            case R.id.menu_option_close_session:
                Toast.makeText(_CONTEXT, R.string.message_closing_session, Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(),ActivityMenu.class));
                finish();
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
        AppFirebase.close();
        if(_SUBSCRIPTION != null && !_SUBSCRIPTION.isUnsubscribed()) _SUBSCRIPTION.unsubscribe();
    }
}