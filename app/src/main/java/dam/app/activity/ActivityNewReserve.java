package dam.app.activity;

import android.os.Bundle;

import dam.app.R;

public class ActivityNewReserve extends ActivityMain  {

    private String idField;
    private String idReserve;
    private String fieldName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_reserve);
        createDrawable(this, false);

        idField = getIntent().getStringExtra("idField");
        idReserve = getIntent().getStringExtra("idReserve");
        fieldName = getIntent().getStringExtra("fieldName");

    }

}