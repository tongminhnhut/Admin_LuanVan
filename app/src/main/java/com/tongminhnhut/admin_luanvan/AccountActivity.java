package com.tongminhnhut.admin_luanvan;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.tongminhnhut.admin_luanvan.BLL.CheckConnection;
import com.tongminhnhut.admin_luanvan.BLL.Common;
import com.tongminhnhut.admin_luanvan.DAL.LoadAccountDAL;
import com.tongminhnhut.admin_luanvan.DAL.LoadBannerDAL;
import com.tongminhnhut.admin_luanvan.Model.User;

import static com.tongminhnhut.admin_luanvan.DAL.LoadAccountDAL.db_User;

public class AccountActivity extends AppCompatActivity {
    RecyclerView recyclerView ;
    RecyclerView.LayoutManager layoutManager;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        inintView();
        addEvent();
    }

    private void addEvent() {
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if (CheckConnection.isConnectedInternet(getApplicationContext())){
                    Intent intent = new Intent(getApplicationContext(), AccountActivity.class);
                    LoadAccountDAL.loadAccount(intent,getApplicationContext(),recyclerView,swipeRefreshLayout);
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
                    Intent intent = new Intent(getApplicationContext(), AccountActivity.class);
                    LoadAccountDAL.loadAccount(intent,getApplicationContext(),recyclerView,swipeRefreshLayout);
                }else {
                    Toast.makeText(getApplicationContext(), "Vui lòng kiểm tra internet", Toast.LENGTH_SHORT).show();
                    return;
                }

            }
        });
    }

    private void inintView() {
        recyclerView = findViewById(R.id.recyclerView_Account);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        swipeRefreshLayout = findViewById(R.id.swipeLayout_Account);



    }
    private void showDialogEdit(final String key, final User item) {
        AlertDialog.Builder alertDialod = new AlertDialog.Builder(AccountActivity.this);
        alertDialod.setTitle("Cập nhật thông tin");
        alertDialod.setMessage("Điền thông tin");
        alertDialod.setIcon(R.drawable.ic_edit_black_24dp);

        final EditText edtName = new EditText(AccountActivity.this);
        edtName.setText(item.getName());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        edtName.setLayoutParams(lp);
        alertDialod.setView(edtName);

        alertDialod.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                db_User.child(key).setValue(edtName.getText().toString());
            }
        });
        alertDialod.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alertDialod.show();

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle().equals(Common.Update)){
            showDialogEdit(LoadAccountDAL.adapter.getRef(item.getOrder()).getKey(), LoadAccountDAL.adapter.getItem(item.getOrder()));
        }
        return super.onContextItemSelected(item);
    }
}
