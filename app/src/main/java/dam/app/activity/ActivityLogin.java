package dam.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import dam.app.R;

public class ActivityLogin extends ActivityMain {

    protected Button bntLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        createDrawable();
        _CONTEXT = this;

        bntLogin = findViewById(R.id.btnRegisterUser);
        bntLogin.setOnClickListener(v -> {
            Intent makeMenuScreen = new Intent(_CONTEXT, ActivityMenu.class);
            startActivity(makeMenuScreen);
            Log.d("on ActivityLogin", _CONTEXT.getResources().getString(R.string.activity_fields));
            finish();
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent makeMenuScreen = new Intent(_CONTEXT, ActivityMenu.class);
        startActivity(makeMenuScreen);
        Log.d("on ActivityLogin", _CONTEXT.getResources().getString(R.string.activity_fields));
        finish();
    }
}