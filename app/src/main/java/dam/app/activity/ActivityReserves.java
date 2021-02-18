package dam.app.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import dam.app.R;
import dam.app.adapters.ReservesRecycler;

public class ActivityReserves extends ActivityMain {
    RecyclerView recyclerReserves;
    TextView lblFieldName;

    private String idField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserves_recycler);

        idField = getIntent().getStringExtra("idField");
        createDrawable(this, idField == null);

        recyclerReserves = findViewById(R.id.recyclerReserves);
        recyclerReserves.setHasFixedSize(true);
        recyclerReserves.setLayoutManager(new LinearLayoutManager(this));
        recyclerReserves.setAdapter(new ReservesRecycler(new ArrayList<>(), _CONTEXT));

        if(idField != null){
            String nameField = getIntent().getStringExtra("nameField");
            lblFieldName = findViewById(R.id.lblFieldName);
            lblFieldName.setVisibility(View.VISIBLE);
            lblFieldName.setText(nameField);
            _FIREBASE.setReservesRecyclerViewByField(recyclerReserves, idField);
        }
        else _FIREBASE.setReservesRecyclerView(recyclerReserves);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        navigationView.getMenu().getItem(1).setVisible(false);
        navigationView.getMenu().getItem(2).setVisible(false);
        navigationView.getMenu().getItem(3).setVisible(false);
        return true;
    }
}
