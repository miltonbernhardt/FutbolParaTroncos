package dam.app.database;

import android.net.Uri;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

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

import dam.app.R;
import dam.app.activity.ActivityMain;
import dam.app.extras.EnumPaths;
import dam.app.extras.EnumSortOption;
import dam.app.model.Comment;
import dam.app.model.Field;
import dam.app.model.Reserve;
import dam.app.model.Schedule;
import dam.app.model.User;
import dam.app.recycler.CommentRecycler;
import dam.app.recycler.FieldRecycler;

public class AppFirebase {
    private static AppCompatActivity _CONTEXT;
    private static AppFirebase _INSTANCE = null;
    private static DatabaseReference _FIREBASE;

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

    public void getCommentsFromField(long id, EnumSortOption sortBy, RecyclerView recyclerView) {
        Query myTopPostsQuery = _FIREBASE.child("comments").orderByChild(sortBy.toString());
        myTopPostsQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Comment> list = new ArrayList<>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Comment c = postSnapshot.getValue(Comment.class);
                    if(c.getIdReserve() == id) list.add(c);
                }
                recyclerView.setAdapter(new CommentRecycler(list, (ActivityMain) _CONTEXT));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(_CONTEXT, R.string.failedOperation, Toast.LENGTH_LONG).show();
            }
        });
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

        if(comment.getImagePath() != null && !comment.getImagePath().equals("")) {
            uploadImage(comment.getImagePath());
            if(downloadUri != null) comment.setImagePath(downloadUri.toString());
        }
        writeNewObject(comment);
        /*//long id = daoComment.insert(comment);
        comment.setId(id);
        writeNewObject(comment);
        if(id >= 0) updateRating(idField);
        return id;*/
        return 1;
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


    // ------------------- FIREBASE ------------------------

    public void uploadImage(String pathImage) {
        Uri file = Uri.fromFile(new File(pathImage));

        StorageReference ref = FirebaseStorage.getInstance().getReference().child(EnumPaths.PATH_IMAGES_REVIEW +file.getLastPathSegment());
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

    public void writeNewObject(Field f) {
        _FIREBASE.child("fields").child(String.valueOf(f.getId())).setValue(f);

    }

    public void writeNewObject(Schedule s) {
        _FIREBASE.child("fields").child(String.valueOf(s.getIdField())).child("schedule").setValue(s);
    }

    public void writeNewObject(User user) {
        _FIREBASE.child("users").child( String.valueOf(user.getId()) ).setValue(user);
    }

    public void writeNewObject(Reserve reserve) {
        _FIREBASE.child("reserve").child( String.valueOf(reserve.getId()) ).setValue(reserve);
    }

    public void writeNewObject(Comment comment) {
        DatabaseReference ref = _FIREBASE.child("comments").push();
        //comment.setId(ref.getKey());
        ref.setValue(comment);
    }

    // --------------------- MODIFICAR DATOS ---------------------

    public void updateObject(Field field) {
        _FIREBASE.child("reserve").child( String.valueOf(field.getId()) ).setValue(field);
    }

    // --------------------- OBTENER DATOS ---------------------
    /*private Field field;
    public Field getField(){
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                field = dataSnapshot.getValue(Field.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("on AppRepository", "loadPost:onCancelled", databaseError.toException());
            }
        };
        _FIREBASE.addListenerForSingleValueEvent(postListener);
        return  field;
    }*/
}

