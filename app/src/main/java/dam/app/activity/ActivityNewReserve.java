package dam.app.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.Guideline;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.DecimalFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import dam.app.R;
import dam.app.extras.EnumStateReserve;
import dam.app.model.Field;
import dam.app.model.Reserve;

public class ActivityNewReserve extends ActivityMain {
    DatePickerDialog datePicker;

    Button btnNewReserve;
    Spinner spinnerStartingTime;
    TextView lblFinishTimeValue;
    TextView lblFieldName;
    TextView lblLocationValue;
    TextView lblCostValue;
    TextView lblChosenDateValue;

    private Field field;
    private LocalDate chosenDate;
    boolean nextDay = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_reserve);
        createDrawable(this, false);

        field = (Field) getIntent().getExtras().getSerializable("field");

        btnNewReserve = findViewById(R.id.btnNewReserve);
        spinnerStartingTime = findViewById(R.id.spinnerStartingTime);
        lblFinishTimeValue = findViewById(R.id.lblFinishTimeValue);
        lblFieldName = findViewById(R.id.lblFieldName);
        lblLocationValue = findViewById(R.id.lblLocationValue);
        lblCostValue = findViewById(R.id.lblCostValue);
        lblChosenDateValue = findViewById(R.id.lblChosenDateValue);

        lblLocationValue.setText(field.getAddress());
        lblCostValue.setText("$ "+new DecimalFormat("##.##").format(field.getPrice()));
        lblFieldName.setText(field.getName());

        LocalTime today = LocalTime.now();
        int hourNow = today.getHour();
        List<String> openingHours = new ArrayList<>();

        while(hourNow < field.getOpeningTime()){
            openingHours.add(String.format("%02d", hourNow)+":00");
            hourNow++;
        }
        nextDay = (openingHours.size() == 0);


        initCalendar();

        lblChosenDateValue.setOnClickListener(v -> datePicker.show(_CONTEXT.getSupportFragmentManager(), "Datepickerdialog"));

        btnNewReserve.setOnClickListener(v -> {
            new MaterialAlertDialogBuilder(_CONTEXT, R.style.Body_ThemeOverlay_MaterialComponents_MaterialAlertDialog)
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .setTitle("RESERVAR CANCHA")
                    .setMessage("¿Deseas hacer una reserva para la cancha '"+field.getName()+"' el día "+lblChosenDateValue.getText().toString()+" de "+
                            spinnerStartingTime.getSelectedItem().toString()+" a "+lblFinishTimeValue.getText().toString()+" hs ?")
                    .setPositiveButton("Si", (dialog, which) -> saveReserve())
                    .setNegativeButton("No", null)
                    .show();
        });

    }

    public void saveReserve(){
        Reserve r = new Reserve();
        r.setDateOfReserve(chosenDate);
        r.setStartTime(Integer.parseInt(lblChosenDateValue.getText().toString().substring(0,2)));
        r.setFinishTime(Integer.parseInt(lblFinishTimeValue.getText().toString().substring(0,2)));
        r.setHasCommented(false);
        r.setIdField(field.getId());
        r.setState(EnumStateReserve.ACTIVA.toString());
        r.setNameField(lblFieldName.getText().toString());
        r.setPrice(field.getPrice());
        r.setAddress(field.getAddress());
        _FIREBASE.saveReserve(r);
        //TODO añadir notificación de que se confirma la reserva
    }

    private void initSpinner(){
        LocalTime now = LocalTime.now();
        int hourNow = now.getHour();
        List<String> openingHours = new ArrayList<>();

        while(hourNow < field.getOpeningTime()){
            openingHours.add(String.format("%02d", hourNow)+":00");
            hourNow++;
        }

        nextDay = (openingHours.size() == 0);

        if(nextDay){
            hourNow = field.getOpeningTime();
            while(hourNow < field.getClosingTime()){
                openingHours.add(String.format("%02d", hourNow)+":00");
                hourNow++;
            }
        }
        spinnerStartingTime.setAdapter(new ArrayAdapter<>(_CONTEXT, R.layout.spinner_layout, openingHours));
        spinnerStartingTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String value = spinnerStartingTime.getSelectedItem().toString();
                String hour = (Integer.parseInt(value.substring(0, 2)) + 1) +":00";
                lblFinishTimeValue.setText(hour);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) { }
        });
    }

    private void initCalendar(){
        chosenDate = LocalDate.now();
        if(nextDay) chosenDate = chosenDate.plusDays(1);
        lblChosenDateValue.setText(chosenDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

        Calendar now = Calendar.getInstance();
        now.set(chosenDate.getYear(), chosenDate.getMonthValue()-1, chosenDate.getDayOfMonth());

        if (datePicker == null)
            datePicker = DatePickerDialog.newInstance(null, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
        else
            datePicker.initialize( null, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));

        datePicker.setOnDateSetListener((view, year, monthOfYear, dayOfMonth) -> {
            chosenDate = LocalDate.of(year, monthOfYear, dayOfMonth);
            lblChosenDateValue.setText(String.format("%02d", dayOfMonth)+"/"+String.format("%02d", (monthOfYear+1))+"/"+year);
            initSpinner();
        });

        datePicker.setThemeDark(false);
        datePicker.vibrate(false);
        datePicker.dismissOnPause(true);
        datePicker.showYearPickerFirst(false);
        datePicker.setVersion(DatePickerDialog.Version.VERSION_2);
        datePicker.setTitle("FECHA DE RESERVA");
        datePicker.setAccentColor(getResources().getColor(R.color.colorPrimary));
        datePicker.setScrollOrientation(DatePickerDialog.ScrollOrientation.VERTICAL);

        List<DayOfWeek> daysAvailable = new ArrayList<>();
        List<Calendar> days = new ArrayList<>();

        if(field.isMONDAY()) daysAvailable.add(DayOfWeek.MONDAY);
        if(field.isTUESDAY()) daysAvailable.add(DayOfWeek.TUESDAY);
        if(field.isWEDNESDAY()) daysAvailable.add(DayOfWeek.WEDNESDAY);
        if(field.isTHURSDAY()) daysAvailable.add(DayOfWeek.THURSDAY);
        if(field.isFRIDAY()) daysAvailable.add(DayOfWeek.FRIDAY);
        if(field.isSATURDAY()) daysAvailable.add(DayOfWeek.SATURDAY);
        if(field.isSUNDAY()) daysAvailable.add(DayOfWeek.SUNDAY);

        LocalDate today = LocalDate.now();

        LocalDate lastDay = today.plusMonths(1);

        int i = 0;
        if(nextDay) i++;
        while(!today.equals(lastDay)) {

            if(daysAvailable.contains(today.getDayOfWeek())){
                Calendar day = Calendar.getInstance();
                day.add(Calendar.DAY_OF_MONTH, i);
                days.add(day);
            }
            i++;
            today = today.plusDays(1);
        }
        datePicker.setSelectableDays(days.toArray(new Calendar[days.size()]));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        navigationView.getMenu().getItem(2).setVisible(false);
        navigationView.getMenu().getItem(3).setVisible(false);
        return true;
    }
}