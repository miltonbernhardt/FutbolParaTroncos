package dam.app.model;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.time.LocalDate;

public class Reserve implements Serializable {
    private String idUser;
    private String idField;

    private String id;
    private String dateOfReserve;
    private int startTime;
    private int finishTime;
    private boolean hasCommented = false;

    public Reserve(){ }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getIdField() {
        return idField;
    }

    public void setIdField(String idField) {
        this.idField = idField;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDateOfReserve() {
        return dateOfReserve;
    }

    public void setDateOfReserve(String dateOfReserve) {
        this.dateOfReserve = dateOfReserve;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(int finishTime) {
        this.finishTime = finishTime;
    }

    public boolean isHasCommented() {
        return hasCommented;
    }

    public void setHasCommented(boolean hasCommented) {
        this.hasCommented = hasCommented;
    }

    @Exclude
    public LocalDate getDateOfReserveAsDate() {
        if (dateOfReserve == null) {
            return null;
        } else {
            return LocalDate.parse(this.dateOfReserve);
        }
    }

    @Exclude
    public void setDateOfReserveFromDate(LocalDate dateOfReserve) {
        if (dateOfReserve == null) {
            this.dateOfReserve = "";
        } else {
            this.dateOfReserve = dateOfReserve.toString();
        }
    }
}
