package dam.app.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;

import dam.app.R;
import dam.app.extras.ImageHelper;
import dam.app.model.Comment;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ActivityNewComment extends ActivityMain {
    protected Button btnAddComment;
    protected EditText textComment;
    protected ImageButton btnCamera;
    protected ImageButton btnDeleteImage;
    protected ImageButton btnGallery;
    protected ImageView imageUpload;
    protected RatingBar ratingBar;
    protected TextView lblNameField;

    protected long idReserve;
    protected String fieldName;

    private static final int CAMERA_REQUEST = 1;
    private static final int GALLERY_REQUEST = 2;

    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private static final int PICK_FROM_GALLERY_PERMISSION_CODE = 101;

    FirebaseStorage storage;
    StorageReference storageRef;
    StorageReference platosImagesRef;
    byte[] dataImage;
    Uri downloadUri;

    /*private void getStorageReference() {
        StorageReference storageRef = storage.getReference();
        StorageReference platosImagesRef = storageRef.child("images/field.jpg");
    }

    private void someFunction() {
        UploadTask uploadTask = platosImagesRef.putBytes(dataImage);
        // UploadTask uploadTask = platosImagesRef.putFile(file);
        // UploadTask uploadTask = platosImagesRef.putStream(stream);

        Task<Uri> urlTask = uploadTask.continueWithTask(task -> {
            if (!task.isSuccessful()) throw task.getException();
            return platosImagesRef.getDownloadUrl();
        }).addOnCompleteListener(task -> {
            if (task.isSuccessful())  { downloadUri = task.getResult(); }
            else {
                // Fallo
            }
        });
    }*/

    private void launchCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == CAMERA_REQUEST ||  requestCode == GALLERY_REQUEST) && resultCode == Activity.RESULT_OK) {
            Bitmap image = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            dataImage = baos.toByteArray();
            //imageUpload.setImageBitmap(ImageHelper.getRoundedCornerBitmap(image, 20));
            imageUpload.setImageBitmap(BitmapFactory.decodeByteArray(dataImage, 0, dataImage.length));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_comment);
        createDrawable(this);

//        mAuth = FirebaseAuth.getInstance();
  //      signInAnonymously();
        //storage = FirebaseStorage.getInstance();

        idReserve = getIntent().getLongExtra("idReserve", -1);
        fieldName = getIntent().getStringExtra("fieldTitle");

        btnAddComment = findViewById(R.id.btnAddComment);
        btnCamera = findViewById(R.id.btnCamera);
        btnDeleteImage = findViewById(R.id.btnDeleteImage);
        btnGallery = findViewById(R.id.btnGallery);
        imageUpload = findViewById(R.id.imageUpload);
        lblNameField = findViewById(R.id.lblNameField);
        ratingBar = findViewById(R.id.ratingBar);
        textComment = findViewById(R.id.textComment);

        lblNameField.setText(fieldName);

        btnCamera.setOnClickListener(v -> {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
            else launchCamera();
        });
        btnDeleteImage.setOnClickListener(v -> { imageUpload.setImageDrawable(null); });
        btnGallery.setOnClickListener(v -> {
            if (ActivityCompat.checkSelfPermission(_CONTEXT, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                ActivityCompat.requestPermissions(_CONTEXT, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PICK_FROM_GALLERY_PERMISSION_CODE);
            else  openGallery();
        });

        btnAddComment.setOnClickListener(v -> {
            float score = ratingBar.getRating();
            String txtComment = textComment.getText().toString();

            if(score == 0.0) Toast.makeText(_CONTEXT, R.string.should_rate, Toast.LENGTH_LONG).show();
            else{
                Comment comment = new Comment();
                comment.setComment(txtComment);
                comment.setScore((int)score);
                comment.setImageUUID("");
                comment.setDateOfComment(LocalDate.now());
                comment.setIdReserve(idReserve);
                if(imageUpload.getDrawable() != null){
                    //ToDo IMAGE save image
                    comment.setImageUUID("b");
                }
                addComment(comment);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MY_CAMERA_PERMISSION_CODE){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                launchCamera();
            }
            else Toast.makeText(this, R.string.errorCamera, Toast.LENGTH_LONG).show();
        } else if  (requestCode == PICK_FROM_GALLERY_PERMISSION_CODE){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                openGallery();
            }
            else Toast.makeText(this, R.string.errorGallery, Toast.LENGTH_LONG).show();
        }
    }

    public void addComment(Comment comment) {
        Observable<Long> observer = Observable.create(subscriber -> {
            subscriber.onNext(_REPOSITORY.saveComment(comment));
            subscriber.onCompleted();
        });

        _SUBSCRIPTION = observer.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                id -> {
                    if(id < 0){
                        Log.d("on ActivityNewComment", _CONTEXT.getResources().getString(R.string.errorSaveComment));
                        Toast.makeText(_CONTEXT, R.string.errorSaveComment, Toast.LENGTH_LONG).show();
                    }
                    else{
                        Log.d("on ActivityNewComment", _CONTEXT.getResources().getString(R.string.successfulSaveComment));
                        Toast.makeText(_CONTEXT, R.string.successfulSaveComment, Toast.LENGTH_LONG).show();
                        finish();
                    }
                } ,
                error -> Toast.makeText(_CONTEXT, R.string.errorSaveComment, Toast.LENGTH_LONG).show());
    }
}