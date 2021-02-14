package dam.app.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.io.Serializable;
import java.time.LocalDate;

import static androidx.room.ForeignKey.CASCADE;

@Deprecated
@Entity(foreignKeys = {@ForeignKey(entity = ReserveRm.class, parentColumns = "id", childColumns = "idReserve", onDelete = CASCADE)},
        indices = {@Index(value = {"idReserve"})})
public class CommentRm implements Serializable {
    /* ---- RELATIONS ---- */

    @ColumnInfo(name="idReserve")
    private long idReserve;

    /* ---- ATTRIBUTES ---- */
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    private long id;

    @TypeConverters(Converters.class)
    @ColumnInfo(name="dateOfComment")
    private LocalDate dateOfComment;

    @ColumnInfo(name="username")
    private String username;

    @ColumnInfo(name="comment")
    private String comment;

    @ColumnInfo(name="score")
    private int score;

    @ColumnInfo(name="imageURI")
    private String imagePath;

    public CommentRm(){ }

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

    public LocalDate getDateOfComment() {
        return this.dateOfComment;
    }

    public void setDateOfComment(LocalDate dateOfComment) {
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
}
