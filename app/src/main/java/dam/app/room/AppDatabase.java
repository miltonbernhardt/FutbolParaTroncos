package dam.app.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import dam.app.model.Comment;
import dam.app.model.Field;
import dam.app.model.Reserve;
import dam.app.model.Schedule;
import dam.app.model.User;

@Deprecated
@Database(entities = {Comment.class, Field.class, Reserve.class, Schedule.class, User.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase _INSTANCE;

    public abstract DAOComment daoComment();
    public abstract DAOField daoField();
    public abstract DAOReserve daoReserve();
    public abstract DAOSchedule daoSchedule();
    public abstract DAOUser daoUser();

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