package dam.app.adapters;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.Guideline;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;

import dam.app.R;
import dam.app.activity.ActivityMain;
import dam.app.activity.ActivityNewComment;
import dam.app.extras.EnumStateReserve;
import dam.app.model.Reserve;

public class ReservesRecycler extends RecyclerView.Adapter<ReservesRecycler.ViewHolderReserve> {

    private final ActivityMain _CONTEXT;
    private final List<Reserve> list;
    private final DecimalFormat df = new DecimalFormat("##.#");

    public ReservesRecycler(List<Reserve> list, ActivityMain _CONTEXT){
        this.list = list;
        this._CONTEXT = _CONTEXT;
    }

    public static class ViewHolderReserve extends RecyclerView.ViewHolder {
        //CardView cardViewComment;
        Button btnCancel;
        Button btnHasUsed;
        Button btnReview;
        Guideline guideline1;
        Guideline guideline2;
        Guideline guideline3;
        Guideline guideline4;
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
            btnHasUsed = v.findViewById(R.id.btnHasUsed);
            btnReview = v.findViewById(R.id.btnReview);
            guideline1 = v.findViewById(R.id.guideline1);
            guideline2 = v.findViewById(R.id.guideline2);
            guideline3 = v.findViewById(R.id.guideline3);
            guideline4 = v.findViewById(R.id.guideline4);
            lblNameField = v.findViewById(R.id.lblNameFieldValue);
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

        holder.btnCancel.setTag(position);
        holder.btnHasUsed.setTag(position);
        holder.btnReview.setTag(position);
        holder.lblNameField.setTag(position);
        holder.lblStateReserve.setTag(position);
        holder.lblDateReserveValue.setTag(position);
        holder.lblPriceValue.setTag(position);
        holder.lblStartingTimeValue.setTag(position);
        holder.lblFinishTimeValue.setTag(position);
        holder.lblLocationValue.setTag(position);

        holder.lblNameField.setText(holder.reserve.getNameField());
        holder.lblLocationValue.setText(holder.reserve.getAddress());

        String state = holder.reserve.getState();
        String price = "$ "+holder.reserve.getPrice();
        String date = holder.reserve.getDateOfReserveAsDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String startTime = String.format("%02d", holder.reserve.getStartTime())+":00";
        String finishTime = String.format("%02d", holder.reserve.getFinishTime())+":00";

        holder.lblStateReserve.setText(state);
        holder.lblDateReserveValue.setText(date);
        holder.lblPriceValue.setText(price);
        holder.lblStartingTimeValue.setText(startTime);
        holder.lblFinishTimeValue.setText(finishTime);


        if(state.equals(EnumStateReserve.ACTIVA.toString())){
            holder.btnCancel.setOnClickListener(view -> {
                holder.reserve.setState(EnumStateReserve.CANCELADA.toString());
                _CONTEXT._FIREBASE.updateObject(holder.reserve);
            });

            holder.btnHasUsed.setOnClickListener(view -> {
                holder.reserve.setState(EnumStateReserve.USADA.toString());
                _CONTEXT._FIREBASE.updateObject(holder.reserve);
            });
        }
        else {
            holder.btnCancel.setVisibility(View.GONE);
            holder.btnHasUsed.setVisibility(View.GONE);
            if(state.equals(EnumStateReserve.CANCELADA.toString())) holder.lblStateReserve.getBackground().setTint(_CONTEXT.getResources().getColor(R.color.red));
            else holder.lblStateReserve.getBackground().setTint(_CONTEXT.getResources().getColor(R.color.yellow));
        }


        if(holder.reserve.isHasCommented() || state.equals(EnumStateReserve.CANCELADA.toString())) {
            holder.btnReview.setVisibility(View.GONE);
        }
        else {
            holder.btnReview.setOnClickListener(v -> {
                Intent intent = new Intent(_CONTEXT, ActivityNewComment.class);
                intent.putExtra("idField", holder.reserve.getIdField());
                intent.putExtra("idReserve", holder.reserve.getId());
                intent.putExtra("nameField", holder.reserve.getNameField());
                _CONTEXT.setResult(Activity.RESULT_OK, intent);
                _CONTEXT.startActivity(intent);
                Log.d("on ActivityComments", _CONTEXT.getResources().getString(R.string.activity_new_comment));

            });
        }


        if(holder.btnReview.getVisibility() == View.GONE &&
                holder.btnHasUsed.getVisibility() == View.GONE &&
                holder.btnCancel.getVisibility() == View.GONE){
            holder.guideline1.setGuidelinePercent(0.25f);
            holder.guideline2.setGuidelinePercent(0.50f);
            holder.guideline3.setGuidelinePercent(0.75f);
            holder.guideline4.setGuidelinePercent(1f);
        }
    }

    public int getItemCount() {
        return list.size();
    }
}
