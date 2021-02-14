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
public interface DAOReserve {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(ReserveRm reserve);

    @Delete
    void delete(ReserveRm reserve);

    @Update
    void update(ReserveRm reserve);

    @Query("SELECT * FROM reserveRm WHERE id = :id LIMIT 1")
    ReserveRm find(long id);

    @Query("SELECT * FROM reserveRm")
    List<ReserveRm> findAll();
}
