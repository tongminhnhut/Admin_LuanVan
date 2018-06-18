package com.tongminhnhut.admin_luanvan;

import android.accounts.Account;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.tongminhnhut.admin_luanvan.BLL.CheckConnection;
import com.tongminhnhut.admin_luanvan.BLL.Common;
import com.tongminhnhut.admin_luanvan.DAL.CategoryDAL;
import com.tongminhnhut.admin_luanvan.DAL.LoadDongHoDAL;
import com.tongminhnhut.admin_luanvan.DAL.LoadMenuHomeDAL;
import com.tongminhnhut.admin_luanvan.DAL.SignInDAL;
import com.tongminhnhut.admin_luanvan.Model.Category;
import com.tongminhnhut.admin_luanvan.Model.Token;


import info.hoang8f.widget.FButton;
import io.paperdb.Paper;

import static com.tongminhnhut.admin_luanvan.DAL.CategoryDAL.db_Category;


public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    TextView txtFullname ;
    FloatingActionButton btnAdd ;
    EditText edtName;
    FButton btnUp, btnSelect ;

    Uri saveUri;
    DrawerLayout drawer;
    private final int PICK_IMAGE_REQUEST = 71;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Administrator");
        setSupportActionBar(toolbar);

        Paper.init(this);
       

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


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
        btnAdd = findViewById(R.id.btnAdd_Home);


        addEvents();

//        Intent intent = new Intent(getApplicationContext(), ListenOrder.class);
//        startService(intent);

        updateToken(FirebaseInstanceId.getInstance().getToken());



    }

    private void updateToken(String token) {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference tokens = db.getReference("Tokens");
        Token data = new Token(token, true);
        tokens.child(SignInDAL.currentUser.getPhone()).setValue(data);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle().equals(Common.Update)){
            showUpDateDialog(LoadMenuHomeDAL.adapter.getRef(item.getOrder()).getKey(), LoadMenuHomeDAL.adapter.getItem(item.getOrder()));

        }else if (item.getTitle().equals(Common.Delete)){
            Query query = LoadDongHoDAL.db_DongHo.orderByChild("menuId").equalTo(LoadMenuHomeDAL.adapter.getRef(item.getOrder()).getKey());
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot postDataSnapshot:dataSnapshot.getChildren()){
                        postDataSnapshot.getRef().removeValue();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            db_Category.child(LoadMenuHomeDAL.adapter.getRef(item.getOrder()).getKey()).removeValue();
            Snackbar.make(drawer,"Danh mục "+CategoryDAL.category.getName()+ " đã xoá ", Snackbar.LENGTH_LONG ).show();

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
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(HomeActivity.this);
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
                CategoryDAL.upLoadImage(saveUri, getApplicationContext(), edtName, progressDialog);
            }
        });
        alertDialog.setView(view);
        alertDialog.setIcon(R.drawable.ic_library_add_black_24dp);

        //set button Yes/ No
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                CategoryDAL.addNew(drawer);

            }
        });

        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialog.show();

    }

    //update Dialog
    private void showUpDateDialog(final String key, final Category item) {
        AlertDialog.Builder mDialog = new AlertDialog.Builder(this);
        mDialog.setTitle("Cập nhật thông tin");
        mDialog.setMessage("Điền thông tin");

        LayoutInflater inflater = this.getLayoutInflater();
        View menu_layout = inflater.inflate(R.layout.dialog_add_menu, null);
        edtName = menu_layout.findViewById(R.id.edtName_dialogAdd);
        btnSelect = menu_layout.findViewById(R.id.btnSlect_dialogAdd);
        btnUp = menu_layout.findViewById(R.id.btnUpload_dialogAdd);

        // set Name default
        edtName.setText(item.getName());

        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImage();
            }
        });

        btnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progressDialog = new ProgressDialog(HomeActivity.this);
                CategoryDAL.changeImage(item, saveUri, getApplicationContext(), progressDialog);
            }
        });

        mDialog.setView(menu_layout);
        mDialog.setIcon(R.drawable.ic_shopping_cart_black_24dp);

        //set Button
        mDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

                // Cập nhật lại thông tin loại menu
                item.setName(edtName.getText().toString());
                db_Category.child(key).setValue(item);
            }
        });

        mDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


            }
        });

        mDialog.show();
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
            startActivity(new Intent(getApplicationContext(), StatusActivity.class));
        } else if (id == R.id.nav_QuanlyAccount) {
            startActivity(new Intent(getApplicationContext(), AccountActivity.class));
        } else if (id == R.id.nav_Logout) {
            Paper.book().destroy();
            Intent logoutItent = new Intent(getApplicationContext(), SignInActivity.class);
            logoutItent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(logoutItent);
            finish();

        }else if (id == R.id.nav_banner) {
            startActivity(new Intent(getApplicationContext(), BannerrActivity.class));

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
