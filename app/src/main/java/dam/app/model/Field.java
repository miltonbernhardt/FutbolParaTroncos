package dam.app.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.Objects;

@Entity
public class Field implements Serializable {
    /* ---- ATTRIBUTES ---- */
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    private long id;

    @ColumnInfo(name="name")
    private String name;

    @ColumnInfo(name="price")
    private float price;

    //ToDo see when the maps is done
    @ColumnInfo(name="address")
    private String address;

    @ColumnInfo(name="phoneOfContact")
    private String phoneOfContact;

    @ColumnInfo(name="rating")
    private float rating;

    @ColumnInfo(name="imageUUID")
    private String imageUUID;

    @ColumnInfo(name="positionX")
    String positionX;

    @ColumnInfo(name="positionY")
    String positionY;

    public Field() {
        name = address = phoneOfContact = imageUUID = "";
        rating = 0f;
    }

   public String getPositionX(){return positionX;};

    public void setPositionX(String positionX){this.positionX=positionX;}

    public String getPositionY(){return positionY;};

    public void setPositionY(String positionY){this.positionY=positionY;}

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

    public String getImageUUID() {
        return imageUUID;
    }

    public void setImageUUID(String imageUUID) {
        this.imageUUID = imageUUID;
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
