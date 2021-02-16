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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.List;

import dam.app.R;
import dam.app.activity.ActivityComments;
import dam.app.activity.ActivityMaps;
import dam.app.activity.ActivityNewComment;
import dam.app.activity.ActivityNewReserve;
import dam.app.activity.ActivityReserves;
import dam.app.model.Reserve;

public class ReservesRecycler extends RecyclerView.Adapter<ReservesRecycler.ViewHolderReserve> {

    private final ActivityReserves _CONTEXT;
    private final List<Reserve> list;
    private final DecimalFormat df = new DecimalFormat("##.#");

    public ReservesRecycler(List<Reserve> list, ActivityReserves _CONTEXT){
        this.list = list;
        this._CONTEXT = _CONTEXT;
    }

    public static class ViewHolderReserve extends RecyclerView.ViewHolder {
        //CardView cardViewComment;
        Button btnCancel;
        Button btnReview;
        TextView lblNameField;
        TextView lblStateReserve;
        TextView lblDateReserveValue;
        TextView lblPriceValue;
        TextView lblStartingTimeValue;
        TextView lblFinishTimeValue;
        TextView lblLocationValue;

        Reserve reserve;

        public ViewHolderReserve(@NonNull View v) {
            super(v);
            //cardViewComment = v.findViewById(R.id.cardViewComment);
            btnCancel = v.findViewById(R.id.btnCancel);
            btnReview = v.findViewById(R.id.textCommentRow);
            lblNameField = v.findViewById(R.id.lblNameField);
            lblStateReserve = v.findViewById(R.id.lblStateReserve);
            lblDateReserveValue = v.findViewById(R.id.lblDateReserveValue);
            lblPriceValue = v.findViewById(R.id.lblPriceValue);
            lblStartingTimeValue = v.findViewById(R.id.lblStartingTimeValue);
            lblFinishTimeValue = v.findViewById(R.id.lblFinishTimeValue);
            lblLocationValue = v.findViewById(R.id.lblLocationValue);
        }
    }

    @NonNull
    public ViewHolderReserve onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_reserve, parent, false);
        return new ReservesRecycler.ViewHolderReserve(view);
    }

    public void onBindViewHolder(@NonNull final ViewHolderReserve holder, int position) {
        holder.reserve = list.get(position);

        //holder.cardViewComment.setTag(position);
        holder.btnCancel.setTag(position);
        holder.btnReview.setTag(position);
        holder.lblNameField.setTag(position);
        holder.lblStateReserve.setTag(position);
        holder.lblDateReserveValue.setTag(position);
        holder.lblPriceValue.setTag(position);
        holder.lblStartingTimeValue.setTag(position);
        holder.lblFinishTimeValue.setTag(position);
        holder.lblLocationValue.setTag(position);

        holder.btnCancel.setOnClickListener(view -> {
            if(_CONTEXT._FIREBASE.isLogged()){
                _CONTEXT.startActivity(new Intent(_CONTEXT, ActivityNewReserve.class));
            }
            else{
                //_CONTEXT.showDialog(R.string.user_not_logged, R.string.wish_to_log_for_comment);
                Toast.makeText(_CONTEXT, R.string.failedOperation, Toast.LENGTH_LONG).show();
            }
        });

        holder.btnReview.setOnClickListener(v -> {
            Intent intent = new Intent(_CONTEXT, ActivityNewComment.class);
            intent.putExtra("idField", holder.reserve.getIdField());
            intent.putExtra("idReserve", holder.reserve.getId());
            intent.putExtra("nameField", holder.reserve.field.getName());
            _CONTEXT.setResult(Activity.RESULT_OK, intent);
            _CONTEXT.startActivity(intent);
            Log.d("on ActivityReserves", _CONTEXT.getResources().getString(R.string.activity_new_comment));
        });
    }

    public int getItemCount() {
        return list.size();
    }
}
