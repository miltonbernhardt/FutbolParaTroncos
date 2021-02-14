package dam.app.room;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Deprecated
@Dao
public interface DAOField {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(FieldRm field);

    @Delete
    void delete(FieldRm field);

    @Update
    void update(FieldRm field);

    @Query("SELECT * FROM fieldRm WHERE id = :id LIMIT 1")
    FieldRm find(long id);

    @Query("SELECT * FROM fieldRm ORDER BY name")
    List<FieldRm> findAllByName();

    @Query("SELECT * FROM fieldRm ORDER BY address")
    List<FieldRm> findAllByProximity();

    @Query("SELECT * FROM fieldRm ORDER BY address DESC")
    List<FieldRm> findAllByProximityDesc();

    @Query("SELECT * FROM fieldRm ORDER BY rating")
    List<FieldRm> findAllByScore();

    @Query("SELECT * FROM fieldRm ORDER BY rating DESC")
    List<FieldRm> findAllByScoreDesc();

}
