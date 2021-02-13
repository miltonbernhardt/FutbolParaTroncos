package dam.app.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Objects;

@Entity
public class FieldRm implements Serializable {
    /* ---- ATTRIBUTES ---- */
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    private long id;

    @ColumnInfo(name="name")
    private String name;

    @ColumnInfo(name="price")
    private float price;

    @ColumnInfo(name="address")
    private String address;

    @ColumnInfo(name="phoneOfContact")
    private String phoneOfContact;

    @ColumnInfo(name="rating")
    private float rating;

    @ColumnInfo(name="imagePath")
    private String imagePath;

    @ColumnInfo(name="positionX")
    private String positionX;

    @ColumnInfo(name="positionY")
    private String positionY;

    public FieldRm() { }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public float getRating() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FieldRm field = (FieldRm) o;
        return id == field.id &&
                Objects.equals(name, field.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}