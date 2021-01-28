package dam.app.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.io.Serializable;
import java.time.LocalDate;

import dam.app.database.Converters;

import static androidx.room.ForeignKey.CASCADE;

@Entity(foreignKeys = {@ForeignKey(entity = User.class, parentColumns = "id", childColumns = "idUser", onDelete = CASCADE),
        @ForeignKey(entity = Field.class, parentColumns = "id", childColumns = "idField", onDelete = CASCADE)},
        indices = {@Index(value = {"idUser"}), @Index(value = {"idField"})})
public class Reserve implements Serializable {
    /* ---- RELATIONS ---- */
    @ColumnInfo(name="idUser")
    private long idUser;

    @ColumnInfo(name="idField")
    private long idField;

    /* ---- ATTRIBUTES ---- */
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    private long id;

    @TypeConverters(Converters.class)
    @ColumnInfo(name="dateOfReserve")
    private LocalDate dateOfReserve;

    @ColumnInfo(name="startTime")
    private int startTime;
    @ColumnInfo(name="finishTime")
    private int finishTime;

    public Reserve(){
        dateOfReserve = LocalDate.now();
        startTime = 0;
        finishTime = 0;
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

    public LocalDate getDateOfReserve() {
        return dateOfReserve;
    }

    public void setDateOfReserve(LocalDate dateOfReserve) {
        this.dateOfReserve = dateOfReserve;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(int finishTime) {
        this.finishTime = finishTime;
    }
}
