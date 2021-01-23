package dam.app.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import dam.app.R;
import dam.app.model.Field;

public class FieldAdapter extends ArrayAdapter<Field> {

    public FieldAdapter(@NonNull Context context, @NonNull List<Field> objects) {
        super(context,0, objects);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        final Field field = super.getItem(position);
        View fieldView;
        LayoutInflater inflater = LayoutInflater.from(this.getContext());

        if(convertView == null)
            fieldView = inflater.inflate(R.layout.row_field, parent,false);
        else
            fieldView = convertView;
        FieldHolder fieldHolder = (FieldHolder) fieldView.getTag();

        if(fieldHolder == null){
            fieldHolder = new FieldHolder(fieldView);
            fieldView.setTag(fieldHolder);
        }

        //ToDo setear bien los campos cuando se termine la vista row_field
        //fieldHolder.cantidadPlato.setText("x1");
        //fieldHolder.nombrePlato.setText(field.getTitle());
        //fieldHolder.precioPlato.setText("$ "+String.format("%.2f", plato.getPrice()));

        return fieldView;
    }

    public class FieldHolder {
        TextView cantidadPlato;
        TextView nombrePlato;
        TextView precioPlato;

        public FieldHolder(View v){
            //ToDo setear bien los campos cuando se termine la vista row_field
            /*cantidadPlato = v.findViewById(R.id.rowCantidadPlato);
            nombrePlato = v.findViewById(R.id.rowNombrePlato);
            precioPlato = v.findViewById(R.id.rowPrecioPlato);*/
        }
    }

    public void addField(Field field){
        super.add(field);
        super.notifyDataSetChanged();
    }
}
