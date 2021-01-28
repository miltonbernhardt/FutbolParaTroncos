package dam.app.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import dam.app.R;
import dam.app.database.AppRepository;

public class ActivityReserves  extends AppCompatActivity {

    private ActivityReserves _CONTEXT;
    private AppRepository _REPOSITORY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserves);

        _CONTEXT = this;
        _REPOSITORY = AppRepository.getInstance(_CONTEXT);
    }

    @Override
    public void onStop() {
        super.onStop();
        AppRepository.close();
    }
}
