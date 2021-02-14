package dam.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import dam.app.R;
import dam.app.AppFirebase;

import static android.view.View.GONE;

public class ActivityMenu extends ActivityMain {

    protected Button btnLoginHome;
    protected Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        createDrawable(this);
        _FIREBASE = AppFirebase.getInstance(_CONTEXT);

        btnLoginHome = findViewById(R.id.btnLoginHome);
        btnRegister = findViewById(R.id.btnRegister);
        //VolatileData.persist(_FIREBASE);//Todo quitar o ver donde poner

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null) {
            btnLoginHome.setVisibility(GONE);
            btnRegister.setVisibility(GONE);
            Log.d("user", ""+user.getDisplayName());
            Log.d("pass", ""+user.getEmail());
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
}
