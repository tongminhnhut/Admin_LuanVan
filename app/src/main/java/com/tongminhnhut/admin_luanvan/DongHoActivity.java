package com.tongminhnhut.admin_luanvan;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.tongminhnhut.admin_luanvan.BLL.CheckConnection;
import com.tongminhnhut.admin_luanvan.BLL.Common;
import com.tongminhnhut.admin_luanvan.DAL.DongHoDAL;
import com.tongminhnhut.admin_luanvan.DAL.LoadDongHoDAL;
import com.tongminhnhut.admin_luanvan.Model.DongHo;

import java.util.ArrayList;

import info.hoang8f.widget.FButton;

public class DongHoActivity extends AppCompatActivity {
    SwipeRefreshLayout swipeRefreshLayout ;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    String menuId ="";
    FloatingActionButton btnAdd;
    EditText edtName, edtPrice, edtBrand, edtXuatxu, edtKichthuoc, edtBaohanh, edtMay, edtDaydeo, edtDis;
    FButton btnUpload, btnSelect , btnYes;

    Uri saveUri;
    RelativeLayout relativeLayout;
    private final int PICK_IMAGE_REQUEST = 71;
    AlertDialog.Builder alertDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dong_ho);

        swipeRefreshLayout = findViewById(R.id.swipeLayout_DongHo);
        recyclerView = findViewById(R.id.recyclerView_DongHo);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        initView();
        addEvent();




    }

    private void initView() {
        btnAdd = findViewById(R.id.btnAdd_DongHo);
        relativeLayout = findViewById(R.id.layout_DongHo);

    }

    private void addEvent() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogAddNew();
//                Intent intent = new Intent(getApplicationContext(), AddDongHoActivity.class);
//                intent.putExtra("ID", menuId);
//                startActivity(intent);
            }
        });
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (getIntent() !=null)
                    menuId = getIntent().getStringExtra("ID");
                if (!menuId.isEmpty() && menuId != null ){

                    if (CheckConnection.isConnectedInternet(getApplicationContext())){
//                    Intent intent = new Intent(getApplicationContext(), DongHoActivity.class);
                        LoadDongHoDAL.loadDongHo(menuId, getApplicationContext(), recyclerView,swipeRefreshLayout);
                    }else {
                        Toast.makeText(getApplicationContext(), "Vui lòng kiểm tra internet", Toast.LENGTH_SHORT).show();
                        return;
                    }

                }


            }
        });

        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                if (getIntent() !=null)
                    menuId = getIntent().getStringExtra("ID");
                if (!menuId.isEmpty() && menuId != null ){

                    if (CheckConnection.isConnectedInternet(getApplicationContext())){
//                    Intent intent = new Intent(getApplicationContext(), DongHoActivity.class);
                        LoadDongHoDAL.loadDongHo(menuId, getApplicationContext(), recyclerView,swipeRefreshLayout);
                    }else {
                        Toast.makeText(getApplicationContext(), "Vui lòng kiểm tra internet", Toast.LENGTH_SHORT).show();
                        return;
                    }

                }
            }
        });
    }

    //dialog them moi san pham
    private void showDialogAddNew() {
        alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Thêm sản phẩm mới");
        alertDialog.setMessage("Điền thông tin sản phẩm");
        alertDialog.setIcon(R.drawable.ic_library_add_black_24dp);

        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_dongho, null);

        alertDialog.setView(view);

        btnYes = view.findViewById(R.id.btnYes_dialogAddDH);
        btnSelect = view.findViewById(R.id.btnSlect_dialogAddDH);
        btnUpload = view.findViewById(R.id.btnUpload_dialogAddDH);
        edtName = view.findViewById(R.id.edtName_dialogAddDH);
        edtBaohanh = view.findViewById(R.id.edtBaoHanh_dialogAddDH);
        edtBrand = view.findViewById(R.id.edtBrand_dialogAddDH);
        edtDaydeo = view.findViewById(R.id.edtDaydeo_dialogAddDH);
        edtKichthuoc = view.findViewById(R.id.edtSize_dialogAddDH);
        edtPrice = view.findViewById(R.id.edtPrice_dialogAddDH);
        edtXuatxu = view.findViewById(R.id.edtXuatxu_dialogAddDH);
        edtMay = view.findViewById(R.id.edtMay_dialogAddDH);
        edtDis = view.findViewById(R.id.edtDiscount_dialogAddDH);

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialog progressDialog = new ProgressDialog(DongHoActivity.this);
                DongHoDAL.upLoadImage(menuId, saveUri,getApplicationContext(),
                        edtName,
                        edtPrice,
                        edtBrand,
                        edtXuatxu,
                        edtDis,
                        edtBaohanh,
                        edtKichthuoc,
                        edtDaydeo,
                        edtMay,
                        progressDialog
                        );
//                DongHoDAL.addNew(relativeLayout);
            }
        });

        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImage();
            }
        });


        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DongHoDAL.addNew(relativeLayout);
            }
        });
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alertDialog.create();
        alertDialog.show();





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


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle().equals(Common.Delete)){
            DongHoDAL.db_DongHo.child(LoadDongHoDAL.adapter.getRef(item.getOrder()).getKey()).removeValue();

        } else if (item.getTitle().equals(Common.Update)){
            showDialoUpdate(LoadDongHoDAL.adapter.getRef(item.getOrder()).getKey(), LoadDongHoDAL.adapter.getItem(item.getOrder()));
//            Intent intent = new Intent(getApplicationContext(), UpdatDongHoActivity.class);
//            intent.putExtra("key",LoadDongHoDAL.adapter.getRef(item.getOrder()).getKey());
//            intent.putExtra("dongho", LoadDongHoDAL.adapter.getItem(item.getOrder()) );
//            startActivity(intent);
        }
        return super.onContextItemSelected(item);
    }

    private void showDialoUpdate(final String key, final DongHo item) {
        alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Thêm sản phẩm mới");
        alertDialog.setMessage("Điền thông tin sản phẩm");
        alertDialog.setIcon(R.drawable.ic_library_add_black_24dp);

        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_dongho, null);

        alertDialog.setView(view);

        btnYes = view.findViewById(R.id.btnYes_dialogAddDH);
        btnSelect = view.findViewById(R.id.btnSlect_dialogAddDH);
        btnUpload = view.findViewById(R.id.btnUpload_dialogAddDH);
        edtName = view.findViewById(R.id.edtName_dialogAddDH);
        edtBaohanh = view.findViewById(R.id.edtBaoHanh_dialogAddDH);
        edtBrand = view.findViewById(R.id.edtBrand_dialogAddDH);
        edtDaydeo = view.findViewById(R.id.edtDaydeo_dialogAddDH);
        edtKichthuoc = view.findViewById(R.id.edtSize_dialogAddDH);
        edtPrice = view.findViewById(R.id.edtPrice_dialogAddDH);
        edtXuatxu = view.findViewById(R.id.edtXuatxu_dialogAddDH);
        edtMay = view.findViewById(R.id.edtMay_dialogAddDH);
        edtDis = view.findViewById(R.id.edtDiscount_dialogAddDH);

        edtDis.setText(item.getDiscount());
        edtMay.setText(item.getMay());
        edtXuatxu.setText(item.getXuatXu());
        edtPrice.setText(item.getGia());
        edtKichthuoc.setText(item.getSize());
        edtDaydeo.setText(item.getDayDeo());
        edtBrand.setText(item.getThuongHieu());
        edtName.setText(item.getName());
        edtBaohanh.setText(item.getBaoHanh());

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialog progressDialog = new ProgressDialog(DongHoActivity.this);
                DongHoDAL.changeImage(item,saveUri,getApplicationContext(), progressDialog);
            }
        });

        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImage();
            }
        });


        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                item.setDiscount(edtDis.getText().toString());
                item.setMay(edtMay.getText().toString());
                item.setXuatXu(edtXuatxu.getText().toString());
                item.setGia(edtPrice.getText().toString());
                item.setSize(edtKichthuoc.getText().toString());
                item.setDayDeo(edtDaydeo.getText().toString());
                item.setThuongHieu(edtBrand.getText().toString());
                item.setName(edtName.getText().toString());
                item.setBaoHanh(edtBaohanh.getText().toString());
                DongHoDAL.db_DongHo.child(key).setValue(item);
                Snackbar.make(relativeLayout,"Sản phẩm "+item.getName()+ " đã được chỉnh sửa ", Snackbar.LENGTH_LONG ).show();

            }
        });
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alertDialog.create();
        alertDialog.show();

    }
}
