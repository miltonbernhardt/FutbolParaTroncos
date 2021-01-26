package dam.app.database;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import dam.app.R;
import dam.app.activity.ActivityComments;
import dam.app.activity.ActivityFields;
import dam.app.dao.DAOComment;
import dam.app.dao.DAOField;
import dam.app.dao.DAOReserve;
import dam.app.dao.DAOSchedule;
import dam.app.dao.DAOUser;
import dam.app.model.Comment;
import dam.app.model.Field;
import dam.app.recycler.CommentRecycler;
import dam.app.recycler.FieldRecycler;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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
        if (_INSTANCE == null) {
            _INSTANCE = new AppRepository(context);
        }
        return _INSTANCE;
    }

    public Subscriber<List<Comment>> getCommentsSubscriber(RecyclerView recyclerView){
        Observable<List<Comment>> observer2 = Observable.create((Observable.OnSubscribe<List<Comment>>) observer -> {
            observer.onNext(daoComment.findAll());
            observer.onCompleted();
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());

        Subscriber<List<Comment>> subscribe = new Subscriber<List<Comment>>() {
            final View view = _CONTEXT.getWindow().getDecorView().getRootView();

            @Override
            public void onCompleted() { }

            @Override
            public void onError(Throwable e) {
                Snackbar.make(view, _CONTEXT.getResources().getString(R.string.failedOperation), Snackbar.LENGTH_LONG).show();
            }

            @Override
            public void onNext(List<Comment> value) {
                if(value.isEmpty()) {
                    //ToDo AL FINAL quitar esto
                    if(daoField.findAll().isEmpty()){
                        for (Field f : VolatileData.getFields()) {
                            daoField.insert(f);
                        }
                    }
                    for (Comment c : VolatileData.getComments()) {
                        daoComment.insert(c);
                    }
                    Snackbar.make(view, _CONTEXT.getResources().getString(R.string.noCommentsInDB), Snackbar.LENGTH_LONG).show();
                    value = daoComment.findAll();
                }

                CommentRecycler adapter = new CommentRecycler((ActivityComments)_CONTEXT, value);
                recyclerView.setAdapter(adapter);
            }
        };
        observer2.subscribe(subscribe);
        return subscribe;
    }

    public Subscriber<List<Field>> getFieldsSubscriber(RecyclerView recyclerView) {
        Observable<List<Field>> observer2 = Observable.create((Observable.OnSubscribe<List<Field>>) observer -> {
            observer.onNext(daoField.findAll());
            observer.onCompleted();
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());

        Subscriber<List<Field>> subscribe = new Subscriber<List<Field>>() {
            final View view = _CONTEXT.getWindow().getDecorView().getRootView();

            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                Snackbar.make(view, _CONTEXT.getResources().getString(R.string.failedOperation), Snackbar.LENGTH_LONG).show();
            }

            @Override
            public void onNext(List<Field> value) {
                if (value.isEmpty()) {
                    //Todo AL FINAL quitar esto
                    for (Field f : VolatileData.getFields()) {
                        daoField.insert(f);
                    }
                    Snackbar.make(view, _CONTEXT.getResources().getString(R.string.noFieldsInDB), Snackbar.LENGTH_LONG).show();
                    value = daoField.findAll();
                }

                FieldRecycler adapter = new FieldRecycler((ActivityFields) _CONTEXT, value);
                recyclerView.setAdapter(adapter);
            }
        };
        observer2.subscribe(subscribe);
        return subscribe;
    }

    public static void close(){
        _INSTANCE = null;
        db.close();
    }
}

