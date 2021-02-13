package dam.app.activity;

import android.os.Bundle;

import dam.app.R;

public class ActivityReserves extends ActivityMain {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserves_recycler);
        createDrawable(this);
        if(_REPOSITORY.isLogged()) setMenu(R.menu.menu_without_reserves_with_session);
        else setMenu(R.menu.menu_without_reserves_and_session);
    }

}
