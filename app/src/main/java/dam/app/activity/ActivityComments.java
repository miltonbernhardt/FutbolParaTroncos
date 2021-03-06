package dam.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
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

import dam.app.R;
import dam.app.extras.EnumSortOption;
import dam.app.adapters.CommentRecycler;

public class ActivityComments extends ActivityMain {
    RecyclerView recyclerView;
    Button btnMakeOpinion;
    TextView lblNameField;
    Spinner spinnerSortComments;

    private String idField;
    private String nameField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments_recycler);
        createDrawable(this, false);

        idField = getIntent().getStringExtra("idField");
        nameField = getIntent().getStringExtra("nameField");

        lblNameField = findViewById(R.id.lblNameFieldValue);
        lblNameField.setText(nameField);

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
            if(_FIREBASE.isLogged()){
                Intent intent = new Intent(_CONTEXT, ActivityReserves.class);
                intent.putExtra("idField", idField);
                intent.putExtra("nameField", nameField);
                setResult(Activity.RESULT_OK, intent);
                startActivity(intent);
                Log.d("on ActivityComments", _CONTEXT.getResources().getString(R.string.activity_new_comment));
            }
            else Toast.makeText(_CONTEXT, R.string.user_not_logged, Toast.LENGTH_LONG).show();
        });

        recyclerView = findViewById(R.id.recyclerComments);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new CommentRecycler(new ArrayList<>(), _CONTEXT));
    }

    public void setComments(String idField, EnumSortOption sortBy) {
        _FIREBASE.setCommentsRecyclerView(idField, sortBy, recyclerView);
        /*  - Técnicamente no se necesitaría de Observables y Subscriptions, porque la consulta de los datos a fireba se hace en segundo plano.
            - Esto quedo de haber implementado en un principio ROOM

        Observable<List<Comment>> observer = Observable.create(subscriber -> {
            subscriber.onNext(_REPOSITORY.getCommentsFromField(idField, sortBy, recyclerView));
            subscriber.onCompleted();
        });

        _SUBSCRIPTION = observer.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                fields -> recyclerView.setAdapter(new CommentRecycler(fields, _CONTEXT)) ,
                error -> Toast.makeText(_CONTEXT, R.string.failedOperation, Toast.LENGTH_LONG).show());
        */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        if(_FIREBASE.isLogged()) {
            navigationView.getMenu().getItem(2).setVisible(false);
            navigationView.getMenu().getItem(3).setVisible(false);
        }
        else{
            navigationView.getMenu().getItem(1).setVisible(false);
            navigationView.getMenu().getItem(4).setVisible(false);
        }
        return true;
    }
}


