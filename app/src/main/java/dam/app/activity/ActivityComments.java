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
import dam.app.model.Comment;
import dam.app.recycler.CommentRecycler;
import rx.Subscriber;

public class ActivityComments extends AppCompatActivity {
    RecyclerView recyclerView;
    CommentRecycler adapter;
    RecyclerView.LayoutManager layoutManager;
    AppRepository repository = null;
    ActivityComments context;

    Subscriber<List<Comment>> subscription;

    Spinner spinnerCommentsOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments_recycler);
        context = this;

        layoutManager = new LinearLayoutManager(this);
        adapter = new CommentRecycler(context, new ArrayList<>());

        recyclerView = findViewById(R.id.recyclerComments);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        repository = AppRepository.getInstance(this);
        subscription = repository.getCommentsSubscriber(recyclerView);

        spinnerCommentsOptions = findViewById(R.id.spinnerCommentsOptions);
        spinnerCommentsOptions.setAdapter(new ArrayAdapter<>(this, R.layout.spinner_layout, getResources().getStringArray(R.array.optionsComments)));

    }

    @Override
    public void onStop() {
        super.onStop();
        if(!subscription.isUnsubscribed()) subscription.unsubscribe();
        AppRepository.close();
    }
}


