package dam.app.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import dam.app.R;

public class ActivityNewComment extends AppCompatActivity {
    private EditText textComment;
    private Button btnAddComment;
    private Button btnCamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_comment);

        textComment = findViewById(R.id.textComment);
        btnAddComment = findViewById(R.id.btnAddComment);
        btnCamera = findViewById(R.id.btnCamera);
    }
}