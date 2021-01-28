package dam.app.activity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import dam.app.R;
import dam.app.database.AppRepository;
import dam.app.model.Field;
import dam.app.recycler.FieldRecycler;
import rx.Subscriber;

public class ActivityFields extends AppCompatActivity {
    private RecyclerView recyclerView;

    private ActivityFields _CONTEXT;
    private AppRepository _REPOSITORY = null;
    private Subscriber<List<Field>> _SUBSCRIPTION;

    private Spinner spinnerOptionsFields;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fields_recycler);


        _CONTEXT = this;
        _REPOSITORY = AppRepository.getInstance(_CONTEXT);

        recyclerView = findViewById(R.id.recyclerFields);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new FieldRecycler(this, new ArrayList<>()));

        _SUBSCRIPTION = _REPOSITORY.getFieldsSubscriber(recyclerView);

        spinnerOptionsFields = findViewById(R.id.spinnerOptionsFields);
        spinnerOptionsFields.setAdapter(new ArrayAdapter<>(this, R.layout.spinner_layout, getResources().getStringArray(R.array.spinnerOptionsFields)));
    }

    @Override
    public void onStop() {
        super.onStop();
        if(!_SUBSCRIPTION.isUnsubscribed()) _SUBSCRIPTION.unsubscribe();
        AppRepository.close();
    }
}

