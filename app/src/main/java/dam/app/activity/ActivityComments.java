package dam.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import dam.app.R;
import dam.app.model.Comment;
import dam.app.model.Field;
import dam.app.recycler.CommentRecycler;
import rx.Subscriber;

public class ActivityComments extends ActivityMain {
    protected RecyclerView recyclerView;
    protected Button btnMakeOpinion;
    protected Field field;
    protected TextView lblNameField;
    protected Spinner spinnerCommentsOptions;

    private Subscriber<List<Comment>> subscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments_recycler);
        createDrawable();
        _CONTEXT = this;

        recyclerView = findViewById(R.id.recyclerComments);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new CommentRecycler((ActivityComments) _CONTEXT, new ArrayList<>()));

        field = (Field) getIntent().getExtras().getSerializable("field");

        subscription = _REPOSITORY.getCommentsSubscriber(recyclerView, field.getId()); //ToDo ActivityComments ver por qué no carga la lista la primera que entras a la vista

        lblNameField = findViewById(R.id.lblNameField);
        lblNameField.setText(field.getName());

        spinnerCommentsOptions = findViewById(R.id.spinnerCommentsOptions);
        spinnerCommentsOptions.setAdapter(new ArrayAdapter<>(this, R.layout.spinner_layout, getResources().getStringArray(R.array.optionsComments)));

        btnMakeOpinion = findViewById(R.id.btnMakeOpinion);
        btnMakeOpinion.setOnClickListener(v -> {
            Intent makeReviewScreen = new Intent(_CONTEXT, ActivityNewComment.class);
            makeReviewScreen.putExtra("addButtonAsk", true);
            startActivity(makeReviewScreen);
            Log.d("on ActivityComments", _CONTEXT.getResources().getString(R.string.activity_new_comment));
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.menu_option_fields:
                //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_frame,new first()).commit();
                break;
            case R.id.menu_option_reserves:
                //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_frame, new second()).commit();
                break;
            case R.id.menu_option_close_session:
                Snackbar.make(_CONTEXT.getWindow().getDecorView().getRootView(), _CONTEXT.getResources().getString(R.string.message_closing_session), Snackbar.LENGTH_LONG).show();

                break;
        }
        return true;
    }

    @Override
    public void onStop() {
        super.onStop();
        if(!subscription.isUnsubscribed()) subscription.unsubscribe();
    }
}


