package com.tongminhnhut.admin_luanvan.DAL;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.tongminhnhut.admin_luanvan.BLL.Common;
import com.tongminhnhut.admin_luanvan.BLL.ConvertToStatus;
import com.tongminhnhut.admin_luanvan.BLL.ItemClickListener;
import com.tongminhnhut.admin_luanvan.Model.MyResponse;
import com.tongminhnhut.admin_luanvan.Model.Notification;
import com.tongminhnhut.admin_luanvan.Model.RequestOrder;
import com.tongminhnhut.admin_luanvan.Model.Sender;
import com.tongminhnhut.admin_luanvan.Model.Token;
import com.tongminhnhut.admin_luanvan.Model_ViewHolder.Status1_model;
import com.tongminhnhut.admin_luanvan.Model_ViewHolder.Status2_model;
import com.tongminhnhut.admin_luanvan.R;
import com.tongminhnhut.admin_luanvan.Remote.APIService;
import com.tongminhnhut.admin_luanvan.ViewHolder.OrderViewHolder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Status1 {
    public static DatabaseReference db_Request = FirebaseDatabase.getInstance().getReference("RequestOrder");
    public static FirebaseRecyclerAdapter<RequestOrder, OrderViewHolder> adapter;
    public static void loadOrder_CB(final APIService mService,final Context context, final Intent tracking, final Intent orderDetail, final RecyclerView recyclerView, final SwipeRefreshLayout swipeRefreshLayout){
        Query query = db_Request.orderByChild("status").equalTo("1");
        FirebaseRecyclerOptions<RequestOrder> options = new FirebaseRecyclerOptions.Builder<RequestOrder>()
                .setQuery(query, RequestOrder.class)
                .build();
        adapter = new FirebaseRecyclerAdapter<RequestOrder, OrderViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull OrderViewHolder holder, final int position, @NonNull final RequestOrder model) {
                holder.txtName.setText(model.getName());
                holder.txtId.setText(adapter.getRef(position).getKey());
                holder.txtAddress.setText(model.getAddress());
                holder.txtPhone.setText(model.getPhone());
                holder.txtStatus.setText(ConvertToStatus.convertCodeStatus(model.getStatus()));
                holder.btnDirection.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tracking.addFlags(tracking.FLAG_ACTIVITY_NEW_TASK);
                        Common.currentRe = model;
                        context.startActivity(tracking);
                    }
                });
                holder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        orderDetail.addFlags(orderDetail.FLAG_ACTIVITY_NEW_TASK);
                        Common.currentRe = model;
                        orderDetail.putExtra("orderdetail", adapter.getRef(position).getKey());
                        context.startActivity(orderDetail);
                    }
                });
                holder.btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.getItem(position).setStatus("1");
                        db_Request.child(adapter.getRef(position).getKey()).setValue(adapter.getItem(position));
                        sendOrderStatusToUser(adapter.getRef(position).getKey(),model,mService,context );
                        adapter.notifyDataSetChanged();


                    }
                });

                holder.btnxoa.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        db_Request.child(adapter.getRef(position).getKey()).removeValue();
                        adapter.notifyDataSetChanged();
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
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
        recyclerView.getAdapter().notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }

    public static void sendOrderStatusToUser(final String key, final RequestOrder item, final APIService serVice, final Context context) {
        DatabaseReference tokens = FirebaseDatabase.getInstance().getReference("Tokens");
        tokens.orderByKey().equalTo(item.getPhone())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot postSnapShot:dataSnapshot.getChildren()){
                            Token token = postSnapShot.getValue(Token.class);

                            Notification notification = new Notification("Watch Store", "don hang "+key+" da cap nhat");
                            Sender content = new Sender(token.getToken(), notification);
                            serVice.sendNotification(content)
                                    .enqueue(new Callback<MyResponse>() {
                                        @Override
                                        public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                                            if (response.body().success==1){
                                                Toast.makeText(context, "Order was update", Toast.LENGTH_SHORT).show();
                                            }else
                                                Toast.makeText(context, "Order was update but not send notification", Toast.LENGTH_SHORT).show();
                                        }

                                        @Override
                                        public void onFailure(Call<MyResponse> call, Throwable t) {
                                            Log.e("ERROR",t.getMessage());
                                        }
                                    });
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

}

