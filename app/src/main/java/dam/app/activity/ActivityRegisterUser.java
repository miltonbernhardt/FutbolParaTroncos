package dam.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dam.app.R;

public class ActivityRegisterUser extends ActivityMain  {

    protected FirebaseAuth mAuth;
    protected Button mRegisterBtn;
    protected EditText mUserName, mEmail, mPassword, mPasswordConfirmation;
    protected ProgressBar mLoadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        createDrawable();
        _CONTEXT = this;
        mAuth = FirebaseAuth.getInstance();
        mRegisterBtn = findViewById(R.id.btnRegisterUser);
        mUserName = findViewById(R.id.textUserName);
        mEmail = findViewById(R.id.textEmail);
        mPassword = findViewById(R.id.textPassword);
        mPasswordConfirmation = findViewById(R.id.textPasswordConfirmation);
        mLoadingBar = findViewById(R.id.progressBarRegister);

        //if the user is login, so we redirect to the main menu
        if(mAuth.getCurrentUser() != null) startActivity(new Intent(getApplicationContext(),ActivityMenu.class));
        //Regular Expression for Standard Email adress
        String emailRegex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern emailPattern = Pattern.compile(emailRegex);

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Fields to validate
                String email = mEmail.getText().toString().trim();
                String userName = mUserName.getText().toString();
                String password1 = mPassword.getText().toString().trim();
                String password2 = mPasswordConfirmation.getText().toString().trim();
                Log.d("pass",password1);

                Matcher matcher = emailPattern.matcher(email);
                Boolean wrongField = false;

                //progressBar animation
                mLoadingBar.setVisibility(View.VISIBLE);

                if(matcher.matches()){

                }else{
                    //icon wrong email
                    wrongField = true;
                }
                if(userName.length() > 0){

                }
                else{
                    //icon wrong name
                    wrongField = true;
                }
                if(password1.equals(password2)){

                }
                else{
                    //icon wrong password icon
                    wrongField = true;
                }
                //if all field are right, so go on
                if(!wrongField) {
                    mAuth.createUserWithEmailAndPassword(email, password1)
                            .addOnCompleteListener(ActivityRegisterUser.this, task -> {
                                if (task.isSuccessful()) {
                                    // Sign in success
                                    Toast.makeText(ActivityRegisterUser.this, "Registración exitosa.",
                                            Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(),ActivityMenu.class));
                                    finish();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w("FAIL", "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(ActivityRegisterUser.this, "Error en la registración.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            });
                }
                mLoadingBar.setVisibility(View.INVISIBLE);

            }
        });



    }

}
