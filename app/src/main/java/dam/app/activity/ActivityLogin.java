package dam.app.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
            _FIREBASE.signIn(email,password);
        });

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        navigationView.getMenu().getItem(1).setVisible(false);
        navigationView.getMenu().getItem(2).setVisible(false);
        navigationView.getMenu().getItem(4).setVisible(false);
        return true;
    }
}