package com.tongminhnhut.admin_luanvan;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.tongminhnhut.admin_luanvan.BLL.CheckConnection;
import com.tongminhnhut.admin_luanvan.BLL.Common;
import com.tongminhnhut.admin_luanvan.DAL.LoadMenuHomeDAL;
import com.tongminhnhut.admin_luanvan.DAL.LoadOrderStatusDAL;
import com.tongminhnhut.admin_luanvan.DAL.SignInDAL;
import com.tongminhnhut.admin_luanvan.Model.RequestOrder;

public class OrderStatusActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    SwipeRefreshLayout swipeRefreshLayout;
    MaterialSpinner spinner ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);

        recyclerView = findViewById(R.id.recyclerView_OrderStatus);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        swipeRefreshLayout= findViewById(R.id.swipeLayout_OrderStatus);

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
                if (CheckConnection.isConnectedInternet(OrderStatusActivity.this)){
                    Intent intent = new Intent(getApplicationContext(), TrackingOrderActivity.class);
                    LoadOrderStatusDAL.loadOrderStatus(getApplicationContext(), intent, recyclerView, swipeRefreshLayout);
                }else {
                    Toast.makeText(OrderStatusActivity.this, "Vui lòng kiểm tra internet", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });

        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                if (CheckConnection.isConnectedInternet(OrderStatusActivity.this)){
                    Intent intent = new Intent(getApplicationContext(), TrackingOrderActivity.class);
                    LoadOrderStatusDAL.loadOrderStatus(getApplicationContext(), intent,recyclerView, swipeRefreshLayout);
                }else {
                    Toast.makeText(OrderStatusActivity.this, "Vui lòng kiểm tra internet", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle().equals(Common.Delete_request)){
            deleteOrderRequest(LoadOrderStatusDAL.adapter.getRef(item.getOrder()).getKey());
        }else if (item.getTitle().equals(Common.Update_Request)){
            showUpdateDialog(LoadOrderStatusDAL.adapter.getRef(item.getOrder()).getKey(), LoadOrderStatusDAL.adapter.getItem(item.getOrder()));
        }
        return super.onContextItemSelected(item);
    }

    private void deleteOrderRequest(String key) {
        LoadOrderStatusDAL.db_Request.child(key).removeValue();
    }

    private void showUpdateDialog(final String key, final RequestOrder item) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Cập nhật");
        alertDialog.setMessage("Cập nhật trạng thái đơn hàng");
        alertDialog.setIcon(R.drawable.ic_access_time_black_24dp);

        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_update_orderstatus, null);

        spinner = view.findViewById(R.id.spinner_dialog);
        spinner.setItems("Đang xử lý","Đã chuẩn bị xong hàng","Đơn hàng đang được giao đến khách hàng");
        alertDialog.setView(view);

        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                item.setStatus(String.valueOf(spinner.getSelectedIndex()));
                LoadOrderStatusDAL.db_Request.child(key).setValue(item);
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
