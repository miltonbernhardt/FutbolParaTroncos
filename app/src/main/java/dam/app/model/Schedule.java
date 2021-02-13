package dam.app.model;

import java.io.Serializable;

public class Schedule implements Serializable {
    private long idField;

    private long id;
    private boolean MONDAY = false;
    private boolean TUESDAY = false;
    private boolean WEDNESDAY = false;
    private boolean THURSDAY = false;
    private boolean FRIDAY = false;
    private boolean SATURDAY = false;
    private boolean SUNDAY = false;
    private int openingTime;
    private int closingTime;

    public Schedule (){}

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
