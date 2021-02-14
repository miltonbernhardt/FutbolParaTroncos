package dam.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import dam.app.R;

public class ActivityLogin extends ActivityMain {

    protected Button bntLogin;
    protected EditText mEmail, mPassword;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        createDrawable(this);

        bntLogin = findViewById(R.id.btnRegisterUser);
        mEmail = findViewById(R.id.textUserNameLogin);
        mPassword = findViewById(R.id.textPasswordLogin);
        mAuth = FirebaseAuth.getInstance();
        //TODO habilitar boton login solo si se escribió algo en email y password
        bntLogin.setOnClickListener(v -> {
            Intent makeMenuScreen = new Intent(_CONTEXT, ActivityMenu.class);
            startActivity(makeMenuScreen);
            String email = mEmail.getText().toString();
            String password = mPassword.getText().toString();
            //Log.d("user",email);
            //Log.d("pass",password);
            signIn(email,password);
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

    private void signIn(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(ActivityLogin.this,
                task -> {
                    if (task.isSuccessful()) {
                            Toast.makeText(ActivityLogin.this, "Registración exitosa.",
                                    Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(_CONTEXT, ActivityMenu.class));
                            finish();
                        } else {
                            Log.w("ActivityLogin", "signInWithEmail:failure", task.getException());
                            Toast.makeText(_CONTEXT, "Error en trar iniciar sesión.", Toast.LENGTH_SHORT).show();
                        }

                });
    }
}