package dam.app.extras;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import dam.app.R;
import dam.app.activity.ActivityMain;
import dam.app.activity.ActivitySession;

public class Dialog extends DialogFragment {

    private final ActivityMain _CONTEXT;
    private final String title;
    private final String message;

    public Dialog(String title, String message, ActivityMain _CONTEXT) {
        this.message = message;
        this.title = title;
        this._CONTEXT = _CONTEXT;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_window, container);
        Button btnYes = view.findViewById(R.id.btnYes);
        Button btnNo = view.findViewById(R.id.btnNo);
        TextView lblTitle = view.findViewById(R.id.lblTitleDialog);
        TextView lblMessage = view.findViewById(R.id.lblMessageDialog);

        lblTitle.setText(title);
        lblMessage.setText(message);

        btnNo.setOnClickListener(viewBtn -> {
            dismiss();
        });

        btnYes.setOnClickListener(viewBtn -> {
            dismiss();
            Intent makeReviewScreen = new Intent(_CONTEXT, ActivitySession.class);
            startActivity(makeReviewScreen);
            Log.d("on Dialog", _CONTEXT.getResources().getString(R.string.activity_session));
        });

        return view;
    }

}