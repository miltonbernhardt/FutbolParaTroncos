package dam.app.dao;


import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import dam.app.model.Field;

public interface DAOField {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(Field field);

    @Delete
    void delete(Field field);

    @Update
    void update(Field field);

    @Query("SELECT * FROM field WHERE id = :id LIMIT 1")
    Field find(long id);

    @Query("SELECT * FROM field")
    List<Field> findAll();
}
