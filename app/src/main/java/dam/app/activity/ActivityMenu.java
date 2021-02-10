package dam.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import dam.app.R;

import static android.view.View.GONE;

public class ActivityMenu extends ActivityMain {

    protected Button btnLoginHome;
    protected Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        createDrawable();
        _CONTEXT = this;

        btnLoginHome = findViewById(R.id.btnLoginHome);
        btnRegister = findViewById(R.id.btnRegister);

        //if the user is login, so don't show the buttons are useless
        if(FirebaseAuth.getInstance().getCurrentUser() != null) {
            btnLoginHome.setVisibility(GONE);
            btnRegister.setVisibility(GONE);
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            Log.d("user",user.getDisplayName());
            Log.d("pass",user.getEmail());
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
