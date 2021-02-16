package dam.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import dam.app.R;

public class ActivityLogin extends ActivityMain {
    Button bntLogin;
    EditText mEmail;
    EditText mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        createDrawable(this, true);

        mEmail = findViewById(R.id.textEmailLogin);
        mPassword = findViewById(R.id.textPasswordLogin);
        addListenerTextView(mEmail);
        addListenerTextView(mPassword);

        bntLogin = findViewById(R.id.btnRegisterUser);
        bntLogin.setEnabled(false);
        bntLogin.setOnClickListener(v -> {
            String email = mEmail.getText().toString();
            String password = mPassword.getText().toString();
            signIn(email,password);
        });

        setMenu(R.menu.menu_only_fields);
    }

    private void addListenerTextView(TextView t){
        t.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(mEmail.getText().toString().trim().length() > 0 && mPassword.getText().toString().trim().length() > 0)
                    bntLogin.setEnabled(true);
                else
                    bntLogin.setEnabled(false);
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void afterTextChanged(Editable s) { }
        });
    }

    private void signIn(String email, String password){
        _AUTH.signInWithEmailAndPassword(email, password).addOnCompleteListener(_CONTEXT, task -> {
            if (task.isSuccessful()) {
                Log.w("on ActivityLogin", _CONTEXT.getResources().getString(R.string.successfulLogin));
                Toast.makeText(_CONTEXT, R.string.successfulLogin, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(_CONTEXT, ActivityMenu.class));
                _CONTEXT.finish();
            } else {
                Log.w("on ActivityLogin", _CONTEXT.getResources().getString(R.string.errorLogin), task.getException());
                Toast.makeText(_CONTEXT, R.string.errorLogin, Toast.LENGTH_SHORT).show();
            }
        });
    }
}