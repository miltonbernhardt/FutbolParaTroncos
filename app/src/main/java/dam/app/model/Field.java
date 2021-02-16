package dam.app.model;

import java.io.Serializable;
import java.util.Objects;

public class Field implements Serializable {
    private String id;
    private String name;
    private float price;
    private String address;
    private String phoneOfContact;
    private float rating;
    private String imagePath;
    private String positionX;
    private String positionY;

    private boolean MONDAY = true;
    private boolean TUESDAY = true;
    private boolean WEDNESDAY = true;
    private boolean THURSDAY = true;
    private boolean FRIDAY = true;
    private boolean SATURDAY = true;
    private boolean SUNDAY = false;
    private int openingTime = 10;
    private int closingTime = 22;

    public Field() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getPhoneOfContact() {
        return phoneOfContact;
    }

    public void setPhoneOfContact(String phoneOfContact) {
        this.phoneOfContact = phoneOfContact;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getPositionX(){return positionX;};

    public void setPositionX(String positionX){this.positionX=positionX;}

    public String getPositionY(){return positionY;};

    public void setPositionY(String positionY){this.positionY=positionY;}

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Field field = (Field) o;
        return id == field.id &&
                Objects.equals(name, field.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
