package com.tongminhnhut.admin_luanvan;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.tongminhnhut.admin_luanvan.BLL.CheckConnection;
import com.tongminhnhut.admin_luanvan.DAL.LoadBannerDAL;
import com.tongminhnhut.admin_luanvan.DAL.LoadDongHoDAL;

public class BannerrActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bannerr);

        initView();
        addEvents();
    }

    private void addEvents() {
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

    private void initView() {
        swipeRefreshLayout = findViewById(R.id.swipeLayout_Banner);
        recyclerView = findViewById(R.id.recyclerView_Banner);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }
}
