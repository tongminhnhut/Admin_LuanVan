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
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.tongminhnhut.admin_luanvan.BLL.Common;
import com.tongminhnhut.admin_luanvan.DAL.LoadOrderStatusDAL;
import com.tongminhnhut.admin_luanvan.DAL.Status1;
import com.tongminhnhut.admin_luanvan.DAL.Status2;
import com.tongminhnhut.admin_luanvan.Model.MyResponse;
import com.tongminhnhut.admin_luanvan.Model.Notification;
import com.tongminhnhut.admin_luanvan.Model.RequestOrder;
import com.tongminhnhut.admin_luanvan.Model.Sender;
import com.tongminhnhut.admin_luanvan.Model.Token;
import com.tongminhnhut.admin_luanvan.Model_ViewHolder.Status1_model;
import com.tongminhnhut.admin_luanvan.OrderDetailActivity;
import com.tongminhnhut.admin_luanvan.R;
import com.tongminhnhut.admin_luanvan.Remote.APIService;
import com.tongminhnhut.admin_luanvan.TrackingOrderActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment1 extends Fragment {
    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;
    public SwipeRefreshLayout swipeRefreshLayout;
    public Context context ;
    public MaterialSpinner spinner;
    public APIService mService ;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }
    public Fragment1() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_1, container, false);
        recyclerView = view.findViewById(R.id.recycler_Fragment1);
        recyclerView.setHasFixedSize(true);
        swipeRefreshLayout = view.findViewById(R.id.swipeLayout_Fragment1);
        layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        Intent tracking = new Intent(context, TrackingOrderActivity.class);
        Intent order = new Intent(context, OrderDetailActivity.class);
        mService = Common.getFCMClient();
        Status1.loadOrder_CB(mService,context, tracking,order,recyclerView, swipeRefreshLayout);

        return view;
    }

}
