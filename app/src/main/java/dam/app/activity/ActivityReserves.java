package dam.app.activity;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import dam.app.R;
import dam.app.recycler.ReservesRecycler;

public class ActivityReserves extends ActivityMain {
    RecyclerView recyclerReserves;

    private String idUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserves_recycler);
        createDrawable(this, true);

        setMenu(R.menu.menu_without_reserves_with_session);

        idUser = getIntent().getStringExtra("idUser");

        recyclerReserves = findViewById(R.id.recyclerReserves);
        recyclerReserves.setHasFixedSize(true);
        recyclerReserves.setLayoutManager(new LinearLayoutManager(this));
        recyclerReserves.setAdapter(new ReservesRecycler(new ArrayList<>(), _CONTEXT));
        _FIREBASE.getReservesByUser(idUser, recyclerReserves);
    }
}
