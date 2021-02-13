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
public interface DAOComment {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(CommentRm comment);

    @Delete
    void delete(CommentRm comment);

    @Update
    void update(CommentRm comment);

    @Query("SELECT * FROM commentRm WHERE id = :id LIMIT 1")
    CommentRm find(long id);

    @Query("SELECT * FROM commentRm WHERE idReserve = :idReserve ORDER BY dateOfComment")
    List<CommentRm> findAllByDate(long idReserve);

    @Query("SELECT * FROM commentRm WHERE idReserve = :idReserve ORDER BY dateOfComment DESC")
    List<CommentRm> findAllByDateDesc(long idReserve);

    @Query("SELECT * FROM commentRm WHERE idReserve = :idReserve ORDER BY score")
    List<CommentRm> findAllByScore(long idReserve);


    @Query("SELECT * FROM commentRm WHERE idReserve = :idReserve ORDER BY score DESC")
    List<CommentRm> findAllByScoreDesc(long idReserve);
}
