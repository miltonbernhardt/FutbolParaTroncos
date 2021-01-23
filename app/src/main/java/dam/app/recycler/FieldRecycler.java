package dam.app.recycler;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import dam.app.R;
import dam.app.activity.ActivityFields;
import dam.app.model.Field;

public class FieldRecycler extends RecyclerView.Adapter<FieldRecycler.FieldHolder> {

    private final ActivityFields activityFields;
    private final List<Field> fieldList;

    public FieldRecycler(ActivityFields activityFields, List<Field> fieldListParameter){
        this.activityFields = activityFields;
        this.fieldList = fieldListParameter;
    }

    public static class FieldHolder extends RecyclerView.ViewHolder {
        ImageButton btnLocate;
        Button btnReserve;
        Button btnReviews;
        CardView cardViewField;
        ImageView imageField;
        RatingBar ratingBarField;
        TextView lblTitleFieldRow;

        Field field;

        public FieldHolder(@NonNull View v) {
            super(v);
            btnLocate = v.findViewById(R.id.btnLocate);
            btnReserve = v.findViewById(R.id.btnReserve);
            cardViewField = v.findViewById(R.id.cardViewField);
            btnReviews = v.findViewById(R.id.btnReviews);
            imageField = v.findViewById(R.id.imageField);
            ratingBarField = v.findViewById(R.id.ratingBarFieldRow);
            LayerDrawable stars = (LayerDrawable) ratingBarField.getProgressDrawable();
            //ToDo FielHolder PONERLE BORDE
            stars.getDrawable(2).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);
            stars.getDrawable(0).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);
            stars.getDrawable(1).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);
            lblTitleFieldRow = v.findViewById(R.id.lblTitleFieldRow);
        }
    }

    @NonNull
    public FieldHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_field, parent, false);
        return new FieldHolder(view);
    }

    public void onBindViewHolder(@NonNull final FieldHolder fieldHolder, int position) {
        fieldHolder.btnLocate.setTag(position);
        fieldHolder.btnReserve.setTag(position);
        fieldHolder.btnReviews.setTag(position);
        fieldHolder.cardViewField.setTag(position);
        fieldHolder.imageField.setTag(position);
        fieldHolder.lblTitleFieldRow.setTag(position);
        fieldHolder.ratingBarField.setTag(position);

        //ToDo FieldRecycler setear todo de acuerdo a lo que haya en la base de datos
        fieldHolder.field = fieldList.get(position);
        //Si está o no en la bd
        if(true) fieldHolder.imageField.setImageResource(R.drawable.f5field);
        else fieldHolder.imageField.setImageResource(R.drawable.no_image_avaible);

        fieldHolder.lblTitleFieldRow.setText(R.string.lblTitleFieldRow);
        fieldHolder.ratingBarField.setRating(3.5f);


        fieldHolder.btnLocate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Plato plato = plateList.get(plateHolder.getAdapterPosition());
                Intent intent = new Intent(activityPlateRecycler, ActivityPedido.class);
                intent.putExtra("plato", plato);
                activityPlateRecycler.getIntent().getSerializableExtra("plato");
                activityPlateRecycler.setResult(Activity.RESULT_OK, intent);
                activityPlateRecycler.finish();*/
            }
        });

        fieldHolder.btnReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {}
        });

        fieldHolder.btnReviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {}
        });

    }

    public int getItemCount() {
        return fieldList.size();
    }
}
