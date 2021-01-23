package dam.app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import dam.app.R;
import dam.app.activity.ActivityComments;
import dam.app.model.Field;

public class CommentRecycler extends RecyclerView.Adapter<CommentRecycler.ViewHolderComment> {

    private final ActivityComments activityComments;
    private final List<Field> fieldList;

    public CommentRecycler(ActivityComments activityComments, List<Field> plateListParameter){
        this.activityComments = activityComments;
        this.fieldList = plateListParameter;
    }

    public static class ViewHolderComment extends RecyclerView.ViewHolder {
        //ToDo setear bien los campos cuando se termine la vista row_field
        /*ImageView plateImage;
        CardView cardViewPlate;
        TextView textTitle;
        Button btnVer;*/

        public ViewHolderComment(@NonNull View itemView) {
            super(itemView);
        }
    }

    @NonNull
    public ViewHolderComment onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_comment, parent, false);
        return new CommentRecycler.ViewHolderComment(view);
    }

    public void onBindViewHolder(@NonNull final CommentRecycler.ViewHolderComment commentHolder, int position) {
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
