package dam.app.model;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.time.LocalDate;

import dam.app.extras.EnumStateReserve;

public class Reserve implements Serializable {
    private String idUser;
    private String idField;

    private float price;
    private String nameField;

    private String address;

    private String id;
    private String dateOfReserve;
    private int startTime;
    private int finishTime;

    private String state = EnumStateReserve.ACTIVA.toString();
    private boolean hasCommented = false;

    public Field field;

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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getNameField() {
        return nameField;
    }

    public void setNameField(String nameField) {
        this.nameField = nameField;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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
    public void setDateOfReserve(LocalDate dateOfReserve) {
        if (dateOfReserve == null) {
            this.dateOfReserve = "";
        } else {
            this.dateOfReserve = dateOfReserve.toString();
        }
    }
}
