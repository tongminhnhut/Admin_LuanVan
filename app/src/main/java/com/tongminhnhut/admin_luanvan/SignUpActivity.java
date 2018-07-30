package com.tongminhnhut.admin_luanvan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tongminhnhut.admin_luanvan.BLL.MD5;
import com.tongminhnhut.admin_luanvan.DAL.SignUpDAL;
import com.tongminhnhut.admin_luanvan.Model.User;

import dmax.dialog.SpotsDialog;
import info.hoang8f.widget.FButton;

public class SignUpActivity extends AppCompatActivity {
    EditText edtPhone, edtName, edtPass;
    FButton btnSignUp ;
    DatabaseReference db_User ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        db_User = FirebaseDatabase.getInstance().getReference("User");

        initView();
        addEvents();
    }

    private void addEvents() {
        final String phone = edtPhone.getText().toString();
        final String pass = edtPass.getText().toString();
        final String name = edtName.getText().toString();
        final SpotsDialog dialog = new SpotsDialog(SignUpActivity.this, "Loading . . .");

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    SignUpDAL.signUp(intent,getApplicationContext(),
                            edtPhone,
                            edtPass,
                            edtName,
                            dialog);


//                finish();
            }
        });

    }

    private void initView() {
        edtName = findViewById(R.id.edtFullName_SignUp);
        edtPass = findViewById(R.id.edtPass_SignUp);
        edtPhone = findViewById(R.id.edtPhonenumber_SignUp);
        btnSignUp = findViewById(R.id.btnSignUp_SignUp);

    }
}
