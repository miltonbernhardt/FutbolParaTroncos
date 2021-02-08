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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import dam.app.R;
import dam.app.model.Field;
import dam.app.recycler.FieldRecycler;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ActivityFields extends ActivityMain {
    protected RecyclerView recyclerView;
    protected Spinner spinnerOptionsFields;
    protected Spinner spinnerSortOptionsFields;

    protected Subscription _SUBSCRIPTION;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fields_recycler);
        createDrawable();
        _CONTEXT = this;

        spinnerSortOptionsFields = findViewById(R.id.spinnerSortOptionsFields);
        spinnerOptionsFields = findViewById(R.id.spinnerOptionsFields);
        spinnerOptionsFields.setAdapter(new ArrayAdapter<>(_CONTEXT, R.layout.spinner_layout, getResources().getStringArray(R.array.spinnerFieldsOptions)));

        spinnerOptionsFields.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String order = ((TextView)selectedItemView).getText().toString();
                if(order.equals("CERCANÍA"))spinnerSortOptionsFields.setAdapter(new ArrayAdapter<>(_CONTEXT, R.layout.spinner_layout, getResources().getStringArray(R.array.spinnerSortOptionProximity)));
                else{
                    if(order.equals("PUNTUACIÓN")) spinnerSortOptionsFields.setAdapter(new ArrayAdapter<>(_CONTEXT, R.layout.spinner_layout, getResources().getStringArray(R.array.spinnerSortOptionScore)));
                    else spinnerSortOptionsFields.setAdapter(new ArrayAdapter<>(_CONTEXT, R.layout.spinner_layout, getResources().getStringArray(R.array.spinnerSortOptionName)));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) { }
        });

        spinnerSortOptionsFields.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                boolean asc = false;
                if(position == 1) asc = true;
                setFields(spinnerSortOptionsFields.getSelectedItem().toString(), asc);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) { }
        });
    }

    public void setFields(String sortBy, boolean asc) {
        recyclerView = findViewById(R.id.recyclerFields);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new FieldRecycler(this, new ArrayList<>()));

        Observable<List<Field>> observer = Observable.create(subscriber -> {
            subscriber.onNext(_REPOSITORY.getAllFields(sortBy, asc));
            subscriber.onCompleted();
        });

        _SUBSCRIPTION = observer.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                fields -> recyclerView.setAdapter(new FieldRecycler((ActivityFields) _CONTEXT, fields)),
                error -> Snackbar.make(recyclerView, _CONTEXT.getResources().getString(R.string.failedOperation), Snackbar.LENGTH_LONG).show());
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
                break;
            case R.id.menu_option_close_session:
                //ToDo cerrar sessión
                Snackbar.make(recyclerView, _CONTEXT.getResources().getString(R.string.message_closing_session), Snackbar.LENGTH_SHORT).show();
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

