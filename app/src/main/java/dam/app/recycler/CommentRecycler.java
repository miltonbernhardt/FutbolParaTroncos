package dam.app.recycler;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;

import dam.app.R;
import dam.app.activity.ActivityComments;
import dam.app.model.Comment;

public class CommentRecycler extends RecyclerView.Adapter<CommentRecycler.ViewHolderComment> {

    private final ActivityComments activity;
    private final List<Comment> list;
    private final DecimalFormat df = new DecimalFormat("##.#");

    public CommentRecycler(ActivityComments activity, List<Comment> list){
        this.activity = activity;
        this.list = list;
    }

    public static class ViewHolderComment extends RecyclerView.ViewHolder {
        CardView cardViewComment;
        TextView lblDateCommentRow;
        TextView lblRatingFieldRow;
        TextView lblUsernameCommentRow;
        TextView textCommentRow;
        RatingBar ratingBarCommentRow;

        Comment comment;

        public ViewHolderComment(@NonNull View v) {
            super(v);
            cardViewComment = v.findViewById(R.id.cardViewComment);
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
    public ViewHolderComment onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_comment, parent, false);
        return new CommentRecycler.ViewHolderComment(view);
    }

    public void onBindViewHolder(@NonNull final ViewHolderComment holder, int position) {
        holder.comment = list.get(position);
        holder.lblDateCommentRow.setTag(position);
        holder.cardViewComment.setTag(position);
        holder.lblRatingFieldRow.setTag(position);
        holder.lblUsernameCommentRow.setTag(position);
        holder.textCommentRow.setTag(position);
        holder.ratingBarCommentRow.setTag(position);

        holder.lblDateCommentRow.setText(holder.comment.getDateOfComment().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));//TODo ver formato fecha
        df.setRoundingMode(RoundingMode.DOWN);
        holder.lblRatingFieldRow.setText(df.format(holder.comment.getScore()));
        holder.lblUsernameCommentRow.setText(holder.comment.getUsername());
        holder.textCommentRow.setText(holder.comment.getComment());
        holder.ratingBarCommentRow.setRating(holder.comment.getScore());
    }

    public int getItemCount() {
        return list.size();
    }
}
