package dam.app.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

import java.io.Serializable;

public class Cancha implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name="id")
    private long id;

    public Cancha() {}

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if(this.getId().equals( ((Cancha)obj).getId() )){
            return true;
        }
        else{
            return false;
        }
    }
}
