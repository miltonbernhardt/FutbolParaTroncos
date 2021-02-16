package dam.app;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
import java.util.ArrayList;
import java.util.List;

import dam.app.activity.ActivityMain;
import dam.app.activity.ActivityMenu;
import dam.app.activity.ActivityRegisterUser;
import dam.app.activity.ActivityReserves;
import dam.app.extras.EnumPaths;
import dam.app.extras.EnumSortOption;
import dam.app.model.Comment;
import dam.app.model.Field;
import dam.app.model.Reserve;
import dam.app.model.User;
import dam.app.recycler.CommentRecycler;
import dam.app.recycler.FieldRecycler;
import dam.app.recycler.ReservesRecycler;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

public class AppFirebase {
    private static ActivityMain _CONTEXT;
    private static AppFirebase _INSTANCE = null;
    private static DatabaseReference _FIREBASE;

    public static final String _FIELDS = "fields";
    public static final String _COMMENTS = "comments";
    public static final String _RESERVES = "reserves";
    public static final String _USERS = "users";

    private User user;

    private Uri downloadUri;

    private AppFirebase(final ActivityMain context){
        _CONTEXT = context;
        _FIREBASE = FirebaseDatabase.getInstance().getReference();
    }

    public static AppFirebase getInstance(final ActivityMain context) {
        if (_INSTANCE == null) _INSTANCE = new AppFirebase(context);
        return _INSTANCE;
    }

    public void getAllFields(EnumSortOption sortBy, RecyclerView recyclerView) {
        switch (sortBy){
            case PUNTUACION_BAJA:
            case NOMBRE_ALFABETICO:
                _FIREBASE.child("fields").orderByChild(sortBy.toString()).addValueEventListener(new ValueEventListener() {
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
                _FIREBASE.child("fields").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        List<Field> list = new ArrayList<>();
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) list.add(postSnapshot.getValue(Field.class));
                        switch (sortBy){
                            case PUNTUACION_ALTA:
                                list.sort((c1, c2) -> c2.getRating().compareTo(c1.getRating()));
                                break;

                            case DIRECCION_CERCANA:
                                list.sort((c1, c2) -> c1.getAddress().compareTo(c2.getAddress()));
                                break;
                            case DIRECCION_LEJANA:
                                //ToDo hacer lo de la dirección más cercana
                                /*Location locationA = new Location("punto A");

                                locationA.setLatitude(latA);
                                locationA.setLongitude(lngA);

                                Location locationB = new Location("punto B");

                                locationB.setLatitude(latB);
                                locationB.setLongitude(lngB);

                                float distance = locationA.distanceTo(locationB);*/
                                list.sort((c1, c2) -> c2.getAddress().compareTo(c1.getAddress()));
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

    public void getCommentsFromField(String idField, EnumSortOption sortBy, RecyclerView recyclerView) {
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

    public void getReservesByUser(String idUser, RecyclerView recyclerView) {
        _FIREBASE.child("reserves").orderByChild("idUser").equalTo(idUser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Reserve> list = new ArrayList<>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    list.add(postSnapshot.getValue(Reserve.class));
                }
                list.sort((c1, c2) -> c2.getDateOfReserveAsDate().compareTo(c1.getDateOfReserveAsDate()));
                recyclerView.setAdapter(new ReservesRecycler(list, _CONTEXT));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void setActualUser() {
        FirebaseUser u = FirebaseAuth.getInstance().getCurrentUser();
        assert u != null;
        _FIREBASE.child(_USERS).child(u.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(_CONTEXT, R.string.failedOperation, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void saveComment(Comment comment){
        comment.setUsername(user.getUserName());

        if(comment.getImagePath() != null && !comment.getImagePath().equals("")) {
            uploadImage(comment.getImagePath());
            if(downloadUri != null) comment.setImagePath(downloadUri.toString());
        }

        writeNewObject(comment);
        updateRatingField(comment.getIdField());
    }

    public void uploadImage(String pathImage) {
        Uri file = Uri.fromFile(new File(pathImage));

        StorageReference ref = FirebaseStorage.getInstance().getReference().child(EnumPaths.PATH_IMAGES_REVIEW+"/"+file.getLastPathSegment());
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

    // --------------------- SUBIR DATOS ---------------------

    public String writeNewObject(Field f) {
        DatabaseReference ref =  _FIREBASE.child(_FIELDS).push();
        String key = ref.getKey();
        f.setId(key);
        ref.setValue(f);
        return key;
    }

    public void writeNewObject(User u) {
        _FIREBASE.child(_USERS).child(u.getId()).setValue(u);
    }

    public String writeNewObject(Reserve r) {
        DatabaseReference ref =  _FIREBASE.child(_RESERVES).push();
        String key = ref.getKey();
        r.setId(key);
        ref.setValue(r);
        return key;
    }

    public String writeNewObject(Comment c) {
        DatabaseReference ref = _FIREBASE.child(_COMMENTS).push();
        String key = ref.getKey();
        c.setId(key);
        ref.setValue(c);
        return key;
    }




    public void updateObject(Field field) {
        _FIREBASE.child(_FIELDS).child( String.valueOf(field.getId()) ).setValue(field);
    }

    public void updateRatingField(String id){
        _FIREBASE.child(_FIELDS).child(id).addValueEventListener(
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

    public void updateRatingField(Field field) {
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



    public void registerUser(User user){
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(user.getMail(), user.getPassword())
                .addOnCompleteListener(_CONTEXT, task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(_CONTEXT, _CONTEXT.getResources().getString(R.string.successfulRegister), Toast.LENGTH_SHORT).show();
                        FirebaseUser u = FirebaseAuth.getInstance().getCurrentUser();
                        user.setId(u.getUid());
                        writeNewObject(user);
                        _CONTEXT.startActivity(new Intent(_CONTEXT, ActivityMenu.class));
                        _CONTEXT.finish();
                    } else {
                        Log.w("FAIL", "createUserWithEmail:failure", task.getException());
                        Toast.makeText(_CONTEXT,  _CONTEXT.getResources().getString(R.string.errorRegister), Toast.LENGTH_SHORT).show();
                    }
                });
    }



    public boolean isLogged(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null) {
            return true;
        }
        return false;
    }

    public static void close(){
        _INSTANCE = null;
    }

    public void signOut() {
        FirebaseAuth.getInstance().signOut();
    }
}

