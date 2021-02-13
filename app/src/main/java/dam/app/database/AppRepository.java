package dam.app.database;

import android.net.Uri;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import dam.app.R;
import dam.app.dao.DAOComment;
import dam.app.dao.DAOField;
import dam.app.dao.DAOReserve;
import dam.app.dao.DAOSchedule;
import dam.app.dao.DAOUser;
import dam.app.extras.EnumSortOption;
import dam.app.extras.VolatileData;
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
            case PUNTUACION_ALTA: list = daoComment.findAllByScoreDesc(id); break;
            case PUNTUACION_BAJA:  list = daoComment.findAllByScore(id); break;
            case FECHA_CERCANA: list = daoComment.findAllByDateDesc(id); break;
            case FECHA_LEJANA: list = daoComment.findAllByDate(id); break;
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
            case DIRECCION_CERCANA: list = daoField.findAllByProximity(); break;
            case DIRECCION_LEJANA:  list = daoField.findAllByProximityDesc(); break;
            case PUNTUACION_ALTA: list = daoField.findAllByScoreDesc(); break;
            case PUNTUACION_BAJA: list = daoField.findAllByScore(); break;
            case NOMBRE_ALFABETICO: list = daoField.findAllByName(); break;
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
        long idField = comment.getIdReserve();
        comment.setUsername("Setear el username");
        /*Reserve reserve = daoReserve.find(comment.getIdReserve());
        long idField = reserve.getIdField();
        User user = daoUser.find(reserve.getIdUser());
        comment.setUsername(user.getUserName());
        ToDo USER descomentar al hacer bien lo del user
        */

        if(comment.getImageURI() != null && !comment.getImageURI().equals("")) {
            putFile(comment.getImageURI());
            if(downloadUri != null) comment.setImageURI(downloadUri.toString());
        }

        long id = daoComment.insert(comment);
        if(id >= 0) updateRating(idField);
        return id;
    }


    private Uri downloadUri;

    private void putFile(String pathImage) {
        Uri file = Uri.fromFile(new File(pathImage));

        StorageReference ref = FirebaseStorage.getInstance().getReference().child("reviewImages/"+file.getLastPathSegment());
        UploadTask uploadTask = ref.putFile(file);


        uploadTask.continueWithTask(task -> {
            if (!task.isSuccessful()) throw task.getException();
            return ref.getDownloadUrl();
        }).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                downloadUri = task.getResult();
            } else {
                Toast.makeText(_CONTEXT, R.string.errorUploading, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void updateRating(long idField){
        List<Comment> comments = daoComment.findAllByScore(idField);

        float totalScore = 0f;
        int totalComments = 0;

        for (Comment c:comments) {
            totalComments++;
            totalScore += c.getScore();
        }

        Field field = daoField.find(idField);
        field.setRating(totalScore/totalComments);
        daoField.update(field);
    }

    public boolean isLogged(){
        //ToDo SESSION verificar si está logueado
        return true;
    }

    public static void close(){
        _INSTANCE = null;
        db.close();
    }
}

