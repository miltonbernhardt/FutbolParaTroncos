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

    @Query("SELECT * FROM comment WHERE idReserve = :idReserve ORDER BY dateOfComment")
    List<Comment> findAllByDate(long idReserve);

    @Query("SELECT * FROM comment WHERE idReserve = :idReserve ORDER BY dateOfComment DESC")
    List<Comment> findAllByDateDesc(long idReserve);

    @Query("SELECT * FROM comment WHERE idReserve = :idReserve ORDER BY score")
    List<Comment> findAllByScore(long idReserve);


    @Query("SELECT * FROM comment WHERE idReserve = :idReserve ORDER BY score DESC")
    List<Comment> findAllByScoreDesc(long idReserve);

}
