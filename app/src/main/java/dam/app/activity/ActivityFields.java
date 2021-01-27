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
    RecyclerView recyclerView;
    FieldRecycler adapter;
    RecyclerView.LayoutManager layoutManager;
    AppRepository repository = null;
    Subscriber<List<Field>> subscription;

    Spinner spinnerOptionsFields;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fields_recycler);

        layoutManager = new LinearLayoutManager(this);
        adapter = new FieldRecycler(this, new ArrayList<>());
        recyclerView = findViewById(R.id.recyclerFields);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        repository = AppRepository.getInstance(this);
        subscription = repository.getFieldsSubscriber(recyclerView);

        spinnerOptionsFields = findViewById(R.id.spinnerOptionsFields);
        spinnerOptionsFields.setAdapter(new ArrayAdapter<>(this, R.layout.spinner_layout, getResources().getStringArray(R.array.spinnerOptionsFields)));
    }

    @Override
    public void onStop() {
        super.onStop();
        if(!subscription.isUnsubscribed()) subscription.unsubscribe();
        AppRepository.close();
    }
}

