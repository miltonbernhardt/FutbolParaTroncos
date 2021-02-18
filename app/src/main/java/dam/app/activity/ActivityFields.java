package dam.app.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import dam.app.R;
import dam.app.extras.EnumSortOption;
import dam.app.adapters.FieldRecycler;

public class ActivityFields extends ActivityMain {
    RecyclerView recyclerFields;
    Spinner spinnerSortFields;

    private static final int WRITE_DIRECTORY = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fields_recycler);
        createDrawable(this, true);

        if (ActivityCompat.checkSelfPermission(_CONTEXT, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(_CONTEXT, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_DIRECTORY);

        spinnerSortFields = findViewById(R.id.spinnerSortFields);
        spinnerSortFields.setAdapter(new ArrayAdapter<>(_CONTEXT, R.layout.spinner_layout, getResources().getStringArray(R.array.spinnerSortFields)));

        spinnerSortFields.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                switch (position){
                    case 0: setFields(EnumSortOption.PUNTUACION_ALTA); break;
                    case 1: setFields(EnumSortOption.PUNTUACION_BAJA); break;
                    case 2: setFields(EnumSortOption.NOMBRE_ALFABETICO);  break;
                    case 3: setFields(EnumSortOption.DIRECCION_CERCANA); break;
                    case 4: setFields(EnumSortOption.DIRECCION_LEJANA); break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) { }
        });

        recyclerFields = findViewById(R.id.recyclerFields);
        recyclerFields.setHasFixedSize(true);
        recyclerFields.setLayoutManager(new LinearLayoutManager(this));
        recyclerFields.setAdapter(new FieldRecycler(new ArrayList<>(), _CONTEXT));
    }

    public void setFields(EnumSortOption sortBy) {
        _FIREBASE.setFieldsRecyclerView(sortBy, recyclerFields);
        /* - Técnicamente no se necesitaría de Observables y Subscriptions, porque la consulta de los datos a fireba se hace en segundo plano.
           - Esto quedo de haber implementado en un principio ROOM
        Observable<List<Field>> observer = Observable.create(subscriber -> {
            subscriber.onNext(_REPOSITORY.getAllFields(sortBy, recyclerView));
            subscriber.onCompleted();
        });

        _SUBSCRIPTION = observer.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                fields -> recyclerView.setAdapter(new FieldRecycler(_CONTEXT, fields)),
                error -> Toast.makeText(_CONTEXT, R.string.failedOperation, Toast.LENGTH_LONG).show());
         */
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == WRITE_DIRECTORY){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) setFields(EnumSortOption.NOMBRE_ALFABETICO);
            else Toast.makeText(_CONTEXT, R.string.errorCamera, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        navigationView.getMenu().getItem(0).setVisible(false);
        if (_FIREBASE.isLogged()){
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

