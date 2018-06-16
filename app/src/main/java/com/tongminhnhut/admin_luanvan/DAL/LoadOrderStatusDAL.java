package com.tongminhnhut.admin_luanvan.DAL;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.tongminhnhut.admin_luanvan.BLL.Common;
import com.tongminhnhut.admin_luanvan.BLL.ConvertToStatus;
import com.tongminhnhut.admin_luanvan.BLL.ItemClickListener;
import com.tongminhnhut.admin_luanvan.Model.RequestOrder;
import com.tongminhnhut.admin_luanvan.OrderStatusActivity;
import com.tongminhnhut.admin_luanvan.R;
import com.tongminhnhut.admin_luanvan.ViewHolder.OrderViewHolder;

public class LoadOrderStatusDAL extends OrderStatusActivity {
    public static RequestOrder currentRequest ;
    public static DatabaseReference db_Request = FirebaseDatabase.getInstance().getReference("RequestOrder");
    public static FirebaseRecyclerAdapter<RequestOrder, OrderViewHolder> adapter;
    public static void loadOrderStatus ( final Context context,final Intent tracking, final Intent orderDetail,final RecyclerView recyclerView, final SwipeRefreshLayout swipeRefreshLayout){
        FirebaseRecyclerOptions<RequestOrder> options = new FirebaseRecyclerOptions.Builder<RequestOrder>()
                .setQuery(db_Request, RequestOrder.class)
                .build();
        adapter = new FirebaseRecyclerAdapter<RequestOrder, OrderViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull OrderViewHolder holder, int position, @NonNull final RequestOrder model) {
                holder.txtName.setText(model.getName());
                holder.txtId.setText(adapter.getRef(position).getKey());
                holder.txtAddress.setText(model.getAddress());
                holder.txtPhone.setText(model.getPhone());
                holder.txtStatus.setText(ConvertToStatus.convertCodeStatus(model.getStatus()));
                holder.btnDirection.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tracking.addFlags(tracking.FLAG_ACTIVITY_NEW_TASK);
                        currentRequest = model;
                        context.startActivity(tracking);
                    }
                });
                holder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        orderDetail.addFlags(orderDetail.FLAG_ACTIVITY_NEW_TASK);
                        currentRequest = model;
                        orderDetail.putExtra("orderdetail", adapter.getRef(position).getKey());
                        context.startActivity(orderDetail);
                    }
                });
            }

            @Override
            public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_orderstatus, parent, false);
                return new OrderViewHolder(view);
            }
        };

        adapter.startListening();
        recyclerView.setAdapter(adapter);
        recyclerView.getAdapter().notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.startListening();
    }
}
