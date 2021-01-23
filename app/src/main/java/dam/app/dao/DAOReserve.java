package dam.app.dao;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import dam.app.model.Reserve;

public interface DAOReserve {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(Reserve reserve);

    @Delete
    void delete(Reserve reserve);

    @Update
    void update(Reserve reserve);

    @Query("SELECT * FROM reserve WHERE id = :id LIMIT 1")
    Reserve find(long id);

    @Query("SELECT * FROM reserve")
    List<Reserve> findAll();
}
