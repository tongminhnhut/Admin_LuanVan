package com.tongminhnhut.admin_luanvan.DAL;

import android.content.Context;
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
import com.squareup.picasso.Picasso;
import com.tongminhnhut.admin_luanvan.BLL.ItemClickListener;
import com.tongminhnhut.admin_luanvan.Model.DongHo;
import com.tongminhnhut.admin_luanvan.R;
import com.tongminhnhut.admin_luanvan.ViewHolder.DongHoViewholder;

public class LoadDongHoDAL {
    public static DatabaseReference db_DongHo ;
    public static FirebaseRecyclerAdapter<DongHo, DongHoViewholder> adapter;
    public static void loadDongHo (final String ID, final Context context, final RecyclerView recyclerView, final SwipeRefreshLayout swipeRefreshLayout){
        db_DongHo = FirebaseDatabase.getInstance().getReference("DongHo");
        Query query = db_DongHo.orderByChild("menuId").equalTo(ID) ;
        FirebaseRecyclerOptions<DongHo> options = new FirebaseRecyclerOptions.Builder<DongHo>()
                .setQuery(query, DongHo.class)
                .build();

        adapter = new FirebaseRecyclerAdapter<DongHo, DongHoViewholder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull DongHoViewholder holder, int position, @NonNull DongHo model) {
                holder.txtTen.setText(model.getName());
                Picasso.with(context).load(model.getImage())
                        .into(holder.imgHinh);
                holder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {

                    }
                });
            }

            @Override
            public DongHoViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(context).inflate(R.layout.item_dongho,parent, false);

                return new DongHoViewholder(view);
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);
        recyclerView.getAdapter().notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }
}
