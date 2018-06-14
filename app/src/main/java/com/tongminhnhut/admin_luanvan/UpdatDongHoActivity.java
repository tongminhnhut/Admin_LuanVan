package com.tongminhnhut.admin_luanvan;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.tongminhnhut.admin_luanvan.DAL.DongHoDAL;
import com.tongminhnhut.admin_luanvan.Model.DongHo;

import info.hoang8f.widget.FButton;

public class UpdatDongHoActivity extends AppCompatActivity {
    EditText edtName, edtPrice, edtBrand, edtXuatxu, edtKichthuoc, edtBaohanh, edtMay, edtDaydeo, edtDis;
    FButton btnUpload, btnSelect , btnYes, btnNo;

    Uri saveUri;
    //    RelativeLayout relativeLayout;
    private final int PICK_IMAGE_REQUEST = 71;
    String id = "";
    String key = "";
    DongHo dongHo ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updat_dong_ho);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.x = -30;
        params.height = 1500;
        params.width = 1075;
        params.y = -30;

        this.getWindow().setAttributes(params);

        initView();
        key = getIntent().getStringExtra("key");
        dongHo = (DongHo) getIntent().getSerializableExtra("dongho");
        update(key,dongHo);
    }
    private void update(final String key, final DongHo dongHo) {
        edtName.setText(dongHo.getName());
        edtBaohanh.setText(dongHo.getBaoHanh());
        edtBrand.setText(dongHo.getThuongHieu());
        edtDaydeo.setText(dongHo.getDayDeo());
        edtDis.setText(dongHo.getDiscount());
        edtPrice.setText(dongHo.getGia());
        edtKichthuoc.setText(dongHo.getSize());
        edtXuatxu.setText(dongHo.getXuatXu());

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dongHo.setName(edtName.getText().toString());
                dongHo.setBaoHanh(edtBaohanh.getText().toString());
                dongHo.setThuongHieu(edtBrand.getText().toString());
                dongHo.setDayDeo(edtDaydeo.getText().toString());
                dongHo.setDiscount(edtDis.getText().toString());
                dongHo.setGia(edtPrice.getText().toString());
                dongHo.setSize(edtKichthuoc.getText().toString());
                dongHo.setXuatXu(edtXuatxu.getText().toString());
                DongHoDAL.db_DongHo.child(key).setValue(dongHo);
                Toast.makeText(UpdatDongHoActivity.this, "Sản phẩm "+dongHo.getName()+" đã được chỉnh sửa", Toast.LENGTH_LONG).show();
                finish();
            }
        });

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImage();
            }
        });
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progressDialog = new ProgressDialog(UpdatDongHoActivity.this);
                DongHoDAL.changeImage(dongHo, saveUri, getApplicationContext(),progressDialog);
            }
        });


    }


    private void initView() {
        btnNo = findViewById(R.id.btnNo_dialogUpdateDH);
        btnYes = findViewById(R.id.btnYes_dialogUpdateDH);
        btnSelect = findViewById(R.id.btnSlect_dialogUpdateDH);
        btnUpload = findViewById(R.id.btnUpload_dialogUpdateDH);
        edtName = findViewById(R.id.edtName_dialogUpdateDH);
        edtBaohanh = findViewById(R.id.edtBaoHanh_dialogUpdateDH);
        edtBrand = findViewById(R.id.edtBrand_dialogUpdateDH);
        edtDaydeo = findViewById(R.id.edtDaydeo_dialogUpdateDH);
        edtKichthuoc = findViewById(R.id.edtSize_dialogUpdateDH);
        edtPrice = findViewById(R.id.edtPrice_dialogUpdateDH);
        edtXuatxu = findViewById(R.id.edtXuatxu_dialogUpdateDH);
        edtMay = findViewById(R.id.edtMay_dialogUpdateDH);
        edtDis = findViewById(R.id.edtDiscount_dialogUpdateDH);
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
}
