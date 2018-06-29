package com.tongminhnhut.admin_luanvan.DAL;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
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
import com.tongminhnhut.admin_luanvan.Model.Category;
import com.tongminhnhut.admin_luanvan.Model.DongHo;

import java.util.UUID;

public class DongHoDAL {
    static StorageReference image = FirebaseStorage.getInstance().getReference("images/");
    public static DongHo dongHo ;
    public static DatabaseReference db_DongHo = FirebaseDatabase.getInstance().getReference("DongHo");
//    final EditText edtPrice, final EditText edtBrand,final EditText edtBaohanh,final EditText edtDaydeo,final EditText edtMay
//            ,

    public static void upLoadImage(final String menuId, final Uri saveUri, final Context context, final EditText edtName,
            final EditText edtXuatxu,final EditText edtDiscount,final EditText edtPrice,final EditText edtBrand,
                                   final EditText edtBaohanh,final EditText edtDaydeo,final EditText edtMay,
                                    final EditText edtSize,
                                    final ProgressDialog progressDialog) {
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
//                            category = new Category(edtName.getText().toString().trim(), String.valueOf(taskSnapshot.getDownloadUrl()));
                            dongHo = new DongHo();
                            dongHo.setName(edtName.getText().toString());
                            dongHo.setGia(edtPrice.getText().toString());
                            dongHo.setThuongHieu(edtBrand.getText().toString());
                            dongHo.setXuatXu(edtXuatxu.getText().toString());
                            dongHo.setDiscount(edtDiscount.getText().toString());
                            dongHo.setBaoHanh(edtBaohanh.getText().toString());
                            dongHo.setSize(edtSize.getText().toString());
                            dongHo.setDayDeo(edtDaydeo.getText().toString());
                            dongHo.setMay(edtMay.getText().toString());
                            dongHo.setMenuId(menuId);
                            dongHo.setImage(String.valueOf(taskSnapshot.getDownloadUrl()));
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

    public static void addNew(RelativeLayout relativeLayout){
        if (dongHo !=null){
            db_DongHo.push().setValue(dongHo);
            Snackbar.make(relativeLayout,"Sản phẩm "+dongHo.getName()+ " đã được thêm mới ", Snackbar.LENGTH_LONG ).show();


        }
    }

    public static void changeImage(final DongHo item, final Uri saveUri, final Context context, final ProgressDialog progressDialog) {
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
