package dam.app.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

import dam.app.R;
import dam.app.model.Field;
import dam.app.recycler.FieldRecycler;
import rx.Subscriber;

public class ActivityFields extends ActivityMain {
    protected RecyclerView recyclerView;
    protected Spinner spinnerOptionsFields;

    protected Subscriber<List<Field>> _SUBSCRIPTION;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fields_recycler);
        createDrawable();

        _CONTEXT = this;

        recyclerView = findViewById(R.id.recyclerFields);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new FieldRecycler(this, new ArrayList<>()));

        _SUBSCRIPTION = _REPOSITORY.getFieldsSubscriber(recyclerView);

        spinnerOptionsFields = findViewById(R.id.spinnerOptionsFields);
        spinnerOptionsFields.setAdapter(new ArrayAdapter<>(this, R.layout.spinner_layout, getResources().getStringArray(R.array.spinnerOptionsFields)));
    }

    /*@SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.menu_option_reserves:
                Intent makeReviewScreen = new Intent(_CONTEXT, ActivityReserves.class);
                startActivity(makeReviewScreen);
                Log.d("on DrawerLayout", _CONTEXT.getResources().getString(R.string.activity_reserves));
            case R.id.menu_option_close_session:
                //ToDo cerrar sessión
                Snackbar.make(recyclerView, _CONTEXT.getResources().getString(R.string.message_closing_session), Snackbar.LENGTH_SHORT).show();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finish();
                break;
        }

        return true;
    }*/

    @Override
    public void onStop() {
        super.onStop();
        if(!_SUBSCRIPTION.isUnsubscribed()) _SUBSCRIPTION.unsubscribe();
    }
}

