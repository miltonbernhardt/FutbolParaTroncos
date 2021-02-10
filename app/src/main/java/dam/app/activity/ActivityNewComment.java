package dam.app.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;

import dam.app.R;
import dam.app.model.Comment;

public class ActivityNewComment extends ActivityMain {
    protected Button btnAddComment;
    protected CheckBox checkBoxUploadImage;
    protected EditText textComment;
    protected ImageButton btnCamera;
    protected ImageView imageUpload;
    protected RatingBar ratingBar;
    protected TextView lblNameField;

    protected long idReserve;
    protected String fieldName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_comment);
        createDrawable(this);

        idReserve = getIntent().getLongExtra("idReserve", -1);
        fieldName = getIntent().getStringExtra("fieldTitle");

        btnAddComment = findViewById(R.id.btnAddComment);
        btnCamera = findViewById(R.id.btnCamera);
        checkBoxUploadImage = findViewById(R.id.checkBoxUploadImage);
        imageUpload = findViewById(R.id.imageUpload);
        lblNameField = findViewById(R.id.lblNameField);
        ratingBar = findViewById(R.id.ratingBar);
        textComment = findViewById(R.id.textComment);

        lblNameField.setText(fieldName);

        btnCamera.setOnClickListener(v -> {
            //TODO ActivityNewComment subir imágen
        });

        btnAddComment.setOnClickListener(v -> {
            float score = ratingBar.getRating();
            String txtComment = textComment.getText().toString();
            boolean isUpload = checkBoxUploadImage.isChecked();
            if(score == 0.0) Toast.makeText(_CONTEXT, R.string.should_rate, Toast.LENGTH_LONG).show();
            else{
                Comment comment = new Comment();
                comment.setComment(txtComment);
                comment.setScore((int)score);
                comment.setImageUUID("");
                comment.setDateOfComment(LocalDate.now());
                comment.setId(idReserve);
                if(isUpload){
                    //ToDo ActivityNewComment subir imágen
                    comment.setImageUUID("b");
                }
                long id = _REPOSITORY.saveComment(comment);

                if(id < 0){
                    Log.d("on ActivityNewComment", _CONTEXT.getResources().getString(R.string.errorSaveComment));
                    Toast.makeText(_CONTEXT, R.string.errorSaveComment, Toast.LENGTH_LONG).show();
                }
                else{
                    Log.d("on ActivityMenu", _CONTEXT.getResources().getString(R.string.successfulSaveComment));
                    Toast.makeText(_CONTEXT, R.string.successfulSaveComment, Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });
    }
}