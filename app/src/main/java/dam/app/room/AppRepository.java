package dam.app.room;

import android.net.Uri;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import dam.app.extras.EnumSortOption;

public class AppRepository {
    private static AppCompatActivity _CONTEXT;
    private static AppRepository _INSTANCE = null;
    private static AppDatabase _DATABASE = null;

    protected final DAOComment daoComment;
    protected final DAOField daoField;
    protected final DAOReserve daoReserve;
    protected final DAOSchedule daoSchedule;
    protected final DAOUser daoUser;

    private Uri downloadUri;

    private AppRepository(final AppCompatActivity context){
        _CONTEXT = context;
        _DATABASE = AppDatabase.getInstance(_CONTEXT);
        daoComment = _DATABASE.daoComment();
        daoField = _DATABASE.daoField();
        daoReserve = _DATABASE.daoReserve();
        daoSchedule = _DATABASE.daoSchedule();
        daoUser = _DATABASE.daoUser();
    }

    public static AppRepository getInstance(final AppCompatActivity context) {
        if (_INSTANCE == null) _INSTANCE = new AppRepository(context);
        return _INSTANCE;
    }

    public List<FieldRm> getAllFields(EnumSortOption sortBy, RecyclerView recyclerView) {
        List<FieldRm> list = new ArrayList<>();
        switch (sortBy){
            case DIRECCION_CERCANA: list = daoField.findAllByProximity(); break;
            case DIRECCION_LEJANA:  list = daoField.findAllByProximityDesc(); break;
            case PUNTUACION_ALTA: list = daoField.findAllByScoreDesc(); break;
            case PUNTUACION_BAJA: list = daoField.findAllByScore(); break;
            case NOMBRE_ALFABETICO: list = daoField.findAllByName(); break;
        }

        if(list.isEmpty()){
           // VolatileData.persist(this);
            list = daoField.findAllByName();
        }
        return list;
    }

    public List<CommentRm> getCommentsFromField(long id, EnumSortOption sortBy, RecyclerView recyclerView) {
        List<CommentRm> list = new ArrayList<>();
        switch (sortBy){
            case PUNTUACION_ALTA: list = daoComment.findAllByScoreDesc(id); break;
            case PUNTUACION_BAJA:  list = daoComment.findAllByScore(id); break;
            case FECHA_CERCANA: list = daoComment.findAllByDateDesc(id); break;
            case FECHA_LEJANA: list = daoComment.findAllByDate(id); break;
        }

        if(list.isEmpty()){
            //VolatileData.persist(this);
            list = daoComment.findAllByDate(id);
        }
        return list;
    }

    public long saveComment(CommentRm comment){
        long idField = comment.getIdReserve();
        comment.setUsername("Setear el username");
        /*Reserve reserve = daoReserve.find(comment.getIdReserve());
        long idField = reserve.getIdField();
        User user = daoUser.find(reserve.getIdUser());
        comment.setUsername(user.getUserName());
        ToDo USER descomentar al hacer bien lo del user
        */

        long id = daoComment.insert(comment);
        comment.setId(id);
        if(id >= 0) updateRating(idField);
        return id;
    }

    /* todo UPDATE field rating
    private void onStarClicked(DatabaseReference postRef) {
        postRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                Post p = mutableData.getValue(Post.class);
                if (p == null) {
                    return Transaction.success(mutableData);
                }

                if (p.stars.containsKey(getUid())) {
                    // Unstar the post and remove self from stars
                    p.starCount = p.starCount - 1;
                    p.stars.remove(getUid());
                } else {
                    // Star the post and add self to stars
                    p.starCount = p.starCount + 1;
                    p.stars.put(getUid(), true);
                }

                // Set value and report transaction success
                mutableData.setValue(p);
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean committed,
                                   DataSnapshot currentData) {
                // Transaction completed
                Log.d(TAG, "postTransaction:onComplete:" + databaseError);
            }
        });
    }*/

    public void updateRating(long idField){
        /*List<Comment> comments = daoComment.findAllByScore(idField);

        float totalScore = 0f;
        int totalComments = 0;

        for (Comment c:comments) {
            totalComments++;
            totalScore += c.getScore();
        }

        Field field = daoField.find(idField);
        field.setRating(totalScore/totalComments);
        daoField.update(field);*/
    }

    public boolean isLogged(){
        //ToDo SESSION verificar si está logueado
        return true;
    }

    public static void close(){
        _INSTANCE = null;
        //_DATABASE.close();
    }
}

