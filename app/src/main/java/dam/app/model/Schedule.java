package dam.app.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(entity = Field.class, parentColumns = "id", childColumns = "idField", onDelete = CASCADE), indices = {@Index(value = {"idField"})})
public class Schedule {
    /* ---- RELATIONS ---- */
    @ColumnInfo(name="idField")
    private long idField;

    /* ---- ATTRIBUTES ---- */
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name="id")
    private long id;

    @ColumnInfo(name="monday")
    private boolean MONDAY = false;
    @ColumnInfo(name="tuesday")
    private boolean TUESDAY = false;
    @ColumnInfo(name="wednesday")
    private boolean WEDNESDAY = false;
    @ColumnInfo(name="thursday")
    private boolean THURSDAY = false;
    @ColumnInfo(name="friday")
    private boolean FRIDAY = false;
    @ColumnInfo(name="saturday")
    private boolean SATURDAY = false;
    @ColumnInfo(name="sunday")
    private boolean SUNDAY = false;

    @ColumnInfo(name="openingTime")
    private int openingTime;
    @ColumnInfo(name="closingTime")
    private int closingTime;

    public Schedule (){
        openingTime = 8;
        closingTime = 21;
    }

    public long getIdField() {
        return idField;
    }

    public void setIdField(long idField) {
        this.idField = idField;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isMONDAY() {
        return MONDAY;
    }

    public void setMONDAY(boolean MONDAY) {
        this.MONDAY = MONDAY;
    }

    public boolean isTUESDAY() {
        return TUESDAY;
    }

    public void setTUESDAY(boolean TUESDAY) {
        this.TUESDAY = TUESDAY;
    }

    public boolean isWEDNESDAY() {
        return WEDNESDAY;
    }

    public void setWEDNESDAY(boolean WEDNESDAY) {
        this.WEDNESDAY = WEDNESDAY;
    }

    public boolean isTHURSDAY() {
        return THURSDAY;
    }

    public void setTHURSDAY(boolean THURSDAY) {
        this.THURSDAY = THURSDAY;
    }

    public boolean isFRIDAY() {
        return FRIDAY;
    }

    public void setFRIDAY(boolean FRIDAY) {
        this.FRIDAY = FRIDAY;
    }

    public boolean isSATURDAY() {
        return SATURDAY;
    }

    public void setSATURDAY(boolean SATURDAY) {
        this.SATURDAY = SATURDAY;
    }

    public boolean isSUNDAY() {
        return SUNDAY;
    }

    public void setSUNDAY(boolean SUNDAY) {
        this.SUNDAY = SUNDAY;
    }

    public int getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(int openingTime) {
        this.openingTime = openingTime;
    }

    public int getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(int closingTime) {
        this.closingTime = closingTime;
    }
}
