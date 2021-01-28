package dam.app.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import dam.app.R;
import dam.app.database.AppRepository;
import dam.app.model.Comment;
import dam.app.model.Field;
import dam.app.recycler.CommentRecycler;
import rx.Subscriber;

public class ActivityComments extends AppCompatActivity {
    private RecyclerView recyclerView;

    private AppRepository _REPOSITORY = null;
    private ActivityComments _CONTEXT;

    private Subscriber<List<Comment>> subscription;

    private Button btnMakeOpinion;
    private Field field;
    private TextView lblNameField;
    private Spinner spinnerCommentsOptions;

    private static final int REQUEST_CODE = 222;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments_recycler);
        _CONTEXT = this;
        _REPOSITORY = AppRepository.getInstance(_CONTEXT);

        recyclerView = findViewById(R.id.recyclerComments);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new CommentRecycler(_CONTEXT, new ArrayList<>()));

        field = (Field) getIntent().getExtras().getSerializable("field");

        subscription = _REPOSITORY.getCommentsSubscriber(recyclerView, field.getId()); //ToDo ActivityComments ver por qué no carga la lista la primera que entras a la vista

        lblNameField = findViewById(R.id.lblNameField);
        lblNameField.setText(field.getName());

        spinnerCommentsOptions = findViewById(R.id.spinnerCommentsOptions);
        spinnerCommentsOptions.setAdapter(new ArrayAdapter<>(this, R.layout.spinner_layout, getResources().getStringArray(R.array.optionsComments)));

        btnMakeOpinion = findViewById(R.id.btnMakeOpinion);
        btnMakeOpinion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent makeReviewScreen = new Intent(_CONTEXT, ActivityNewComment.class);
                makeReviewScreen.putExtra("addButtonAsk", true);
                startActivity(makeReviewScreen);
                Log.d("on ActivityComments", _CONTEXT.getResources().getString(R.string.activity_new_comment));
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        if(!subscription.isUnsubscribed()) subscription.unsubscribe();
        AppRepository.close();
    }
}


