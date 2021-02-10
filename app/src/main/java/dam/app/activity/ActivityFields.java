package dam.app.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import dam.app.R;
import dam.app.extras.EnumSortOption;
import dam.app.model.Field;
import dam.app.recycler.FieldRecycler;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ActivityFields extends ActivityMain {
    protected RecyclerView recyclerView;
    protected Spinner spinnerOptionsFields;

    protected Subscription _SUBSCRIPTION;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fields_recycler);
        createDrawable(this);

        if(_REPOSITORY.isLogged()) setMenu(R.menu.menu_without_fields_with_session);
        else setMenu(R.menu.menu_without_fields_and_session);

        spinnerOptionsFields = findViewById(R.id.spinnerSortFields);
        spinnerOptionsFields.setAdapter(new ArrayAdapter<>(_CONTEXT, R.layout.spinner_layout, getResources().getStringArray(R.array.spinnerSortFields)));

        spinnerOptionsFields.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                switch (position){
                    case 0: setFields(EnumSortOption.NOMBRE_ALFABETICO);  break;
                    case 1: setFields(EnumSortOption.DIRECCION_CERCANA); break;
                    case 2: setFields(EnumSortOption.DIRECCION_LEJANA); break;
                    case 3: setFields(EnumSortOption.PUNTUACION_ALTA); break;
                    case 4: setFields(EnumSortOption.PUNTUACION_BAJA); break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) { }
        });
    }

    public void setFields(EnumSortOption sortBy) {
        recyclerView = findViewById(R.id.recyclerFields);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new FieldRecycler(_CONTEXT, new ArrayList<>()));

        Observable<List<Field>> observer = Observable.create(subscriber -> {
            subscriber.onNext(_REPOSITORY.getAllFields(sortBy));
            subscriber.onCompleted();
        });

        _SUBSCRIPTION = observer.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                fields -> recyclerView.setAdapter(new FieldRecycler(_CONTEXT, fields)),
                error -> Toast.makeText(_CONTEXT, R.string.failedOperation, Toast.LENGTH_LONG).show());
    }

    @Override
    public void onStop() {
        super.onStop();
        if(!_SUBSCRIPTION.isUnsubscribed()) _SUBSCRIPTION.unsubscribe();
    }
}

