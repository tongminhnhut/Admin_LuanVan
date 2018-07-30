package com.tongminhnhut.admin_luanvan.DAL;

import android.content.Context;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tongminhnhut.admin_luanvan.BLL.MD5;
import com.tongminhnhut.admin_luanvan.Model.User;

import dmax.dialog.SpotsDialog;

public class SignUpDAL {
    static DatabaseReference db_User ;
    public static void signUp (final Intent intent, final Context context, final EditText phone, final EditText pass, final EditText name, final SpotsDialog dialog){
        db_User = FirebaseDatabase.getInstance().getReference("User");
        dialog.show();
        db_User.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(phone.getText().toString().trim()).exists()){
                    dialog.dismiss();
//                    Toast.makeText(context, "Tài khoản đã tồn tại !", Toast.LENGTH_SHORT).show();
                }else {
                    dialog.dismiss();
                    User user = new User(name.getText().toString().trim(), MD5.md5(pass.getText().toString().trim()));
                    db_User.child(phone.getText().toString().trim()).setValue(user);
                    Toast.makeText(context, "Đăng ký thành công !", Toast.LENGTH_SHORT).show();
                    intent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
