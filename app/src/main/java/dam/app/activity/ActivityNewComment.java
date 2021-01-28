package dam.app.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import dam.app.R;
import dam.app.database.AppDatabase;
import dam.app.database.AppRepository;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ActivityNewComment extends AppCompatActivity {
    private EditText textComment;
    private Button btnAddComment;

    private ActivityNewComment _CONTEXT;
    private AppRepository _REPOSITORY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_comment);

        _CONTEXT = this;
        _REPOSITORY = AppRepository.getInstance(_CONTEXT);

        textComment = findViewById(R.id.textComment);
        btnAddComment = findViewById(R.id.btnAddComment);
    }


    @Override
    public void onStop() {
        super.onStop();
        _REPOSITORY.close();
    }
}