package dam.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import dam.app.R;
import dam.app.extras.EnumSortOption;
import dam.app.model.Comment;
import dam.app.recycler.CommentRecycler;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ActivityComments extends ActivityMain {
    protected RecyclerView recyclerView;
    protected Button btnMakeOpinion;
    protected TextView lblNameField;
    protected Spinner spinnerSortComments;

    private Subscription _SUBSCRIPTION;

    private long idField;
    private String fieldName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments_recycler);
        createDrawable(this);

        idField = getIntent().getLongExtra("idField", -1);
        fieldName = getIntent().getStringExtra("nameField");

        lblNameField = findViewById(R.id.lblNameField);
        lblNameField.setText(fieldName);

        spinnerSortComments = findViewById(R.id.spinnerSortComments);
        spinnerSortComments.setAdapter(new ArrayAdapter<>(_CONTEXT, R.layout.spinner_layout, getResources().getStringArray(R.array.spinnerSortComments)));

        spinnerSortComments.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                switch (position){
                    case 0: setComments(idField, EnumSortOption.FECHA_CERCANA); break;
                    case 1: setComments(idField, EnumSortOption.FECHA_LEJANA); break;
                    case 2: setComments(idField, EnumSortOption.PUNTUACION_ALTA); break;
                    case 3: setComments(idField, EnumSortOption.PUNTUACION_BAJA); break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) { }
        });

        btnMakeOpinion = findViewById(R.id.btnMakeOpinion);
        btnMakeOpinion.setOnClickListener(v -> {
            if(_REPOSITORY.isLogged()){
                //ToDo SESSION cuando se implemente lo de session, solo permitir comentar DebugExampleTwoFragment alguien logueado o mostrar un dialogo para que se loguee si quiere comentar
                Intent intent = new Intent(_CONTEXT, ActivityNewComment.class);
                intent.putExtra("idReserve", idField);
                intent.putExtra("fieldTitle", fieldName);
                setResult(Activity.RESULT_OK, intent);
                startActivity(intent);
                Log.d("on ActivityComments", _CONTEXT.getResources().getString(R.string.activity_new_comment));
            }
            else showDialog(R.string.user_not_logged, R.string.wish_to_log_for_comment);
        });
    }

    public void setComments(long idField, EnumSortOption sortBy) {
        recyclerView = findViewById(R.id.recyclerComments);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new CommentRecycler(new ArrayList<>()));

        Observable<List<Comment>> observer = Observable.create(subscriber -> {
            subscriber.onNext(_REPOSITORY.getCommentsFromField(idField, sortBy));
            subscriber.onCompleted();
        });

        _SUBSCRIPTION = observer.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                fields -> recyclerView.setAdapter(new CommentRecycler(fields)) ,
                error -> Toast.makeText(_CONTEXT, R.string.failedOperation, Toast.LENGTH_LONG).show());
    }

    @Override
    public void onStop() {
        super.onStop();
        if(!_SUBSCRIPTION.isUnsubscribed()) _SUBSCRIPTION.unsubscribe();
    }
}


