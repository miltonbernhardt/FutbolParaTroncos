package dam.app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import dam.app.R;
import dam.app.activity.ActivityFields;
import dam.app.model.Field;

public class FieldRecycler extends RecyclerView.Adapter<FieldRecycler.ViewHolderField> {

    private final ActivityFields activityFields;
    private final List<Field> fieldList;

    public FieldRecycler(ActivityFields activityFields, List<Field> fieldListParameter){
        this.activityFields = activityFields;
        this.fieldList = fieldListParameter;
    }

    public static class ViewHolderField extends RecyclerView.ViewHolder {
        //ToDo setear bien los campos cuando se termine la vista row_field
        /*ImageView plateImage;
        CardView cardViewPlate;
        TextView textTitle;
        Button btnVer;*/

        public ViewHolderField(@NonNull View itemView) {
            super(itemView);
        }
    }

    @NonNull
    public ViewHolderField onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_field, parent, false);
        return new ViewHolderField(view);
    }

    public void onBindViewHolder(@NonNull final ViewHolderField fieldHolder, int position) {
        //ToDo setear bien los campos cuando se termine la vista row_field
       /* plateHolder.plate = plateList.get(position);
        plateHolder.cardViewPlate.setTag(position);

        plateHolder.textTitle.setTag(position);
        plateHolder.textTitle.setText(plateHolder.plate.getTitle());
        plateHolder.textPrice.setTag(position);
        plateHolder.textPrice.setText("Precio: "+plateHolder.plate.getPrice().toString()+"$");
        plateHolder.btnVer.setTag(position);
        plateHolder.btnPedirPlato.setTag(position);
        plateHolder.btnPedirPlato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Plato plato = plateList.get(plateHolder.getAdapterPosition());
                Intent intent = new Intent(activityPlateRecycler, ActivityPedido.class);
                intent.putExtra("plato", plato);
                activityPlateRecycler.getIntent().getSerializableExtra("plato");
                activityPlateRecycler.setResult(Activity.RESULT_OK, intent);
                activityPlateRecycler.finish();
            }
        });*/
    }

    public int getItemCount() {
        return fieldList.size();
    }
}
