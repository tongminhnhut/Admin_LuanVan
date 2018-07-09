package com.tongminhnhut.admin_luanvan;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.tongminhnhut.admin_luanvan.DAL.DongHoDAL;
import com.tongminhnhut.admin_luanvan.DAL.LoadMenuHomeDAL;
import com.tongminhnhut.admin_luanvan.Model.DongHo;

import info.hoang8f.widget.FButton;

public class AddDongHoActivity extends AppCompatActivity {
    EditText edtName, edtPrice, edtBrand, edtXuatxu, edtKichthuoc, edtBaohanh, edtMay, edtDaydeo, edtDis;
    FButton btnUpload, btnSelect , btnYes, btnNo;

    Uri saveUri;
    private final int PICK_IMAGE_REQUEST = 71;
    String id = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dong_ho);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.x = -30;
        params.height = 1500;
        params.width = 1075;
        params.y = -30;

        this.getWindow().setAttributes(params);

        initView();
        addEvent();
        id=getIntent().getStringExtra("ID");


    }



    private void addEvent() {
        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImage();
            }
        });
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialog progressDialog = new ProgressDialog(AddDongHoActivity.this);
//                DongHoDAL.upLoadImage(id, saveUri,getApplicationContext(),
//                        edtName,
//                        edtXuatxu,
//                        edtDis,
//                        edtPrice,
//                        edtBrand,
//                        edtBaohanh,
//                        edtDaydeo,
//                        edtMay,
//                        edtKichthuoc,
//                        progressDialog
//
//                );
            }
        });
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                DongHoDAL.addNew();
                finish();
                Toast.makeText(AddDongHoActivity.this, "Sản phẩm đã được thêm vào", Toast.LENGTH_LONG).show();
            }
        });
    }
    //show image
    private void showImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select picture"), PICK_IMAGE_REQUEST);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode ==RESULT_OK && data!=null && data.getData()!=null)
        {
            saveUri=data.getData();
            btnSelect.setText("Image Selected");
        }
    }


    private void initView() {
        btnNo = findViewById(R.id.btnNo_dialogAddDH);
        btnYes = findViewById(R.id.btnYes_dialogAddDH);
        btnSelect = findViewById(R.id.btnSlect_dialogAddDH);
        btnUpload = findViewById(R.id.btnUpload_dialogAddDH);
        edtName = findViewById(R.id.edtName_dialogAddDH);
        edtBaohanh = findViewById(R.id.edtBaoHanh_dialogAddDH);
        edtBrand = findViewById(R.id.edtBrand_dialogAddDH);
        edtDaydeo = findViewById(R.id.edtDaydeo_dialogAddDH);
        edtKichthuoc = findViewById(R.id.edtSize_dialogAddDH);
        edtPrice = findViewById(R.id.edtPrice_dialogAddDH);
        edtXuatxu = findViewById(R.id.edtXuatxu_dialogAddDH);
        edtMay = findViewById(R.id.edtMay_dialogAddDH);
        edtDis = findViewById(R.id.edtDiscount_dialogAddDH);
    }
}
