package com.tongminhnhut.admin_luanvan.DAL;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.tongminhnhut.admin_luanvan.Model.Banner;
import com.tongminhnhut.admin_luanvan.Model.Category;

import java.util.UUID;

public class BannerDAL {
    static StorageReference image ;
    public static Banner banner;
    public static DatabaseReference db_Category = FirebaseDatabase.getInstance().getReference("Banner");
    public static void upLoadImage(final Uri saveUri, final Context context, final EditText edtName, final EditText edtId,final ProgressDialog progressDialog) {
        image = FirebaseStorage.getInstance().getReference("images/");
        if (saveUri != null)
        {

            progressDialog.setMessage("Loading . . . ");
            progressDialog.show();

            String imageName = UUID.randomUUID().toString();
            final StorageReference imageFolder = image.child(imageName);
            imageFolder.putFile(saveUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {
                    progressDialog.dismiss();
                    Toast.makeText(context, "Upload Success !", Toast.LENGTH_SHORT).show();
                    imageFolder.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            banner = new Banner(edtName.getText().toString().trim(), String.valueOf(taskSnapshot.getDownloadUrl()),edtId.getText().toString());
                        }

                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Log.d("AAA", e.getMessage());
                    Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    int progress = (int) (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    progressDialog.setMessage("Uploaded "+ progress+ " %");
                }
            });

        }
    }

    public static void addNew (final RelativeLayout drawer){
        if (banner !=null){
            db_Category.push().setValue(banner);
            Snackbar.make(drawer,"Banner "+banner.getName()+ " đã được thêm mới ", Snackbar.LENGTH_LONG ).show();


        }

    }

    public static void changeImage(final Banner item, final Uri saveUri, final Context context, final ProgressDialog progressDialog) {
        image = FirebaseStorage.getInstance().getReference("images/");
        if (saveUri != null)
        {

            progressDialog.setMessage("Loading . . . ");
            progressDialog.show();

            String imageName = UUID.randomUUID().toString();
            final StorageReference imageFolder = image.child(imageName);
            imageFolder.putFile(saveUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {
                    progressDialog.dismiss();
                    Toast.makeText(context, "Upload Success !", Toast.LENGTH_SHORT).show();
                    imageFolder.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            item.setImage(String.valueOf(taskSnapshot.getDownloadUrl()));
                        }

                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Log.d("AAA", e.getMessage());
                    Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    int progress = (int) (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    progressDialog.setMessage("Uploaded "+ progress+ " %");
                }
            });

        }
    }

}
