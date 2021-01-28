package dam.app.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import dam.app.model.Comment;

@Dao
public interface DAOComment {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(Comment comment);

    @Delete
    void delete(Comment comment);

    @Update
    void update(Comment comment);

    @Query("SELECT * FROM comment WHERE id = :id LIMIT 1")
    Comment find(long id);

    @Query("SELECT * FROM comment")
    List<Comment> findAll();

    @Query("SELECT * FROM comment WHERE idField = :idField")
    List<Comment> findAllByField(long idField);
}
