package dam.app.activity;

import android.os.Bundle;

import dam.app.R;

public class ActivityNewReserve extends ActivityMain  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_reserve);
        createDrawable(this);
    }

}