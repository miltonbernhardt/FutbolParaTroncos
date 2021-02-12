package dam.app.extras;

import android.net.Uri;

import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class FirebasePhotos {

    public static FirebaseStorage storage;
    public static StorageReference storageRef;
    public static StorageReference platosImagesRef;
    public static Uri downloadUri;

    public static void getStorageReference() {
        StorageReference storageRef = storage.getReference();
        StorageReference platosImagesRef = storageRef.child("images/field.jpg");
    }

    public static void putBytes(byte[] dataImage) {
        UploadTask uploadTask = platosImagesRef.putBytes(dataImage);
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
    }

    public static void putFile(Uri file) {
        UploadTask uploadTask = platosImagesRef.putFile(file);

        uploadTask.continueWithTask(task -> {
            if (!task.isSuccessful()) throw task.getException();
            return platosImagesRef.getDownloadUrl();
        }).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                downloadUri = task.getResult();
            } else {
                // Fallo
            }
        });
    }

    public static void someFunction(byte[] dataImage) {
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
    }
}
