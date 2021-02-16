package dam.app.model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.time.LocalDate;

@IgnoreExtraProperties
public class Comment implements Serializable{
    private String idReserve = "";
    private String idField = "";

    private String id = "";
    private String dateOfComment = "";
    private String username = "";
    private String comment = "";
    private int score = 5;
    private String imagePath = "";

    public Comment(){ }

    public String getIdReserve() {
        return idReserve;
    }

    public void setIdReserve(String idReserve) {
        this.idReserve = idReserve;
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

    public String getDateOfComment() {
       return this.dateOfComment;
    }

    public void setDateOfComment(String dateOfComment) {
        this.dateOfComment = dateOfComment;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Exclude
    public LocalDate getDateOfCommentAsDate() {
        if (dateOfComment == null) {
            return null;
        } else {
            return LocalDate.parse(this.dateOfComment);
        }
    }

    @Exclude
    public void setDateOfCommentFromDate(LocalDate dateOfComment) {
        if (dateOfComment == null) {
            this.dateOfComment = "";
        } else {
            this.dateOfComment = dateOfComment.toString();
        }
    }
}
