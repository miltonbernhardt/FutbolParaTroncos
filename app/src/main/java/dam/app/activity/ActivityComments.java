package dam.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import dam.app.R;
import dam.app.model.Comment;
import dam.app.model.Field;
import dam.app.recycler.CommentRecycler;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ActivityComments extends ActivityMain {
    protected RecyclerView recyclerView;
    protected Button btnMakeOpinion;
    protected Field field;
    protected TextView lblNameField;
    protected Spinner spinnerCommentsOptions;

    private Subscription _SUBSCRIPTION;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments_recycler);
        createDrawable();
        _CONTEXT = this;

        field = (Field) getIntent().getExtras().getSerializable("field");

        setComments(field.getId());

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

    public void setComments(long id) {
        recyclerView = findViewById(R.id.recyclerComments);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new CommentRecycler((ActivityComments) _CONTEXT, new ArrayList<>()));

        Observable<List<Comment>> observer = Observable.create(subscriber -> {
            subscriber.onNext(_REPOSITORY.getCommentsFromField(id));
            subscriber.onCompleted();
        });

        _SUBSCRIPTION = observer.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                fields -> recyclerView.setAdapter(new CommentRecycler((ActivityComments) _CONTEXT, fields)) ,
                error -> Snackbar.make(recyclerView, _CONTEXT.getResources().getString(R.string.failedOperation), Snackbar.LENGTH_LONG).show());
    }

    @Override
    public void onStop() {
        super.onStop();
        if(!_SUBSCRIPTION.isUnsubscribed()) _SUBSCRIPTION.unsubscribe();
    }
}


