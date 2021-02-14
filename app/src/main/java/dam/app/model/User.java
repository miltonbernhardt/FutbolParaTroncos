package dam.app.model;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.time.LocalDate;

public class User implements Serializable {
    private String id;
    private String userName;
    private String key;
    private String phone;
    private String birth;

    public User(){ }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    @Exclude
    public LocalDate getBirthAsDate() {
        if (birth == null) {
            return null;
        } else {
            return LocalDate.parse(this.birth);
        }
    }

    @Exclude
    public void setBirthFromDate(LocalDate dateOfComment) {
        if (dateOfComment == null) {
            this.birth = "";
        } else {
            this.birth = dateOfComment.toString();
        }
    }
}
