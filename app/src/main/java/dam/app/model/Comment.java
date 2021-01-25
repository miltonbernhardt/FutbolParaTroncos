package dam.app.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.time.LocalDate;

import dam.app.database.Converters;

import static androidx.room.ForeignKey.CASCADE;

@Entity(foreignKeys = {@ForeignKey(entity = User.class, parentColumns = "id", childColumns = "idUser", onDelete = CASCADE),
        @ForeignKey(entity = Field.class, parentColumns = "id", childColumns = "idField", onDelete = CASCADE)},
        indices = {@Index(value = {"idUser"}), @Index(value = {"idField"})})
public class Comment {
    /* ---- RELATIONS ---- */
    @ColumnInfo(name="idUser")
    private long idUser;

    @ColumnInfo(name="idField")
    private long idField;

    /* ---- ATTRIBUTES ---- */
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name="id")
    private long id;

    @TypeConverters(Converters.class)
    @ColumnInfo(name="dateOfComment")
    private LocalDate dateOfComment;

    //Es repetir datos, pero es más esfuerzo si no se hace
    @ColumnInfo(name="username")
    private String username;

    @ColumnInfo(name="comment")
    private String comment;

    @ColumnInfo(name="score")
    private int score;

    public Comment(){
        dateOfComment = LocalDate.now();
        comment = "";
        score = 5;
    }

    public long getIdUser() {
        return idUser;
    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }

    public long getIdField() {
        return idField;
    }

    public void setIdField(long idField) {
        this.idField = idField;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getDateOfComment() {
        return dateOfComment;
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
}
