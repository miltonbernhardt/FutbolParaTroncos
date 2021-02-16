package dam.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.Button;

import dam.app.R;
import dam.app.extras.VolatileData;

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

        //VolatileData.persist(_FIREBASE);//Todo quitar o ver donde poner

        if(_FIREBASE.isLogged()){
            //menu.findItem(R.id.menu_option_session).setEnabled(true);
            //menu.findItem(R.id.menu_option_fields).setEnabled(false);
            setMenu(R.menu.menu_all_options);
            btnLoginHome.setVisibility(GONE);
            btnRegister.setVisibility(GONE);
        }
        else {
            setMenu(R.menu.menu_without_reserves_and_session);
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
}
