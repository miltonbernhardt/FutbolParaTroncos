package dam.app.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import dam.app.model.Comment;
import dam.app.model.Field;

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

    @Query("SELECT * FROM comment WHERE idField = :idField ORDER BY dateOfComment")//ToDo RESERVE cambiar por idReserve cuando se haga lo de reserva
    List<Comment> findAllByDate(long idField);

    @Query("SELECT * FROM comment WHERE idField = :idField ORDER BY dateOfComment DESC")//ToDo RESERVE cambiar por idReserve cuando se haga lo de reserva
    List<Comment> findAllByDateDesc(long idField);

    @Query("SELECT * FROM comment WHERE idField = :idField ORDER BY score")//ToDo RESERVE cambiar por idReserve cuando se haga lo de reserva
    List<Comment> findAllByScore(long idField);


    @Query("SELECT * FROM comment WHERE idField = :idField ORDER BY score DESC")//ToDo RESERVE cambiar por idReserve cuando se haga lo de reserva
    List<Comment> findAllByScoreDesc(long idField);

}
