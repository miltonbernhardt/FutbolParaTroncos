package dam.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.Button;

import dam.app.R;

import static android.view.View.GONE;

public class ActivityMenu extends ActivityMain {
    Button btnLoginHome;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        createDrawable(this, false);

        btnLoginHome = findViewById(R.id.btnLoginHome);
        btnRegister = findViewById(R.id.btnRegister);

        if(_FIREBASE.isLogged()){
            btnLoginHome.setVisibility(GONE);
            btnRegister.setVisibility(GONE);
        }
        else {
            btnLoginHome.setOnClickListener(v -> {
                Intent makeMenuScreen = new Intent(_CONTEXT, ActivityLogin.class);
                startActivity(makeMenuScreen);
                Log.d("on ActivityMenu", _CONTEXT.getResources().getString(R.string.activity_register_user));
                finish();
            });

            btnRegister.setOnClickListener(v -> {
                Intent makeMenuScreen = new Intent(_CONTEXT, ActivityRegisterUser.class);
                startActivity(makeMenuScreen);
                Log.d("on ActivityMenu", _CONTEXT.getResources().getString(R.string.activity_fields));
                finish();
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        navigationView.getMenu().getItem(2).setVisible(false);
        navigationView.getMenu().getItem(3).setVisible(false);
        if (!_FIREBASE.isLogged()){
            navigationView.getMenu().getItem(1).setVisible(false);
            navigationView.getMenu().getItem(4).setVisible(false);
        }
        return true;
    }
}
