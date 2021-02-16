package dam.app;

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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import dam.app.activity.ActivityMain;
import dam.app.extras.EnumPaths;
import dam.app.extras.EnumSortOption;
import dam.app.model.Comment;
import dam.app.model.Field;
import dam.app.model.Reserve;
import dam.app.model.User;
import dam.app.recycler.CommentRecycler;
import dam.app.recycler.FieldRecycler;

public class AppFirebase {
    private static AppCompatActivity _CONTEXT;
    private static AppFirebase _INSTANCE = null;
    private static DatabaseReference _FIREBASE;

    public static final String _FIELDS = "fields";
    public static final String _COMMENTS = "comments";
    public static final String _RESERVES = "reserves";
    public static final String _USERS = "users";

    private Uri downloadUri;

    private AppFirebase(final AppCompatActivity context){
        _CONTEXT = context;
        _FIREBASE = FirebaseDatabase.getInstance().getReference();
    }

    public static AppFirebase getInstance(final AppCompatActivity context) {
        if (_INSTANCE == null) _INSTANCE = new AppFirebase(context);
        return _INSTANCE;
    }

    public void getAllFields(EnumSortOption sortBy, RecyclerView recyclerView) {
        Query myTopPostsQuery = null;
        if(sortBy.equals(EnumSortOption.DIRECCION_CERCANA)){
            myTopPostsQuery = _FIREBASE.child("fields");
            myTopPostsQuery.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    List<Field> fields = convertFieldsFromServer(dataSnapshot);

                    //ToDo hacer lo de la dirección más cercana
                        /*Location locationA = new Location("punto A");

                        locationA.setLatitude(latA);
                        locationA.setLongitude(lngA);

                        Location locationB = new Location("punto B");

                        locationB.setLatitude(latB);
                        locationB.setLongitude(lngB);

                        float distance = locationA.distanceTo(locationB);*/

                    recyclerView.setAdapter(new FieldRecycler(fields, (ActivityMain) _CONTEXT));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }
        else{
            myTopPostsQuery = _FIREBASE.child("fields").orderByChild(sortBy.toString());
            myTopPostsQuery.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    recyclerView.setAdapter(new FieldRecycler(convertFieldsFromServer(dataSnapshot), (ActivityMain) _CONTEXT));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(_CONTEXT, R.string.failedOperation, Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private List<Field> convertFieldsFromServer(DataSnapshot dataSnapshot){
        List<Field> list = new ArrayList<>();
        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
            list.add(postSnapshot.getValue(Field.class));
        }
        return list;
    }

    public void getCommentsFromField(String id, EnumSortOption sortBy, RecyclerView recyclerView) {
        Query myTopPostsQuery;

        if(sortBy.equals(EnumSortOption.FECHA_CERCANA)) myTopPostsQuery = _FIREBASE.child(_COMMENTS);
        else myTopPostsQuery = _FIREBASE.child(_COMMENTS).orderByChild(sortBy.toString());

        myTopPostsQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Comment> list = new ArrayList<>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Comment c = postSnapshot.getValue(Comment.class);
                    if(c.getIdField()!= null && c.getIdField().equals(id)) list.add(c);
                }
                recyclerView.setAdapter(new CommentRecycler(list, (ActivityMain) _CONTEXT));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(_CONTEXT, R.string.failedOperation, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void saveComment(Comment comment){
        comment.setUsername("Setear el username");

        /*Reserve reserve = daoReserve.find(comment.getIdReserve());
        long idField = reserve.getIdField();
        User user = daoUser.find(reserve.getIdUser());
        comment.setUsername(user.getUserName());
        ToDo USER descomentar al hacer bien lo del user
        */

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

    public String writeNewObject(User u) {
        DatabaseReference ref =  _FIREBASE.child(_USERS).push();
        String key = ref.getKey();
        u.setId(key);
        ref.setValue(u);
        return key;
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
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Field field = dataSnapshot.getValue(Field.class);
                        updateRatingField(field);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w("on AppRepository", "loadPost:onCancelled", databaseError.toException());
                    }
                }
        );
    }

    public void updateRatingField(Field field) {
        _FIREBASE.child(_COMMENTS).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Comment> list = new ArrayList<>();

                float totalScore = 0f;
                int totalComments = 0;

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Comment c = postSnapshot.getValue(Comment.class);
                    assert c != null;
                    if(c.getIdField() != null && c.getIdField().equals(field.getId())){
                        totalComments++;
                        totalScore += c.getScore();
                    }
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

    public boolean isLogged(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null) {
            Log.d("user", " - " + user.getDisplayName());
            Log.d("pass", " - " + user.getEmail());
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

