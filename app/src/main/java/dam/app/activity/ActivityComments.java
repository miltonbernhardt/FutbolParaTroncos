package dam.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
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
    protected Spinner spinnerCommentsSortOptions;

    private Subscription _SUBSCRIPTION;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments_recycler);
        createDrawable();
        _CONTEXT = this;

        field = (Field) getIntent().getExtras().getSerializable("field");

        lblNameField = findViewById(R.id.lblNameField);
        lblNameField.setText(field.getName());

        spinnerCommentsOptions = findViewById(R.id.spinnerCommentsOptions);
        spinnerCommentsOptions.setAdapter(new ArrayAdapter<>(_CONTEXT, R.layout.spinner_layout, getResources().getStringArray(R.array.spinnerCommentsOptions)));
        spinnerCommentsSortOptions = findViewById(R.id.spinnerCommentsSortOptions);

        spinnerCommentsOptions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if(((TextView)selectedItemView).getText().equals("PUNTUACIÓN")) spinnerCommentsSortOptions.setAdapter(new ArrayAdapter<>(_CONTEXT, R.layout.spinner_layout, getResources().getStringArray(R.array.spinnerSortOptionScore)));
                else spinnerCommentsSortOptions.setAdapter(new ArrayAdapter<>(_CONTEXT, R.layout.spinner_layout, getResources().getStringArray(R.array.spinnerSortOptionDate)));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) { }
        });

        spinnerCommentsSortOptions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                boolean asc = false;
                if(position == 1) asc = true;
                setComments(field.getId(), spinnerCommentsSortOptions.getSelectedItem().toString(), asc);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) { }
        });

        btnMakeOpinion = findViewById(R.id.btnMakeOpinion);
        btnMakeOpinion.setOnClickListener(v -> {
            if(_REPOSITORY.isLogged()){
                //ToDo SESSION cuando se implemente lo de session, solo permitir comentar DebugExampleTwoFragment alguien logueado o mostrar un dialogo para que se loguee si quiere comentar
                Intent makeReviewScreen = new Intent(_CONTEXT, ActivityNewComment.class);
                startActivity(makeReviewScreen);
                Log.d("on ActivityComments", _CONTEXT.getResources().getString(R.string.activity_new_comment));
            }
            else{
                showDialog(getResources().getString(R.string.user_not_logged), getResources().getString(R.string.wish_to_log_for_comment));
            }

        });
    }

    public void setComments(long idField, String sortBy, boolean asc) {
        recyclerView = findViewById(R.id.recyclerComments);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new CommentRecycler((ActivityComments) _CONTEXT, new ArrayList<>()));

        Observable<List<Comment>> observer = Observable.create(subscriber -> {
            subscriber.onNext(_REPOSITORY.getCommentsFromField(idField, sortBy, asc));
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


