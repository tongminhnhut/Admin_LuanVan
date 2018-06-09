package com.tongminhnhut.admin_luanvan.DAL;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tongminhnhut.admin_luanvan.BLL.MD5;
import com.tongminhnhut.admin_luanvan.Model.User;

import dmax.dialog.SpotsDialog;

public class SignInDAL {
    public static User currentUser ;
    static DatabaseReference db_User ;

    public static void signIn (final Context context, final String phone, final String pass, final SpotsDialog dialog){
        db_User = FirebaseDatabase.getInstance().getReference("User");
        dialog.show();
        db_User.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(phone).exists()){
                    dialog.dismiss();
                    User user = dataSnapshot.child(phone).getValue(User.class);
                    user.setPhone(phone);
                    if (Boolean.parseBoolean(user.getIsStaff())){
                        if (user.getPassword().equals(MD5.md5(pass))){
                            Toast.makeText(context, "Đăng nhập thành công !", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(context, "Sai tên đăng nhập hoặc mật khẩu !", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(context, "Tài khoản này không phải là quản lý !", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    dialog.dismiss();
                    Toast.makeText(context, "Tài khoản không tồn tại !", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
