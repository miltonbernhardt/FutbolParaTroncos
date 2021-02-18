package dam.app;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import dam.app.activity.ActivityMain;
import dam.app.activity.ActivityMenu;
import dam.app.extras.EnumPaths;
import dam.app.extras.EnumSortOption;
import dam.app.extras.EnumStateReserve;
import dam.app.extras.NotificationReserve;
import dam.app.extras.VolatileData;
import dam.app.model.Comment;
import dam.app.model.Field;
import dam.app.model.Reserve;
import dam.app.model.User;
import dam.app.adapters.CommentRecycler;
import dam.app.adapters.FieldRecycler;
import dam.app.adapters.ReservesRecycler;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AppFirebase {
    protected static ActivityMain _CONTEXT;
    private static AppFirebase _INSTANCE = null;
    private static DatabaseReference _FIREBASE;

    public static final String _FIELDS = "fields";
    public static final String _COMMENTS = "comments";
    public static final String _RESERVES = "reserves";
    public static final String _USERS = "users";

    private FirebaseUser userFirebase;
    private User userDB;

    private Uri downloadUri;

    private AppFirebase(final ActivityMain context){
        _CONTEXT = context;
        _FIREBASE = FirebaseDatabase.getInstance().getReference();
    }

    public static AppFirebase getInstance(final ActivityMain context) {
        if (_INSTANCE == null) _INSTANCE = new AppFirebase(context);
        else _CONTEXT = context;
        return _INSTANCE;
    }



    public void setFieldsRecyclerView(EnumSortOption sortBy, RecyclerView recyclerView) {
        switch (sortBy){
            case PUNTUACION_BAJA:
            case NOMBRE_ALFABETICO:
                _FIREBASE.child(_FIELDS).orderByChild(sortBy.toString()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        List<Field> list = new ArrayList<>();
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) list.add(postSnapshot.getValue(Field.class));
                        recyclerView.setAdapter(new FieldRecycler(list, (ActivityMain) _CONTEXT));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(_CONTEXT, R.string.failedOperation, Toast.LENGTH_LONG).show();
                    }
                });
                break;

            default:
                _FIREBASE.child(_FIELDS).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        List<Field> list = new ArrayList<>();
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) list.add(postSnapshot.getValue(Field.class));
                        switch (sortBy){
                            case PUNTUACION_ALTA:
                                list.sort((c1, c2) -> c2.getRating().compareTo(c1.getRating()));
                                break;

                            case DIRECCION_LEJANA:
                            case DIRECCION_CERCANA:
                                list.sort((c1, c2) -> c1.getAddress().compareTo(c2.getAddress()));
                                break;
                        }
                        recyclerView.setAdapter(new FieldRecycler(list, (ActivityMain) _CONTEXT));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
                break;

        }
    }

    public void setCommentsRecyclerView(String idField, EnumSortOption sortBy, RecyclerView recyclerView) {
        _FIREBASE.child(_COMMENTS).orderByChild("idField").equalTo(idField).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Comment> list = new ArrayList<>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) { list.add(postSnapshot.getValue(Comment.class)); }
                switch (sortBy){
                    case FECHA_CERCANA:
                        list.sort((c1, c2) -> c2.getDateOfCommentAsDate().compareTo(c1.getDateOfCommentAsDate()));
                        break;
                    case FECHA_LEJANA:
                        list.sort((c1, c2) -> c1.getDateOfCommentAsDate().compareTo(c2.getDateOfCommentAsDate()));
                        break;
                    case PUNTUACION_ALTA:
                        list.sort((c1, c2) -> c2.getScore().compareTo(c1.getScore()));
                        break;
                    case PUNTUACION_BAJA:
                        list.sort((c1, c2) -> c1.getScore().compareTo(c2.getScore()));
                        break;
                }
                recyclerView.setAdapter(new CommentRecycler(list, (ActivityMain) _CONTEXT));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(_CONTEXT, R.string.failedOperation, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void setReservesRecyclerView(RecyclerView recyclerView) {
        FirebaseUser u = FirebaseAuth.getInstance().getCurrentUser();
        _FIREBASE.child(_RESERVES).orderByChild("idUser").equalTo(u.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Reserve> list = new ArrayList<>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Reserve r = postSnapshot.getValue(Reserve.class);
                    updateStateReserve(r);
                    list.add(r);
                }
                list.sort((c1, c2) -> c2.getDateOfReserveAsDate().compareTo(c1.getDateOfReserveAsDate()));
                recyclerView.setAdapter(new ReservesRecycler(list, _CONTEXT));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void setReservesRecyclerViewByField(RecyclerView recyclerView, String idField) {
        FirebaseUser u = FirebaseAuth.getInstance().getCurrentUser();
        _FIREBASE.child(_RESERVES).orderByChild("idUser").equalTo(u.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Reserve> list = new ArrayList<>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Reserve r = postSnapshot.getValue(Reserve.class);
                    updateStateReserve(r);
                    if(r.getIdField().equals(idField)){
                        list.add(r);
                    }
                }
                list.sort((c1, c2) -> c2.getDateOfReserveAsDate().compareTo(c1.getDateOfReserveAsDate()));
                recyclerView.setAdapter(new ReservesRecycler(list, _CONTEXT));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void saveReserve(Reserve reserve){
        if(userDB != null) {
            _FIREBASE.child("users").child(userDB.getId()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    userDB = dataSnapshot.getValue(User.class);
                    reserve.setIdUser(userDB.getId());
                    writeNewObject(reserve);
                    Toast.makeText(_CONTEXT, R.string.successfulRegister, Toast.LENGTH_LONG).show();
                    _CONTEXT.finish();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }
    }

    public void saveComment(Comment comment){
        if(userDB != null) saveCommentAux(comment);
        else{
            userFirebase = FirebaseAuth.getInstance().getCurrentUser();
            if(userFirebase != null) {
                _FIREBASE.child("users").child(userFirebase.getUid()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        userDB = dataSnapshot.getValue(User.class);
                        saveCommentAux(comment);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
        }
    }

    private void saveCommentAux(Comment comment){
        comment.setUsername(userDB.getUserName());

        if(comment.getImagePath() != null && !comment.getImagePath().equals("")) {
            uploadImage(comment.getImagePath());
            if(downloadUri != null) comment.setImagePath(downloadUri.toString());
        }

        writeNewObject(comment);
        updateRatingField(comment.getIdField());
        updateReserveCommented(comment.getIdReserve());

        Toast.makeText(_CONTEXT, R.string.successfulSaveComment, Toast.LENGTH_LONG).show();
    }



    public void uploadImage(String pathImage) {
        Uri file = Uri.fromFile(new File(pathImage));

        StorageReference ref = FirebaseStorage.getInstance().getReference().child(EnumPaths.PATH_IMAGES_REVIEW+"/"+file.getLastPathSegment());
        UploadTask uploadTask = ref.putFile(file);
        //TODO añadir notificación de que se subió la foto correctamente
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



    public String writeNewObject(Field f) {
        DatabaseReference ref =  _FIREBASE.child(_FIELDS).push();
        String key = ref.getKey();
        f.setId(key);
        ref.setValue(f);
        return key;
    }

    public void writeNewObject(Reserve r) {
        DatabaseReference ref =  _FIREBASE.child(_RESERVES).push();
        String key = ref.getKey();
        r.setId(key);
        ref.setValue(r);
    }

    public void writeNewObject(Comment c) {
        DatabaseReference ref = _FIREBASE.child(_COMMENTS).push();
        String key = ref.getKey();
        c.setId(key);
        ref.setValue(c);
    }

    public void writeNewObject(User u) {
        userDB = u;
        _FIREBASE.child(_USERS).child(u.getId()).setValue(u);
    }

    public void updateObject(Field field) {
        _FIREBASE.child(_FIELDS).child( String.valueOf(field.getId()) ).setValue(field);
    }

    public void updateObject(Reserve reserve) {
        _FIREBASE.child(_RESERVES).child( String.valueOf(reserve.getId()) ).setValue(reserve);
    }

    private void updateRatingField(String idField){
        _FIREBASE.child(_FIELDS).child(idField).addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Field field = dataSnapshot.getValue(Field.class);
                        updateRatingField(field);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.w("on AppRepository", "loadPost:onCancelled", databaseError.toException());
                    }
                }
        );
    }

    private void updateRatingField(Field field) {
        _FIREBASE.child(_COMMENTS).orderByChild("idField").equalTo(field.getId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                float totalScore = 0f;
                int totalComments = 0;

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Comment c = postSnapshot.getValue(Comment.class);
                    totalComments++;
                    totalScore += c.getScore();
                }

                field.setRating(totalScore/totalComments);
                updateObject(field);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(_CONTEXT, R.string.failedOperation, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void updateStateReserve(Reserve r){
        if(r.getState().equals(EnumStateReserve.ACTIVA.toString())) {
            LocalDate today = LocalDate.now();
            if (r.getDateOfReserveAsDate().isAfter(today)) {
                r.setState(EnumStateReserve.USADA.toString());
                updateObject(r);
            } else {
                if (r.getDateOfReserveAsDate().equals(today) && LocalTime.now().getHour() > r.getStartTime()) {
                    r.setState(EnumStateReserve.USADA.toString());
                    updateObject(r);
                }
            }
        }
    }

    private void updateReserveCommented(String idReserve) {
        _FIREBASE.child(_RESERVES).child(idReserve).addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Reserve reserve = dataSnapshot.getValue(Reserve.class);
                        reserve.setHasCommented(true);
                        updateObject(reserve);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.w("on AppRepository", "loadPost:onCancelled", databaseError.toException());
                    }
                }
        );
    }

    public void updateHeaderDrawer(TextView username) {
        if(userDB == null) {
            userFirebase = FirebaseAuth.getInstance().getCurrentUser();
            if(userFirebase != null) {
                _FIREBASE.child(_USERS).child(userFirebase.getUid()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        User u = dataSnapshot.getValue(User.class);
                        username.setText(u.getUserName());
                        userDB = u;
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
            else username.setText("Usuario no logueado");
        }
        else{
            username.setText(userDB.getUserName());
        }
    }


    public void registerUser(User userParam){
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(userParam.getMail(), userParam.getPassword())
                .addOnCompleteListener(_CONTEXT, task -> {
                    if (task.isSuccessful()) {
                        userFirebase = FirebaseAuth.getInstance().getCurrentUser();
                        userParam.setId(userFirebase.getUid());
                        userDB = userParam;
                        Toast.makeText(_CONTEXT, _CONTEXT.getResources().getString(R.string.successfulRegister), Toast.LENGTH_SHORT).show();

                        VolatileData.addFakeData(_CONTEXT._FIREBASE, userParam.getId());
                        //Toast.makeText(_CONTEXT, _CONTEXT.getResources().getString(R.string.addingFakeData), Toast.LENGTH_SHORT).show();

                        writeNewObject(userDB);
                        _CONTEXT.startActivity(new Intent(_CONTEXT, ActivityMenu.class));
                        _CONTEXT.finish();
                    }
                    else {
                        Log.w("FAIL", "createUserWithEmail:failure", task.getException());
                        Toast.makeText(_CONTEXT,  _CONTEXT.getResources().getString(R.string.errorRegister), Toast.LENGTH_SHORT).show();
                    }
                });
    }
    public void signIn(String email, String password){
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener(_CONTEXT, task -> {
            if (task.isSuccessful()) {
                Log.w("on ActivityLogin", _CONTEXT.getResources().getString(R.string.successfulLogin));
                Toast.makeText(_CONTEXT, R.string.successfulLogin, Toast.LENGTH_SHORT).show();
                _CONTEXT.startActivity(new Intent(_CONTEXT, ActivityMenu.class));
                _CONTEXT.finish();
            } else {
                Log.w("on ActivityLogin", _CONTEXT.getResources().getString(R.string.errorLogin), task.getException());
                Toast.makeText(_CONTEXT, R.string.errorLogin, Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void signOut() { FirebaseAuth.getInstance().signOut(); }
    public boolean isLogged(){
        userFirebase = FirebaseAuth.getInstance().getCurrentUser();
        return userFirebase != null;
    }
}

