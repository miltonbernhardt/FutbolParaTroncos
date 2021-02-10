package dam.app.database;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import dam.app.dao.DAOComment;
import dam.app.dao.DAOField;
import dam.app.dao.DAOReserve;
import dam.app.dao.DAOSchedule;
import dam.app.dao.DAOUser;
import dam.app.extras.EnumSortOption;
import dam.app.model.Comment;
import dam.app.model.Field;

public class AppRepository {
    private static AppCompatActivity _CONTEXT;
    private static AppRepository _INSTANCE = null;
    private static AppDatabase db = null;

    private final DAOComment daoComment;
    private final DAOField daoField;
    private final DAOReserve daoReserve;
    private final DAOSchedule daoSchedule;
    private final DAOUser daoUser;

    private AppRepository(final AppCompatActivity context){
        _CONTEXT = context;
        db = AppDatabase.getInstance(context);
        daoComment = db.daoComment();
        daoField = db.daoField();
        daoReserve = db.daoReserve();
        daoSchedule = db.daoSchedule();
        daoUser = db.daoUser();
    }

    public static AppRepository getInstance(final AppCompatActivity context) {
        if (_INSTANCE == null) _INSTANCE = new AppRepository(context);
        return _INSTANCE;
    }

    public List<Comment> getCommentsFromField(long id, EnumSortOption sortBy) {
        List<Comment> list = new ArrayList<>();

        switch (sortBy){
            case PUNTUACION_ALTA:
                list = daoComment.findAllByScoreDesc(id);
                break;
            case PUNTUACION_BAJA:
                list = daoComment.findAllByScore(id);
                break;
            case FECHA_CERCANA:
                list = daoComment.findAllByDateDesc(id);
                break;
            case FECHA_LEJANA:
                list = daoComment.findAllByDate(id);
                break;
        }

        if(list.isEmpty()){
            //ToDo AL FINAL quitar esto
            if(daoField.findAllByName().isEmpty()){
                for (Field f : VolatileData.getFields()) daoField.insert(f);
            }
            for (Comment c : VolatileData.getComments()) daoComment.insert(c);

            list = daoComment.findAllByDate(id);
        }
        return list;
    }

    public List<Field> getAllFields(EnumSortOption sortBy) {
        List<Field> list = new ArrayList<>();

        switch (sortBy){
            case DIRECCION_CERCANA:
                list = daoField.findAllByProximity();
                break;
            case DIRECCION_LEJANA:
                list = daoField.findAllByProximityDesc();
                break;
            case PUNTUACION_ALTA:
                list = daoField.findAllByScoreDesc();
                break;
            case PUNTUACION_BAJA:
                list = daoField.findAllByScore();
                break;
            case NOMBRE_ALFABETICO:
                list = daoField.findAllByName();
                break;
        }


        if(list.isEmpty()){
            //ToDo AL FINAL quitar esto
            if(daoField.findAllByName().isEmpty()){
                for (Field f : VolatileData.getFields()) daoField.insert(f);
            }
            for (Comment c : VolatileData.getComments()) daoComment.insert(c);

            list = daoField.findAllByName();
        }
        return list;
    }

    public long saveComment(Comment comment){

        return 0l;
    }

    public static boolean isLogged(){
        //ToDo SESSION verificar si está logueado
        return true;
    }

    public static void close(){
        _INSTANCE = null;
        db.close();
    }
}

