package dam.app.model;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.time.LocalDate;

public class Reserve implements Serializable {
    private long idUser;
    private long idField;

    private long id;
    private String dateOfReserve;
    private int startTime;
    private int finishTime;

    public Reserve(){ }

    public long getIdUser() {
        return idUser;
    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
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
