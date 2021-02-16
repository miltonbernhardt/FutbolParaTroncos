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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.text.Normalizer;
import java.time.LocalDate;

import dam.app.R;
import dam.app.extras.EnumPaths;
import dam.app.extras.ImageHelper;
import dam.app.model.Comment;

public class ActivityNewComment extends ActivityMain {
    Button btnAddComment;
    EditText textComment;
    ImageButton btnCamera;
    ImageButton btnDeleteImage;
    ImageButton btnGallery;
    ImageView imageUpload;
    RatingBar ratingBar;
    TextView lblNameField;

    private String idField;
    private String idReserve;
    private String fieldName;

    private static final int CAMERA_REQUEST = 1;
    private static final int GALLERY_REQUEST = 2;

    private static final int PERMISSION_CODE_MY_CAMERA = 100;
    private static final int PICK_FROM_GALLERY_PERMISSION_CODE = 101;

    private String ID_PICTURE;
    private String IMAGE_PATH_CACHE;
    private String IMAGE_PATH_FINAL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_comment);
        createDrawable(this, false);

        idField = getIntent().getStringExtra("idField");
        idReserve = getIntent().getStringExtra("idReserve");
        fieldName = getIntent().getStringExtra("fieldName");

        btnAddComment = findViewById(R.id.btnAddComment);
        btnCamera = findViewById(R.id.btnCamera);
        btnDeleteImage = findViewById(R.id.btnDeleteImage);
        btnGallery = findViewById(R.id.btnGallery);
        imageUpload = findViewById(R.id.imageUpload);
        lblNameField = findViewById(R.id.lblNameField);
        ratingBar = findViewById(R.id.ratingBar);
        textComment = findViewById(R.id.textComment);

        lblNameField.setText(fieldName);

        btnAddComment.setOnClickListener(v -> { saveComment(); });

        btnCamera.setOnClickListener(v -> {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                requestPermissions(new String[]{Manifest.permission.CAMERA}, PERMISSION_CODE_MY_CAMERA);
            else openCamera();
        });

        btnDeleteImage.setVisibility(View.INVISIBLE);
        btnDeleteImage.setOnClickListener(v -> { deleteImage(); });

        btnGallery.setOnClickListener(v -> {
            if (ActivityCompat.checkSelfPermission(_CONTEXT, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                ActivityCompat.requestPermissions(_CONTEXT, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PICK_FROM_GALLERY_PERMISSION_CODE);
            else  openGallery();
        });
        setIdPicture();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode){
                case GALLERY_REQUEST:
                    Uri imageUri = data.getData();
                    try {
                        Bitmap bitmapImage = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                        setImageFinal(bitmapImage);
                    } catch (IOException ignored) { }
                    break;
                case CAMERA_REQUEST:
                    Bitmap bitmapImage = BitmapFactory.decodeFile(IMAGE_PATH_CACHE);
                    setImageFinal(bitmapImage);
                    break;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_CODE_MY_CAMERA){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) openCamera();
            else Toast.makeText(_CONTEXT, R.string.errorCamera, Toast.LENGTH_SHORT).show();
        } else if  (requestCode == PICK_FROM_GALLERY_PERMISSION_CODE){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) openGallery();
            else Toast.makeText(_CONTEXT, R.string.errorGallery, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        deleteCache();
    }

    private void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;

            try {
                deleteCache();
                photoFile = File.createTempFile(ID_PICTURE,".tmp", _CONTEXT.getApplicationContext().getExternalFilesDir(EnumPaths.PATH_IMAGES_REVIEW.toString()));
                IMAGE_PATH_CACHE = photoFile.getAbsolutePath();
            } catch (IOException ex) { Toast.makeText(_CONTEXT, R.string.errorUploading, Toast.LENGTH_LONG).show(); }

            if (photoFile != null) {
                Uri imageUri = FileProvider.getUriForFile(_CONTEXT.getApplicationContext(),"dam.app.provider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(takePictureIntent, CAMERA_REQUEST);
            }
        }
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY_REQUEST);
    }

    private void deleteImage(){
        imageUpload.setImageDrawable(null);
        btnDeleteImage.setVisibility(View.INVISIBLE);
        if(IMAGE_PATH_FINAL != null && IMAGE_PATH_FINAL != ""){
            File originalFile = new File(IMAGE_PATH_FINAL);
            originalFile.delete();
            IMAGE_PATH_FINAL = null;
        }
    }

    private void deleteCache(){
        if(IMAGE_PATH_CACHE != null && !IMAGE_PATH_CACHE.equals("")){
            File originalFile = new File(IMAGE_PATH_CACHE);
            originalFile.delete();
        }
    }

    private void setIdPicture(){
        String s = Normalizer.normalize(fieldName, Normalizer.Form.NFD);
        s = s.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        ID_PICTURE = "field-"+s.replace(" ", "-")+"-"+System.currentTimeMillis();
    }

    private void setImageFinal(Bitmap bitmapImage){

        int nh = (int) ( bitmapImage.getHeight() * (512.0 / bitmapImage.getWidth()) );
        Bitmap scaled = Bitmap.createScaledBitmap(bitmapImage, 512, nh, true);

        imageUpload.setImageBitmap(scaled);

        deleteCache();
        IMAGE_PATH_FINAL = ImageHelper.persistImage(scaled, ID_PICTURE, _CONTEXT, ".jpg", EnumPaths.PATH_IMAGES_REVIEW.toString());
        btnDeleteImage.setVisibility(View.VISIBLE);
    }

    private void saveComment() {

        float score = ratingBar.getRating();
        String txtComment = textComment.getText().toString();

        if(score == 0.0) Toast.makeText(_CONTEXT, R.string.should_rate, Toast.LENGTH_LONG).show();
        else{
            Comment comment = new Comment();
            comment.setComment(txtComment);
            comment.setScore((int)score);
            comment.setImagePath(IMAGE_PATH_FINAL);
            comment.setIdField(idField);
            comment.setIdReserve(idReserve);
            comment.setDateOfCommentFromDate(LocalDate.now());

            _FIREBASE.saveComment(comment);

            Toast.makeText(_CONTEXT, R.string.successfulSaveComment, Toast.LENGTH_LONG).show();
            finish();

            /*Observable<String> observer = Observable.create(subscriber -> {
                subscriber.onNext(_FIREBASE.saveComment(comment));
                subscriber.onCompleted();
            });

            _SUBSCRIPTION = observer.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                    id -> {
                        if(id != null && !id.equals("")){
                            Log.d("on ActivityNewComment", _CONTEXT.getResources().getString(R.string.errorSaveComment));
                            Toast.makeText(_CONTEXT, R.string.errorSaveComment, Toast.LENGTH_LONG).show();
                        }
                        else{
                            Log.d("on ActivityNewComment", _CONTEXT.getResources().getString(R.string.successfulSaveComment));
                            Toast.makeText(_CONTEXT, R.string.successfulSaveComment, Toast.LENGTH_LONG).show();
                            finish();
                        }
                    } ,
                    error -> { error.printStackTrace(); Toast.makeText(_CONTEXT, R.string.errorSaveComment, Toast.LENGTH_LONG).show();});*/
        }
    }
}