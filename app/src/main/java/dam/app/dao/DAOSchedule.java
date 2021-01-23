package dam.app.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import dam.app.model.Schedule;

@Dao
public interface DAOSchedule {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(Schedule schedule);

    @Delete
    void delete(Schedule schedule);

    @Update
    void update(Schedule schedule);

    @Query("SELECT * FROM schedule WHERE id = :id LIMIT 1")
    Schedule find(long id);

    @Query("SELECT * FROM schedule")
    List<Schedule> findAll();
}
