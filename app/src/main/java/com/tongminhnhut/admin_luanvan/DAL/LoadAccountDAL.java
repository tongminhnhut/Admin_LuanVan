package com.tongminhnhut.admin_luanvan.DAL;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tongminhnhut.admin_luanvan.AccountActivity;
import com.tongminhnhut.admin_luanvan.BLL.ConvertToStatus;
import com.tongminhnhut.admin_luanvan.Model.User;
import com.tongminhnhut.admin_luanvan.R;
import com.tongminhnhut.admin_luanvan.ViewHolder.AccountViewHolder;

public class LoadAccountDAL extends AccountActivity {
    public static DatabaseReference db_User = FirebaseDatabase.getInstance().getReference("User");
    public static FirebaseRecyclerAdapter<User, AccountViewHolder> adapter ;
    static AlertDialog.Builder dialog ;

    public static void loadAccount(final Context context,final RecyclerView recyclerView, final SwipeRefreshLayout swipeRefreshLayout){
        FirebaseRecyclerOptions<User> options = new FirebaseRecyclerOptions.Builder<User>()
                .setQuery(db_User, User.class)
                .build();
        adapter = new FirebaseRecyclerAdapter<User, AccountViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull AccountViewHolder holder, final int position, @NonNull User model) {
                holder.txtPhone.setText(String.format("Số điện thoại : %s",adapter.getRef(position).getKey()));
                holder.txtName.setText(String.format("Tên : %s ", model.getName()));
                holder.txtPhanquyen.setText(String.format("Cấp bậc : %s", ConvertToStatus.convertToAccount(Boolean.parseBoolean(model.getIsStaff()))));
                holder.btnPhanquyen.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.getItem(position).setIsStaff("true");
                        db_User.child(adapter.getRef(position).getKey()).setValue(adapter.getItem(position));
                        adapter.notifyDataSetChanged();
                    }
                });
                holder.btnXoa.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        db_User.child(adapter.getRef(position).getKey()).removeValue();
                        adapter.notifyDataSetChanged();
                    }
                });

            }

            @Override
            public AccountViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_account, parent, false);
                return new AccountViewHolder(view);
            }
        };

        adapter.startListening();
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
        swipeRefreshLayout.setRefreshing(false);
    }

}
