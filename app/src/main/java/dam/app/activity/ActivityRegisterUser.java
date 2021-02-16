package dam.app.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dam.app.R;
import dam.app.model.User;

public class ActivityRegisterUser extends ActivityMain  {

    Button mRegisterBtn;
    EditText mUserName;
    EditText mEmail;
    EditText mPassword;
    EditText mPasswordConfirmation;
    ProgressBar mLoadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        createDrawable(this, true);

        setMenu(R.menu.menu_only_fields);

        mRegisterBtn = findViewById(R.id.btnRegisterUser);
        mUserName = findViewById(R.id.textUserName);
        mEmail = findViewById(R.id.textEmail);
        mPassword = findViewById(R.id.textPassword);
        mPasswordConfirmation = findViewById(R.id.textPasswordConfirmation);
        mLoadingBar = findViewById(R.id.progressBarRegister);

        String emailRegex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern emailPattern = Pattern.compile(emailRegex);

        mRegisterBtn.setOnClickListener(view -> {
            String email = mEmail.getText().toString().trim();
            String userName = mUserName.getText().toString();
            String password1 = mPassword.getText().toString().trim();
            String password2 = mPasswordConfirmation.getText().toString().trim();
            String error = "";
            Matcher matcher = emailPattern.matcher(email);
            boolean wrongField = false;

            mLoadingBar.setVisibility(View.VISIBLE);

            if(!matcher.matches()) {
                wrongField = true;
                error += "- Formato de email inválido.\n";
            }

            if(!(userName.length() > 0)) {
                wrongField = true;
                error += "- Nombre de usuario vacío.\n";
            }

            if(!(password1.length()>5)) {
                wrongField = true;
                error += "- Contraseña demasiado corta.";
            }
            else if (!password1.equals(password2)) {
                    wrongField = true;
                    error += "- Las contraseñas no coinciden.";
            }

            if(!wrongField) {
                User user = new User();
                user.setUserName(userName);
                user.setMail(email);
                user.setPassword(password1);

                _FIREBASE.registerUser(user);
            }
            else {
                Log.w("FAIL", error);
                Toast.makeText(_CONTEXT, error, Toast.LENGTH_SHORT).show();
            }
            mLoadingBar.setVisibility(View.INVISIBLE);
        });
    }

}
