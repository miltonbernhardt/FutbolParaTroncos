package dam.app.activity;

import android.os.Bundle;

import dam.app.R;

public class ActivityReserves extends ActivityMain {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserves);
        createDrawable(this);
    }

}
