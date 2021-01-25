package dam.app.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import dam.app.R;
import dam.app.database.AppRepository;
import dam.app.database.OnResultCallback;
import dam.app.database.VolatileData;
import dam.app.recycler.CommentRecycler;

public class ActivityComments extends AppCompatActivity implements OnResultCallback {
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    AppRepository repository = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments_recycler);

        recyclerView = findViewById(R.id.recyclerComments);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new CommentRecycler(this, VolatileData.getComments());
        recyclerView.setAdapter(adapter);

        //repository = AppRepository.getInstance(this.getApplicationContext(),this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_list_comments, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menuListComments) {
            //Intent registerScreen = new Intent(this, ActivityPedido.class);
            //startActivity(registerScreen);
            //Log.d("New", "ELIGIÓ NUEVO PEDIDO");
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResult(List result) {
        //ToDo ActivityComments hacer esta parte cuando se guarden comments en la bd
    }

    @Override
    public void onStop() {
        super.onStop();
        AppRepository.close();
    }
}


