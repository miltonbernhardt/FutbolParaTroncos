package dam.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import dam.app.R;
import dam.app.database.AppRepository;

public class ActivitySession extends AppCompatActivity {

    private Button bntLogin;

    private ActivitySession _CONTEXT;
    private AppRepository _REPOSITORY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session);

        _CONTEXT = this;
        _REPOSITORY = AppRepository.getInstance(_CONTEXT);

        bntLogin = findViewById(R.id.btnLogin);
        bntLogin.setOnClickListener(v -> {
            Intent makeReviewScreen = new Intent(_CONTEXT, ActivityFields.class);
            makeReviewScreen.putExtra("addButtonAsk", true);
            startActivity(makeReviewScreen);
            Log.d("on ActivitySession", _CONTEXT.getResources().getString(R.string.activity_fields));
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        AppRepository.close();
    }
}