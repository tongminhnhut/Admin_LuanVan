package com.tongminhnhut.admin_luanvan;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import com.tongminhnhut.admin_luanvan.DAL.BannerDAL;
import com.tongminhnhut.admin_luanvan.DAL.LoadBannerDAL;
import com.tongminhnhut.admin_luanvan.DAL.LoadDongHoDAL;
import com.tongminhnhut.admin_luanvan.Model.Banner;

import info.hoang8f.widget.FButton;

public class BannerrActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    SwipeRefreshLayout swipeRefreshLayout;
    FloatingActionButton btnAdd ;
    FButton btnSelect, btnUpload;
    EditText edtId, edtName;

    Uri saveUri;
    RelativeLayout relativeLayout;
    private final int PICK_IMAGE_REQUEST = 71;
    ProgressDialog progressDialog ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bannerr);

        relativeLayout = findViewById(R.id.layout_Banner);

        initView();
        addEvents();
    }

    private void addEvents() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogAddBanner();
            }
        });
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                    if (CheckConnection.isConnectedInternet(getApplicationContext())){
//                    Intent intent = new Intent(getApplicationContext(), DongHoActivity.class);
                        LoadBannerDAL.loadBanner(getApplicationContext(), swipeRefreshLayout,recyclerView);
                    }else {
                        Toast.makeText(getApplicationContext(), "Vui lòng kiểm tra internet", Toast.LENGTH_SHORT).show();
                        return;
                    }

                }

        });

        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {

                if (CheckConnection.isConnectedInternet(getApplicationContext())){
//                    Intent intent = new Intent(getApplicationContext(), DongHoActivity.class);
                    LoadBannerDAL.loadBanner(getApplicationContext(), swipeRefreshLayout,recyclerView);

                }else {
                    Toast.makeText(getApplicationContext(), "Vui lòng kiểm tra internet", Toast.LENGTH_SHORT).show();
                    return;
                }

            }
        });
    }

    private void showDialogAddBanner() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Thêm banner mới");
        alertDialog.setMessage("Điền thông tin");
        alertDialog.setIcon(R.drawable.ic_library_add_black_24dp);

        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_banner, null);

        alertDialog.setView(view);


        edtId = view.findViewById(R.id.edtId_dialogAdd_Banner);
        edtName = view.findViewById(R.id.edtName_dialogAdd_Banner);
        btnSelect = view.findViewById(R.id.btnSlect_dialogAdd_Banner);
        btnUpload = view.findViewById(R.id.btnUpload_dialogAdd_Banner);

        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImage();
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog =  new ProgressDialog(BannerrActivity.this);
                BannerDAL.upLoadImage(saveUri, getApplicationContext(),edtName, edtId, progressDialog);
            }
        });

        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                BannerDAL.addNew(relativeLayout);
            }
        });
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

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

    private void initView() {
        btnAdd = findViewById(R.id.btnAdd_Banner);
        swipeRefreshLayout = findViewById(R.id.swipeLayout_Banner);
        recyclerView = findViewById(R.id.recyclerView_Banner);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle().equals(Common.Update)) {
                showDialogUpdate(LoadBannerDAL.adapter.getRef(item.getOrder()).getKey(), LoadBannerDAL.adapter.getItem(item.getOrder()));
        } else if (item.getTitle().equals(Common.Delete)){
                BannerDAL.db_Category.child(LoadBannerDAL.adapter.getRef(item.getOrder()).getKey()).removeValue();
        }
        return super.onContextItemSelected(item);
    }

    private void showDialogUpdate(final String key, final Banner item) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Cập nhật thông tin");
        alertDialog.setMessage("Điền thông tin");
        alertDialog.setIcon(R.drawable.ic_edit_black_24dp);

        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_banner, null);
        alertDialog.setView(view);


        edtId = view.findViewById(R.id.edtId_dialogAdd_Banner);
        edtName = view.findViewById(R.id.edtName_dialogAdd_Banner);
        btnSelect = view.findViewById(R.id.btnSlect_dialogAdd_Banner);
        btnUpload = view.findViewById(R.id.btnUpload_dialogAdd_Banner);

        edtId.setText(item.getID());
        edtName.setText(item.getName());

        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImage();
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog =  new ProgressDialog(BannerrActivity.this);
                BannerDAL.changeImage(item,saveUri,getApplicationContext(), progressDialog);
            }
        });

        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                item.setID(edtId.getText().toString());
                item.setName(edtName.getText().toString());
                BannerDAL.db_Category.child(key).setValue(item);
                Snackbar.make(relativeLayout,"Banner "+item.getName()+ " đã được chỉnh sửa ", Snackbar.LENGTH_LONG ).show();


            }
        });
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alertDialog.show();




    }
}
