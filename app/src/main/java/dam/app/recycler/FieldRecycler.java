package dam.app.recycler;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
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

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;

import dam.app.R;
import dam.app.activity.ActivityComments;
import dam.app.activity.ActivityMain;
import dam.app.activity.ActivityNewReserve;
import dam.app.activity.ActivityMaps;
import dam.app.extras.EnumPaths;
import dam.app.extras.ImageHelper;
import dam.app.model.Field;

public class FieldRecycler extends RecyclerView.Adapter<FieldRecycler.FieldHolder> {

    private final ActivityMain _CONTEXT;
    private final List<Field> list;
    private final DecimalFormat df = new DecimalFormat("##.#");


    public FieldRecycler(List<Field> list, ActivityMain _CONTEXT){
        this.list = list;
        this._CONTEXT = _CONTEXT;
    }

    public static class FieldHolder extends RecyclerView.ViewHolder {
        Field field;

        ImageButton btnLocate;
        Button btnReserve;
        Button btnReviews;
        CardView cardViewField;
        ImageView imageField;
        RatingBar ratingBarField;
        TextView lblAddressFieldRow;
        TextView lblRatingFieldRow;
        TextView lblTitleFieldRow;

        public FieldHolder(@NonNull View v) {
            super(v);
            btnLocate = v.findViewById(R.id.btnLocate);
            btnReserve = v.findViewById(R.id.btnReserve);
            cardViewField = v.findViewById(R.id.cardViewField);
            btnReviews = v.findViewById(R.id.btnOpinions);
            imageField = v.findViewById(R.id.imageField);
            ratingBarField = v.findViewById(R.id.ratingBarFieldRow);
            LayerDrawable stars = (LayerDrawable) ratingBarField.getProgressDrawable();
            stars.getDrawable(0).setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
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

        holder.field = list.get(position);

        String path = holder.field.getImagePath();
        if(path != null && !path.equals("")) {

            Bitmap bitmapImage = BitmapFactory.decodeFile(holder.field.getImagePath());

            if(bitmapImage != null)
                holder.imageField.setImageBitmap(bitmapImage);
            else{
                Uri file = Uri.fromFile(new File(path));
                StorageReference islandRef = FirebaseStorage.getInstance().getReference().child(EnumPaths.PATH_FIELDS+file.getLastPathSegment());
                File localFile = null;
                try {
                    localFile = File.createTempFile("images", "tmp");
                } catch (IOException ignore) { }

                File finalLocalFile = localFile;
                islandRef.getFile(localFile).addOnSuccessListener(taskSnapshot -> {
                    Bitmap bitmap = BitmapFactory.decodeFile(finalLocalFile.getAbsolutePath());
                    holder.imageField.setImageBitmap(bitmap);

                    ImageHelper.persistImage(bitmap, file.getLastPathSegment(), _CONTEXT, "", EnumPaths.PATH_FIELDS.toString());
                });
            }
        }

        holder.lblAddressFieldRow.setText(holder.field.getAddress());
        df.setRoundingMode(RoundingMode.DOWN);
        holder.lblRatingFieldRow.setText(df.format(holder.field.getRating()));
        holder.lblTitleFieldRow.setText(holder.field.getName());
        holder.ratingBarField.setRating(holder.field.getRating());

        holder.btnLocate.setOnClickListener(view -> {
            Intent i= new Intent(_CONTEXT, ActivityMaps.class);
            i.putExtra("positionX",list.get(holder.getAdapterPosition()).getPositionX());
            i.putExtra("positionY",list.get(holder.getAdapterPosition()).getPositionY());
            i.putExtra("fieldName",list.get(holder.getAdapterPosition()).getName());
            _CONTEXT.startActivity(i);
        });

        holder.btnReserve.setOnClickListener(view -> {
            //ToDo SESSION cuando se implemente lo de session, solo permitir reservar a alguien logueado o mostrar un dialogo para que se loguee si quiere comentar
            if(_CONTEXT._FIREBASE.isLogged()){
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
        });

    }

    public int getItemCount() {
        return list.size();
    }
}
