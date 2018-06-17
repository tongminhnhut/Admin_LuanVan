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
import com.squareup.picasso.Picasso;
import com.tongminhnhut.admin_luanvan.Model.Banner;
import com.tongminhnhut.admin_luanvan.R;
import com.tongminhnhut.admin_luanvan.ViewHolder.BannerViewHolder;

public class LoadBannerDAL {
    public static DatabaseReference db_Banner = FirebaseDatabase.getInstance().getReference("Banner");
    public static FirebaseRecyclerAdapter<Banner, BannerViewHolder> adapter;

    public static void loadBanner(final Context context,final SwipeRefreshLayout swipeRefreshLayout, final RecyclerView recyclerView){
        FirebaseRecyclerOptions<Banner> options = new FirebaseRecyclerOptions.Builder<Banner>()
                .setQuery(db_Banner, Banner.class)
                .build();
        adapter = new FirebaseRecyclerAdapter<Banner, BannerViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull BannerViewHolder holder, int position, @NonNull Banner model) {
                holder.txtName.setText(model.getName());
                Picasso.with(context).load(model.getImage()).into(holder.imgBanner);
            }

            @Override
            public BannerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_banner, parent, false);
                return new BannerViewHolder(view);
            }
        };

        adapter.startListening();
        recyclerView.setAdapter(adapter);
        recyclerView.getAdapter().notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }
}
