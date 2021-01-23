package dam.app.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import dam.app.model.User;

@Dao
public interface DAOUser {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(User user);

    @Delete
    void delete(User user);

    @Update
    void update(User user);

    @Query("SELECT * FROM user WHERE id = :id LIMIT 1")
    User find(long id);

    @Query("SELECT * FROM user")
    List<User> findAll();
}
