package dam.app.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
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

        spinnerOptionsFields = findViewById(R.id.spinnerSortFields);
        spinnerOptionsFields.setAdapter(new ArrayAdapter<>(_CONTEXT, R.layout.spinner_layout, getResources().getStringArray(R.array.spinnerSortFields)));

        spinnerOptionsFields.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                switch (position){
                    case 0:
                        setFields(EnumSortOption.NOMBRE_ALFABETICO);
                        break;
                    case 1:
                        setFields(EnumSortOption.DIRECCION_CERCANA);
                        break;
                    case 2:
                        setFields(EnumSortOption.DIRECCION_LEJANA);
                        break;
                    case 3:
                        setFields(EnumSortOption.PUNTUACION_ALTA);
                        break;
                    case 4:
                        setFields(EnumSortOption.PUNTUACION_BAJA);
                        break;
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

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.menu_option_fields:
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.menu_option_reserves:
                Intent makeReviewScreen = new Intent(_CONTEXT, ActivityReserves.class);
                startActivity(makeReviewScreen);
                Log.d("on DrawerLayout", _CONTEXT.getResources().getString(R.string.activity_reserves));
                finish();
                break;
            case R.id.menu_option_close_session:
                //ToDo cerrar sessión
                Toast.makeText(_CONTEXT, R.string.message_closing_session, Toast.LENGTH_SHORT).show();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                this.finishAffinity();
                break;
        }
        return true;
    }

    @Override
    public void onStop() {
        super.onStop();
        if(!_SUBSCRIPTION.isUnsubscribed()) _SUBSCRIPTION.unsubscribe();
    }
}

