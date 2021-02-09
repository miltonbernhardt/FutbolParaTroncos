package dam.app.extras;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.DialogFragment;

import dam.app.R;
import dam.app.activity.ActivityMain;


public class Dialog extends DialogFragment {

    private Button btnOk;
    private Button btnCancel;

    private ActivityMain _CONTEXT;

    private String title;
    private String message;

    public Dialog(String title, String message, ActivityMain _CONTEXT) {
        this.message = message;
        this.title = title;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_window, container);
        btnOk = view.findViewById(R.id.btnYes);
        btnCancel = view.findViewById(R.id.btnNo);
        getDialog().setTitle("Hello");

        return view;
    }

    /*public View showDialog(String title, String message, ActivityMain _CONTEXT)
    {
        ((TextView) findViewById(R.id.lblTitleDialog)).setText(title);
        ((TextView) findViewById(R.id.lblMessageDialog)).setText(message);
        DialogFragment dialog = new DialogFragment();
        LayoutInflater inflater = dialog.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_window, findViewById(R.id.parent));

        Button btnOk = view.findViewById(R.id.btnOk);
        Button btnCancel = view.findViewById(R.id.btnCancel);

        return view;
    }*/

}