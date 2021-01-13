package dam.app.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import dam.app.dao.DAOCancha;
import dam.app.model.Cancha;

@Database(entities = {Cancha.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase _INSTANCE;

    public abstract DAOCancha daoCancha();

    private static final int NUMBER_OF_THREADS = 1;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static AppDatabase getInstance(final Context context) {
        if (_INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (_INSTANCE == null) {
                    _INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "AppDatabase")
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return _INSTANCE;
    }

    @Override
    public void close(){
        super.close();
        _INSTANCE = null;
    }
}