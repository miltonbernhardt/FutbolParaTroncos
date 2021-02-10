package dam.app.recycler;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.util.Log;
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

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;

import dam.app.R;
import dam.app.activity.ActivityComments;
import dam.app.activity.ActivityMain;
import dam.app.activity.ActivityNewReserve;
import dam.app.database.AppRepository;
import dam.app.model.Field;

public class FieldRecycler extends RecyclerView.Adapter<FieldRecycler.FieldHolder> {

    private final ActivityMain _CONTEXT;
    private final List<Field> list;
    private final DecimalFormat df = new DecimalFormat("##.#");


    public FieldRecycler(ActivityMain _CONTEXT, List<Field> list){
        this.list = list;
        this._CONTEXT = _CONTEXT;
    }

    public static class FieldHolder extends RecyclerView.ViewHolder {
        ImageButton btnLocate;
        Button btnReserve;
        Button btnReviews;
        CardView cardViewField;
        ImageView imageField;
        RatingBar ratingBarField;
        TextView lblAddressFieldRow;
        TextView lblRatingFieldRow;
        TextView lblTitleFieldRow;

        Field field;

        public FieldHolder(@NonNull View v) {
            super(v);
            btnLocate = v.findViewById(R.id.btnLocate);
            btnReserve = v.findViewById(R.id.btnReserve);
            cardViewField = v.findViewById(R.id.cardViewField);
            btnReviews = v.findViewById(R.id.btnOpinions);
            imageField = v.findViewById(R.id.imageField);
            ratingBarField = v.findViewById(R.id.ratingBarFieldRow);
            LayerDrawable stars = (LayerDrawable) ratingBarField.getProgressDrawable();
            stars.getDrawable(0).setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);//las estrellas vacias
            lblAddressFieldRow = v.findViewById(R.id.lblAddressFieldRow);
            lblRatingFieldRow = v.findViewById(R.id.lblRatingFieldRow);
            lblTitleFieldRow = v.findViewById(R.id.lblTitleFieldRow);
        }
    }

    @NonNull
    public FieldHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_field, parent, false);
        return new FieldHolder(view);
    }

    public void onBindViewHolder(@NonNull final FieldHolder holder, int position) {
        holder.btnLocate.setTag(position);
        holder.btnReserve.setTag(position);
        holder.btnReviews.setTag(position);
        holder.cardViewField.setTag(position);
        holder.imageField.setTag(position);
        holder.lblAddressFieldRow.setTag(position);
        holder.lblRatingFieldRow.setTag(position);
        holder.lblTitleFieldRow.setTag(position);
        holder.ratingBarField.setTag(position);

        //ToDo FieldRecycler cambiar esto cuando se haya guardado bien las imágenes
        holder.field = list.get(position);
        //Si está o no en la bd
        switch (holder.field.getImageUUID()){
            default:
            case "":
                holder.imageField.setImageResource(R.mipmap.image_no_image_available);
                break;
            case "DebugExampleTwoFragment":
                holder.imageField.setImageResource(R.mipmap.image_field_a);
                break;
            case "b":
                holder.imageField.setImageResource(R.mipmap.image_field_b);
                break;
            case "c":
                holder.imageField.setImageResource(R.mipmap.image_field_c);
                break;
            case "d":
                holder.imageField.setImageResource(R.mipmap.image_field_d);
                break;
        }

        holder.lblAddressFieldRow.setText(holder.field.getAddress());
        df.setRoundingMode(RoundingMode.DOWN);
        holder.lblRatingFieldRow.setText(df.format(holder.field.getRating()));
        holder.lblTitleFieldRow.setText(holder.field.getName());
        holder.ratingBarField.setRating(holder.field.getRating());

        holder.btnLocate.setOnClickListener(view -> { //ToDo JUANI - MAPS agregar función cuando se implemente lo del maps
        });

        holder.btnReserve.setOnClickListener(view -> {
            //ToDo SESSION cuando se implemente lo de session, solo permitir reservar DebugExampleTwoFragment alguien logueado o mostrar un dialogo para que se loguee si quiere comentar
            if(_CONTEXT._REPOSITORY.isLogged()){
                _CONTEXT.startActivity(new Intent(_CONTEXT, ActivityNewReserve.class));
            }
            else{
                _CONTEXT.showDialog(R.string.user_not_logged, R.string.wish_to_log_for_comment);
            }
        });

        holder.btnReviews.setOnClickListener(view -> {
            Intent intent = new Intent(_CONTEXT, ActivityComments.class);
            intent.putExtra("idField", list.get(holder.getAdapterPosition()).getId());
            intent.putExtra("nameField", list.get(holder.getAdapterPosition()).getName());
            _CONTEXT.setResult(Activity.RESULT_OK, intent);
            _CONTEXT.startActivity(intent);
            Log.d("on ActivityFields", _CONTEXT.getResources().getString(R.string.activity_comments));
            //_CONTEXT.finish();
        });

    }

    public int getItemCount() {
        return list.size();
    }
}
