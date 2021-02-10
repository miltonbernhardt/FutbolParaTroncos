package dam.app.activity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import dam.app.R;
import dam.app.database.AppRepository;
import dam.app.extras.Dialog;
import rx.Subscription;

public class ActivityMain extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    protected ActivityMain _CONTEXT;
    public AppRepository _REPOSITORY = null;
    protected Subscription _SUBSCRIPTION;

    protected static final int REQUEST_CODE = 222;

    protected Toolbar toolbar;
    protected DrawerLayout drawerLayout;
    protected ActionBarDrawerToggle toggle;
    protected NavigationView navigationView;

    public void createDrawable(ActivityMain _CONTEXT){
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigation_view);

        setSupportActionBar(toolbar);
        navigationView.setNavigationItemSelectedListener(this);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_drawer_open, R.string.nav_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        this._CONTEXT = _CONTEXT;
        _REPOSITORY = AppRepository.getInstance(_CONTEXT);

        if(_REPOSITORY.isLogged()) setMenu(R.menu.menu_all_options);
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
                //ToDo cerrar sessión
                Toast.makeText(_CONTEXT, R.string.message_closing_session, Toast.LENGTH_SHORT).show();
                this.finishAffinity();
                break;
        }
        return true;
    }

    public void showDialog(String title, String message)
    {
        FragmentManager fm = getSupportFragmentManager();
        Dialog editNameDialog = new Dialog(title,  message, _CONTEXT);
        editNameDialog.show(fm, "fragment_edit_name");
    }

    public void showDialog(int title, int message)
    {
        FragmentManager fm = getSupportFragmentManager();
        Dialog editNameDialog = new Dialog(getResources().getString(title),  getResources().getString(message), _CONTEXT);
        editNameDialog.show(fm, "fragment_edit_name");
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        AppRepository.close();
        if(_SUBSCRIPTION != null && !_SUBSCRIPTION.isUnsubscribed()) _SUBSCRIPTION.unsubscribe();
    }
}