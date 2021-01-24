package dam.app.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.time.LocalDate;

import dam.app.database.Converters;

@Entity
public class User {
    /* ---- ATTRIBUTES -----*/
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name="id")
    private long id;

    @ColumnInfo(name="userName")
    private String userName;

    @ColumnInfo(name="key")
    private String key;

    //ToDo is the first and last name required?

    @ColumnInfo(name="phone")
    private String phone;

    @TypeConverters(Converters.class)
    @ColumnInfo(name="birth")
    private LocalDate birth;

    public User(){
        userName = "";
        key = "";
        phone = "";
        birth = LocalDate.now();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getBirth() {
        return birth;
    }

    public void setBirth(LocalDate birth) {
        this.birth = birth;
    }
}
