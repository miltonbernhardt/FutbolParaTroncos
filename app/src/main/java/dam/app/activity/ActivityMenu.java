package dam.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import dam.app.R;
import dam.app.database.AppRepository;

public class ActivityMenu extends ActivityMain {

    protected Button btnLoginHome;
    protected Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        createDrawable();
        _CONTEXT = this;
        _REPOSITORY = AppRepository.getInstance(_CONTEXT);
        //ToDo ActivityMenu fijarse si está logueado, si es asi ocultar los botones

        btnLoginHome = findViewById(R.id.btnLoginHome);
        btnLoginHome.setOnClickListener(v -> {
            Intent makeMenuScreen = new Intent(_CONTEXT, ActivitySession.class);
            startActivity(makeMenuScreen);
            Log.d("on ActivityMenu", _CONTEXT.getResources().getString(R.string.activity_register_user));
            finish();//ToDo ActivityMenu no deberia ser finish, deberia esperar que el otro vuelva
        });


        btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(v -> {
            /*Intent makeMenuScreen = new Intent(_CONTEXT, ActivityRegisterUser.class);
            startActivity(makeMenuScreen);
            Log.d("on ActivityMenu", _CONTEXT.getResources().getString(R.string.activity_fields));
            //finish();*/
            showDialog("PRUEBAS", "DESCOMENTAR EN ACTIVITY MENÚ");
        });
    }
}
