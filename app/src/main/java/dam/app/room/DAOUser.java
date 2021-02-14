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
public interface DAOUser {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(UserRm user);

    @Delete
    void delete(UserRm user);

    @Update
    void update(UserRm user);

    @Query("SELECT * FROM userRm WHERE id = :id LIMIT 1")
    UserRm find(long id);

    @Query("SELECT * FROM userRm")
    List<UserRm> findAll();
}
