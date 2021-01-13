package dam.app.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import java.util.List;

import dam.app.model.Cancha;

public class CanchaAdapter extends ArrayAdapter<Cancha>  {
    public CanchaAdapter(@NonNull Context context, @NonNull List<Cancha> objects) {
        super(context, 0, objects);
    }
}
