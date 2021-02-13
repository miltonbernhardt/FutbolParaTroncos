package dam.app.recycler;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import java.time.format.DateTimeFormatter;
import java.util.List;

import dam.app.R;
import dam.app.activity.ActivityMain;
import dam.app.extras.ImageHelper;
import dam.app.model.Comment;

public class CommentRecycler extends RecyclerView.Adapter<CommentRecycler.ViewHolderComment> {

    private final List<Comment> list;
    private final DecimalFormat df = new DecimalFormat("##.#");
    private final ActivityMain _CONTEXT;

    public CommentRecycler(List<Comment> list, ActivityMain _CONTEXT){
        this.list = list;
        this._CONTEXT = _CONTEXT;
    }

    public static class ViewHolderComment extends RecyclerView.ViewHolder {
        CardView cardViewComment;//TODO ActivityNewComment agregar imagen
        ImageView imageViewNewComment;
        TextView lblDateCommentRow;
        TextView lblRatingFieldRow;
        TextView lblUsernameCommentRow;
        TextView textCommentRow;
        RatingBar ratingBarCommentRow;

        Comment comment;

        public ViewHolderComment(@NonNull View v) {
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
    public ViewHolderComment onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_comment, parent, false);
        return new CommentRecycler.ViewHolderComment(view);
    }

    public void onBindViewHolder(@NonNull final ViewHolderComment holder, int position) {
        holder.comment = list.get(position);

        holder.cardViewComment.setTag(position);
        holder.imageViewNewComment.setTag(position);
        holder.lblDateCommentRow.setTag(position);
        holder.lblRatingFieldRow.setTag(position);
        holder.lblUsernameCommentRow.setTag(position);
        holder.ratingBarCommentRow.setTag(position);
        holder.textCommentRow.setTag(position);

        if(holder.comment.getImageURI() != null && !holder.comment.getImageURI().equals("")) {

            Bitmap bitmapImage = BitmapFactory.decodeFile(holder.comment.getImageURI());

            if(bitmapImage != null)
                holder.imageViewNewComment.setImageBitmap(bitmapImage);
            else{
                Uri file = Uri.fromFile(new File(holder.comment.getImageURI()));
                StorageReference islandRef = FirebaseStorage.getInstance().getReference().child("reviewImages/"+file.getLastPathSegment());

                File localFile = null;
                try {
                    localFile = File.createTempFile("images", "tmp");
                } catch (IOException ignore) { }

                File finalLocalFile = localFile;
                islandRef.getFile(localFile).addOnSuccessListener(taskSnapshot -> {
                    Bitmap bitmap = BitmapFactory.decodeFile(finalLocalFile.getAbsolutePath());
                    holder.imageViewNewComment.setImageBitmap(bitmap);

                    ImageHelper.persistImage(bitmap, file.getLastPathSegment(), _CONTEXT, "");
                });
            }
        }

        holder.lblDateCommentRow.setText(holder.comment.getDateOfComment().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
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
