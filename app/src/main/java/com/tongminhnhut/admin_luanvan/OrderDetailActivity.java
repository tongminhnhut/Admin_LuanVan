package com.tongminhnhut.admin_luanvan;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.tongminhnhut.admin_luanvan.BLL.ConvertToStatus;
import com.tongminhnhut.admin_luanvan.DAL.LoadOrderStatusDAL;
import com.tongminhnhut.admin_luanvan.ViewHolder.OrderDetailAdapter;

public class OrderDetailActivity extends AppCompatActivity {
    TextView txtID, txtName, txtPhone, txtAddress, txtStatus;
    String id_Order = "";
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        initView();
        if (getIntent() != null)
            id_Order = getIntent().getStringExtra("orderdetail");
        addEvent();

    }

    private void addEvent() {
        txtID.setText(id_Order);
        txtName.setText(LoadOrderStatusDAL.currentRequest.getName());
        txtPhone.setText(LoadOrderStatusDAL.currentRequest.getPhone());
        txtAddress.setText(LoadOrderStatusDAL.currentRequest.getAddress());
        txtStatus.setText(ConvertToStatus.convertCodeStatus(LoadOrderStatusDAL.currentRequest.getName()));

        OrderDetailAdapter adapter = new OrderDetailAdapter(LoadOrderStatusDAL.currentRequest.getOrderList(), this);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

    }

    private void initView() {
        txtID = findViewById(R.id.txtId_orderDetail);
        txtName = findViewById(R.id.txtName_orderDetail);
        txtPhone = findViewById(R.id.txtPhone_orderDetail);
        txtAddress = findViewById(R.id.txtAddress_orderDetail);
        txtStatus = findViewById(R.id.txtStatus_orderDetail);
        recyclerView = findViewById(R.id.recyclerView_OrderDetail);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

    }
}
