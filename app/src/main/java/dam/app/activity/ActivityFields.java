package dam.app.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import dam.app.R;
import dam.app.extras.EnumSortOption;
import dam.app.model.Field;
import dam.app.recycler.FieldRecycler;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;



public class ActivityFields extends ActivityMain {
    protected RecyclerView recyclerView;
    protected Spinner spinnerOptionsFields;

    private static final int WRITE_DIRECTORY = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fields_recycler);
        createDrawable(this);

        if (ActivityCompat.checkSelfPermission(_CONTEXT, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(_CONTEXT, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_DIRECTORY);

        if(_REPOSITORY.isLogged()) setMenu(R.menu.menu_without_fields_with_session);
        else setMenu(R.menu.menu_without_fields_and_session);

        mAuth = FirebaseAuth.getInstance();
        signInAnonymously();//ToDo cambiar con el session

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
                    //case 4: setFields(EnumSortOption.PUNTUACION_BAJA); break;
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
        recyclerView.setAdapter(new FieldRecycler(new ArrayList<>(), _CONTEXT));
        _REPOSITORY.getAllFields(sortBy, recyclerView);

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

        _REPOSITORY.getAllFields(sortBy, recyclerView);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == WRITE_DIRECTORY){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) setFields(EnumSortOption.NOMBRE_ALFABETICO);
            else Toast.makeText(_CONTEXT, R.string.errorCamera, Toast.LENGTH_SHORT).show();
        }
    }
}

