package dam.app.recycler;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.List;

import dam.app.R;
import dam.app.model.Reserve;

public class ReservesRecycler extends RecyclerView.Adapter<ReservesRecycler.ViewHolderReserve> {

    private final List<Reserve> list;
    private final DecimalFormat df = new DecimalFormat("##.#");

    public ReservesRecycler(List<Reserve> list){
        this.list = list;
    }

    public static class ViewHolderReserve extends RecyclerView.ViewHolder {
        CardView cardViewComment;
        ImageView imageViewNewComment;
        TextView lblDateCommentRow;
        TextView lblRatingFieldRow;
        TextView lblUsernameCommentRow;
        TextView textCommentRow;
        RatingBar ratingBarCommentRow;

        Reserve reserve;

        public ViewHolderReserve(@NonNull View v) {
            super(v);
            cardViewComment = v.findViewById(R.id.cardViewComment);
            imageViewNewComment = v.findViewById(R.id.imageViewNewComment);
            lblDateCommentRow = v.findViewById(R.id.lblDateCommentRow);
            lblRatingFieldRow = v.findViewById(R.id.lblRatingFieldRow);
            lblUsernameCommentRow = v.findViewById(R.id.lblUsernameCommentRow);
            textCommentRow = v.findViewById(R.id.textCommentRow);
            ratingBarCommentRow = v.findViewById(R.id.ratingBarCommentRow);
            LayerDrawable stars = (LayerDrawable) ratingBarCommentRow.getProgressDrawable();
            stars.getDrawable(0).setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
        }
    }

    @NonNull
    public ViewHolderReserve onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_reserve, parent, false);
        return new ReservesRecycler.ViewHolderReserve(view);
    }

    public void onBindViewHolder(@NonNull final ViewHolderReserve holder, int position) {
        holder.reserve = list.get(position);

        holder.cardViewComment.setTag(position);
        holder.imageViewNewComment.setTag(position);
        holder.lblDateCommentRow.setTag(position);
        holder.lblRatingFieldRow.setTag(position);
        holder.lblUsernameCommentRow.setTag(position);
        holder.ratingBarCommentRow.setTag(position);
        holder.textCommentRow.setTag(position);
    }

    public int getItemCount() {
        return list.size();
    }
}
