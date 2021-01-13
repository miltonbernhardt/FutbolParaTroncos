package dam.app.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import dam.app.model.Cancha;

@Dao
public interface DAOCancha {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insertar(Cancha cancha);

    @Delete
    void borrar(Cancha cancha);

    @Update
    void actualizar(Cancha cancha);

    @Query("SELECT * FROM cancha WHERE id = :id LIMIT 1")
    Cancha buscar(long id);

    @Query("SELECT * FROM cancha")
    List<Cancha> buscarTodos();
}
