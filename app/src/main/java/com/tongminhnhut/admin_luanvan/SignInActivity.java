package com.tongminhnhut.admin_luanvan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.rey.material.widget.CheckBox;
import com.tongminhnhut.admin_luanvan.BLL.CheckConnection;
import com.tongminhnhut.admin_luanvan.BLL.Common;
import com.tongminhnhut.admin_luanvan.DAL.SignInDAL;

import dmax.dialog.SpotsDialog;
import info.hoang8f.widget.FButton;
import io.paperdb.Paper;

public class SignInActivity extends AppCompatActivity {
    EditText edtPhone, edtPass ;
    FButton btnSignIn;
    CheckBox cb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);


        Paper.init(this);
        initView();
        addEvents();
    }

    private void addEvents() {
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = edtPhone.getText().toString();
                String pass = edtPass.getText().toString();
                final SpotsDialog dialog = new SpotsDialog(SignInActivity.this, "Loading . . .");
                if (CheckConnection.isConnectedInternet(getApplicationContext())){
                    if (phone.isEmpty() || pass.isEmpty()){
                        Toast.makeText(SignInActivity.this, "Vui lòng nhập đầy đủ", Toast.LENGTH_SHORT).show();
                    }else {
                        if (cb.isChecked()){
                            Paper.book().write(Common.USER_KEY,edtPhone.getText().toString());
                            Paper.book().write(Common.PWD_KEY, edtPass.getText().toString());
                        }
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        SignInDAL.signIn(getApplicationContext(),phone, pass, dialog, intent);
                    }
                    
                } else {
                    Toast.makeText(SignInActivity.this, "Vui lòng kiểm tra kết nối !", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initView() {
        cb = findViewById(R.id.cbRemember);
        edtPhone = findViewById(R.id.edtPhonenumber_SignIp);
        edtPass = findViewById(R.id.edtPass_SignIp);
        btnSignIn = findViewById(R.id.btnSignIn_SignIn);
    }
}
