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
import com.squareup.picasso.Picasso;
import com.tongminhnhut.admin_luanvan.BLL.HomeActivity;
import com.tongminhnhut.admin_luanvan.BLL.ItemClickListener;
import com.tongminhnhut.admin_luanvan.Model.Category;
import com.tongminhnhut.admin_luanvan.R;
import com.tongminhnhut.admin_luanvan.ViewHolder.HomeViewHolder;

public class LoadMenuHomeDAL extends HomeActivity {
    static DatabaseReference db_Menu ;
    static FirebaseRecyclerAdapter<Category, HomeViewHolder> adapter ;
    public static void loadMenu(final Context context, final RecyclerView recyclerView, final SwipeRefreshLayout swipeRefreshLayout, final Intent intent){
        db_Menu = FirebaseDatabase.getInstance().getReference("Catergory");
        FirebaseRecyclerOptions<Category> options = new FirebaseRecyclerOptions.Builder<Category>()
                .setQuery(db_Menu, Category.class)
                .build();
        adapter = new FirebaseRecyclerAdapter<Category, HomeViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull HomeViewHolder holder, int position, @NonNull Category model) {
                holder.txtTen.setText(model.getName());
                Picasso.with(context).load(model.getImage())
                        .into(holder.imgHinh);
                holder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        intent.putExtra("ID", adapter.getRef(position).getKey());
                        intent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);

                    }
                });
            }

            @Override
            public HomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(context).inflate(R.layout.item_home, parent, false);
                return new HomeViewHolder(view);
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
