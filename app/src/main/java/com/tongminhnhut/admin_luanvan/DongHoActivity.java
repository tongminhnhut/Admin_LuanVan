package com.tongminhnhut.admin_luanvan;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.tongminhnhut.admin_luanvan.BLL.CheckConnection;
import com.tongminhnhut.admin_luanvan.BLL.HomeActivity;
import com.tongminhnhut.admin_luanvan.DAL.LoadDongHoDAL;
import com.tongminhnhut.admin_luanvan.DAL.LoadMenuHomeDAL;

public class DongHoActivity extends AppCompatActivity {
    SwipeRefreshLayout swipeRefreshLayout ;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    String menuId ="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dong_ho);

        swipeRefreshLayout = findViewById(R.id.swipeLayout_DongHo);
        recyclerView = findViewById(R.id.recyclerView_DongHo);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

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

}
