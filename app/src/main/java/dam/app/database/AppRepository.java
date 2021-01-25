package dam.app.database;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import dam.app.dao.DAOComment;
import dam.app.dao.DAOField;
import dam.app.dao.DAOReserve;
import dam.app.dao.DAOSchedule;
import dam.app.dao.DAOUser;

public class AppRepository implements OnResultCallback {

    private static AppRepository _INSTANCE = null;
    private static AppDatabase db = null;
    private final OnResultCallback callback;

    private final DAOComment daoComment;
    private final DAOField daoField;
    private final DAOReserve daoReserve;
    private final DAOSchedule daoSchedule;
    private final DAOUser daoUser;

    private AppRepository(final Context context, OnResultCallback callback2){
        db = AppDatabase.getInstance(context);
        daoComment = db.daoComment();
        daoField = db.daoField();
        daoReserve = db.daoReserve();
        daoSchedule = db.daoSchedule();
        daoUser = db.daoUser();
        callback = callback2;
    }

    public static AppRepository getInstance(final Context context, OnResultCallback<?> callback) {
        if (_INSTANCE == null) {
            _INSTANCE = new AppRepository(context, callback);
        }
        return _INSTANCE;
    }

    public static void close(){
        _INSTANCE = null;
        //db.close(); //ToDo AppRepository cambiar
    }

    @Override
    public void onResult(List result) {
        Log.d("DEBUG", "Entity found");
        callback.onResult(result);
    }
}

