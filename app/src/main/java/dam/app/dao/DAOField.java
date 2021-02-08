package dam.app.dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import dam.app.model.Field;

@Dao
public interface DAOField {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(Field field);

    @Delete
    void delete(Field field);

    @Update
    void update(Field field);

    @Query("SELECT * FROM field WHERE id = :id LIMIT 1")
    Field find(long id);

    @Query("SELECT * FROM field ORDER BY name")
    List<Field> findAllByName();

    @Query("SELECT * FROM field ORDER BY name DESC")
    List<Field> findAllByNameDesc();

    @Query("SELECT * FROM field ORDER BY address") //ToDo DAO ordenar por promixidad, capaz hacerlo en el repository
    List<Field> findAllByProximity();

    @Query("SELECT * FROM field ORDER BY address DESC") //ToDo DAO ordenar por promixidad, capaz hacerlo en el repository
    List<Field> findAllByProximityDesc();

    @Query("SELECT * FROM field ORDER BY rating")
    List<Field> findAllByScore();

    @Query("SELECT * FROM field ORDER BY rating DESC")
    List<Field> findAllByScoreDesc();

}
