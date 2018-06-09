package com.tongminhnhut.admin_luanvan;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.tongminhnhut.admin_luanvan.BLL.CheckConnection;
import com.tongminhnhut.admin_luanvan.DAL.SignInDAL;

import dmax.dialog.SpotsDialog;
import info.hoang8f.widget.FButton;

public class SignInActivity extends AppCompatActivity {
    EditText edtPhone, edtPass ;
    FButton btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

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
                    SignInDAL.signIn(getApplicationContext(),phone, pass, dialog);
                } else {
                    Toast.makeText(SignInActivity.this, "Vui lòng kiểm tra kết nối !", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initView() {
        edtPhone = findViewById(R.id.edtPhonenumber_SignIp);
        edtPass = findViewById(R.id.edtPass_SignIp);
        btnSignIn = findViewById(R.id.btnSignIn_SignIn);
    }
}
