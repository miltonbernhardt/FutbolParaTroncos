package dam.app.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Field {
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

    public Field() {
        name = address = phoneOfContact = imageUUID = "";
        rating = 0f;
    }

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
}
