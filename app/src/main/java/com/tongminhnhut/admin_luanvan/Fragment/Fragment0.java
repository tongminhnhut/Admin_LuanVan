package com.tongminhnhut.admin_luanvan.Fragment;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.tongminhnhut.admin_luanvan.BLL.Common;
import com.tongminhnhut.admin_luanvan.DAL.LoadOrderStatusDAL;

import com.tongminhnhut.admin_luanvan.OrderDetailActivity;
import com.tongminhnhut.admin_luanvan.R;
import com.tongminhnhut.admin_luanvan.Remote.APIService;
import com.tongminhnhut.admin_luanvan.TrackingOrderActivity;

public class Fragment0 extends Fragment {
    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;
    public SwipeRefreshLayout swipeRefreshLayout;
    public MaterialSpinner spinner;
    public APIService mService ;
    public Context context ;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public Fragment0() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mService = Common.getFCMClient();



    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_0, container, false);
        recyclerView = view.findViewById(R.id.recylerView_Fragment0);
        recyclerView.setHasFixedSize(true);
        swipeRefreshLayout = view.findViewById(R.id.swipeLayout_Fragment0);
        layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        Intent tracking = new Intent(context, TrackingOrderActivity.class);
        Intent order = new Intent(context, OrderDetailActivity.class);

        LoadOrderStatusDAL.loadOrderStatus(mService,context, tracking,order,recyclerView, swipeRefreshLayout);
        return view;
    }

}
