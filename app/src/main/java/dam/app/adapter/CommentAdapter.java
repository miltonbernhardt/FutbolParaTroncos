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
import dam.app.model.Comment;

public class CommentAdapter extends ArrayAdapter<Comment> {

    public CommentAdapter(@NonNull Context context, @NonNull List<Comment> objects) {
        super(context,0, objects);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        final Comment field = super.getItem(position);
        View commentView;
        LayoutInflater inflater = LayoutInflater.from(this.getContext());

        if(convertView == null)
            commentView = inflater.inflate(R.layout.row_comment, parent,false);
        else
            commentView = convertView;
        CommentHolder commentHolder = (CommentHolder) commentView.getTag();

        if(commentHolder == null){
            commentHolder = new CommentHolder(commentView);
            commentView.setTag(commentHolder);
        }

        //ToDo setear bien los campos cuando se termine la vista row_comment
        //fieldHolder.cantidadPlato.setText("x1");
        //fieldHolder.nombrePlato.setText(field.getTitle());
        //fieldHolder.precioPlato.setText("$ "+String.format("%.2f", plato.getPrice()));

        return commentView;
    }

    public class CommentHolder {

        public CommentHolder(View v){
            //ToDo setear bien los campos cuando se termine la vista row_comment
            /*cantidadPlato = v.findViewById(R.id.rowCantidadPlato);
            nombrePlato = v.findViewById(R.id.rowNombrePlato);
            precioPlato = v.findViewById(R.id.rowPrecioPlato);*/
        }
    }

    public void addComment(Comment comment){
        super.add(comment);
        super.notifyDataSetChanged();
    }
}
