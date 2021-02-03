package dam.app.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import dam.app.R;

public class ActivityNewComment extends ActivityMain {
    protected EditText textComment;
    protected Button btnAddComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_comment);
        createDrawable();
        _CONTEXT = this;

        textComment = findViewById(R.id.textComment);
        btnAddComment = findViewById(R.id.btnAddComment);
    }
}