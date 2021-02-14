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
public interface DAOSchedule {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(ScheduleRm schedule);

    @Delete
    void delete(ScheduleRm schedule);

    @Update
    void update(ScheduleRm schedule);

    @Query("SELECT * FROM scheduleRm WHERE id = :id LIMIT 1")
    ScheduleRm find(long id);

    @Query("SELECT * FROM scheduleRm")
    List<ScheduleRm> findAll();
}
