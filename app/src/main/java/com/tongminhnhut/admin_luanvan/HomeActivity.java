package com.tongminhnhut.admin_luanvan;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.tongminhnhut.admin_luanvan.BLL.CheckConnection;
import com.tongminhnhut.admin_luanvan.DAL.LoadImageDAL;
import com.tongminhnhut.admin_luanvan.DAL.LoadMenuHomeDAL;
import com.tongminhnhut.admin_luanvan.DAL.SignInDAL;
import com.tongminhnhut.admin_luanvan.Model.Category;


import info.hoang8f.widget.FButton;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    TextView txtFullname ;
    FloatingActionButton btnAdd ;

    EditText edtName;
    FButton btnUp, btnSelect ;

    Category newCategory ;
    Uri saveUri;
    private final int PICK_IMAGE_REQUEST = 71;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Administrator");
        setSupportActionBar(toolbar);

       

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //init View
        swipeRefreshLayout = findViewById(R.id.swipeLayout_Home);
        txtFullname = findViewById(R.id.txtFullName);
        recyclerView = findViewById(R.id.recyclerView_Home);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //set name for user
        View headerView = navigationView.getHeaderView(0);
        txtFullname = headerView.findViewById(R.id.txtFullName);
        txtFullname.setText(SignInDAL.currentUser.getName());
        btnAdd = findViewById(R.id.btnAdd_DongHo);


        addEvents();




    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle().equals("Delete")){
//            new Database(this).removeItemCart(listOrder.get());
        }
        return super.onContextItemSelected(item);
    }

    private void addEvents() {
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (CheckConnection.isConnectedInternet(HomeActivity.this)){
                    Intent intent = new Intent(getApplicationContext(), DongHoActivity.class);
                    LoadMenuHomeDAL.loadMenu(getApplicationContext(),recyclerView, swipeRefreshLayout, intent);
                }else {
                    Toast.makeText(HomeActivity.this, "Vui lòng kiểm tra internet", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });

        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                if (CheckConnection.isConnectedInternet(HomeActivity.this)){
                    Intent intent = new Intent(getApplicationContext(), DongHoActivity.class);
                    LoadMenuHomeDAL.loadMenu(getApplicationContext(),recyclerView, swipeRefreshLayout, intent);

                }else {
                    Toast.makeText(HomeActivity.this, "Vui lòng kiểm tra internet", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogAdd();
            }
        });

    }

    private void showImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select picture"), PICK_IMAGE_REQUEST);

    }


    private void showDialogAdd() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(HomeActivity.this);
        alertDialog.setTitle("Thêm mới");
        alertDialog.setMessage("Điền thông tin");

        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_menu, null);

        edtName = view.findViewById(R.id.edtName_dialogAdd);
        btnSelect = view.findViewById(R.id.btnSlect_dialogAdd);
        btnUp = view.findViewById(R.id.btnUpload_dialogAdd);

        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImage();
            }
        });

        btnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialog progressDialog = new ProgressDialog(HomeActivity.this);
                LoadImageDAL.upLoadImage(saveUri, getApplicationContext(), edtName, progressDialog);
            }
        });
        alertDialog.setView(view);
        alertDialog.setIcon(R.drawable.ic_shopping_cart_black_24dp);

        //set button Yes/ No
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialog.show();

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
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_order) {
            // Handle the camera action
        } else if (id == R.id.nav_Quanly) {

        } else if (id == R.id.nav_Logout) {

        }else if (id == R.id.nav_banner) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
