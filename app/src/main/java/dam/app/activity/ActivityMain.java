package dam.app.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import dam.app.R;
import dam.app.AppFirebase;
import dam.app.model.User;
import rx.Subscription;

public class ActivityMain extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    Menu menu;
    NavigationView navigationView;
    Toolbar toolbar;

    public AppFirebase _FIREBASE = null;

    protected ActivityMain _CONTEXT;
    protected Subscription _SUBSCRIPTION;
    protected FirebaseAuth _AUTH;

    private boolean backToMenu = false;

    private User user;

    public void createDrawable(ActivityMain context, boolean backToMenu){
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigation_view);
        menu = findViewById(R.id.menu_all_options);

        setSupportActionBar(toolbar);
        navigationView.setNavigationItemSelectedListener(this);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_drawer_open, R.string.nav_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        _CONTEXT = context;
        _FIREBASE = AppFirebase.getInstance(_CONTEXT);
        _AUTH = FirebaseAuth.getInstance();





        this.backToMenu = backToMenu;
        setMenu(R.menu.menu_all_options);
    }

    protected void setMenu(int menu){
        navigationView.getMenu().clear();
        navigationView.inflateMenu(menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.menu_option_session:
                startActivity(new Intent(_CONTEXT, ActivityMenu.class));

                Log.d("on DrawerLayout", getResources().getString(R.string.activity_session));

                finishAffinity();
                break;
            case R.id.menu_option_fields:
                Intent makeFieldsScreen = new Intent(_CONTEXT, ActivityFields.class);
                startActivity(makeFieldsScreen);

                Log.d("on DrawerLayout", _CONTEXT.getResources().getString(R.string.activity_fields));

                finishAffinity();
                break;
            case R.id.menu_option_reserves:
                if(_FIREBASE.isLogged()) {
                    FirebaseUser u = FirebaseAuth.getInstance().getCurrentUser();
                    Intent makeReviewScreen = new Intent(_CONTEXT, ActivityReserves.class);
                    makeReviewScreen.putExtra("iduser", u.getUid());
                    setResult(Activity.RESULT_OK, makeReviewScreen);
                    startActivity(makeReviewScreen);

                    Log.d("on DrawerLayout", _CONTEXT.getResources().getString(R.string.activity_reserves));

                    finish();

                }

                break;
            case R.id.menu_option_close_session:
                Toast.makeText(_CONTEXT, R.string.message_closing_session, Toast.LENGTH_SHORT).show();
                _FIREBASE.signOut();

                Intent intent = new Intent(_CONTEXT, ActivityMenu.class);
                startActivity(intent);

                Log.d("on DrawerLayout", getResources().getString(R.string.message_closing_session));

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
        AppFirebase.close();
        if(_SUBSCRIPTION != null && !_SUBSCRIPTION.isUnsubscribed()) _SUBSCRIPTION.unsubscribe();
    }
}