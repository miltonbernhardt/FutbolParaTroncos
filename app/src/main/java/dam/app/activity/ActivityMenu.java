package dam.app.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import dam.app.R;
import dam.app.database.AppRepository;

public class ActivityMenu extends AppCompatActivity {

    private ActivityMenu _CONTEXT;
    private AppRepository _REPOSITORY = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        _CONTEXT = this;
        _REPOSITORY = AppRepository.getInstance(_CONTEXT);
    }

    @Override
    public void onStop() {
        super.onStop();
        AppRepository.close();
    }
}
