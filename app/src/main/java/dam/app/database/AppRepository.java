package dam.app.database;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import dam.app.dao.DAOComment;
import dam.app.dao.DAOField;
import dam.app.dao.DAOReserve;
import dam.app.dao.DAOSchedule;
import dam.app.dao.DAOUser;
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

    public List<Comment> getCommentsFromField(long id) {
        List<Comment> list = daoComment.findAllByField(id);
        if(list.isEmpty()){
            //ToDo AL FINAL quitar esto
            if(daoField.findAll().isEmpty()){
                for (Field f : VolatileData.getFields()) daoField.insert(f);
            }
            for (Comment c : VolatileData.getComments()) daoComment.insert(c);
            list = daoComment.findAllByField(id);
        }
        return list;
    }

    public List<Field> getAllFields() {
        List<Field> list = daoField.findAll();
        if(list.isEmpty()){
            //ToDo AL FINAL quitar esto
            if(daoField.findAll().isEmpty()){
                for (Field f : VolatileData.getFields()) daoField.insert(f);
            }
            for (Comment c : VolatileData.getComments()) daoComment.insert(c);
            list = daoField.findAll();
        }
        return list;
    }

    public static void close(){
        _INSTANCE = null;
        db.close();
    }
}

