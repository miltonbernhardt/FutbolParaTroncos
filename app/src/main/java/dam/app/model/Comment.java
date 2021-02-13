package dam.app.model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.time.LocalDate;

@IgnoreExtraProperties
public class Comment implements Serializable {
    private long idReserve;

    private long id;
    private String dateOfComment;
    private String username;
    private String comment;
    private int score;
    private String imagePath;

    public Comment(){ }

    public long getIdReserve() {
        return idReserve;
    }

    public void setIdReserve(long idReserve) {
        this.idReserve = idReserve;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public int getScore() {
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
