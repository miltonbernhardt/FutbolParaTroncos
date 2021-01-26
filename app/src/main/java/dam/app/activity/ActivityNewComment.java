package dam.app.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import dam.app.R;
import dam.app.database.AppDatabase;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ActivityNewComment extends AppCompatActivity {
    private AppDatabase database;
    private EditText textComment;
    private Button btnAddComment;
    private Button btnCamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_comment);

        database = AppDatabase.getInstance(this);

        textComment = findViewById(R.id.textComment);
        btnAddComment = findViewById(R.id.btnAddComment);
        btnCamera = findViewById(R.id.btnCamera);
    }


    @Override
    public void onStop() {
        super.onStop();
        database.close();
    }

}