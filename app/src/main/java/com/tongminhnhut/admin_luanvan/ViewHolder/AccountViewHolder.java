package com.tongminhnhut.admin_luanvan.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.View;
import android.widget.TextView;

import com.tongminhnhut.admin_luanvan.BLL.Common;
import com.tongminhnhut.admin_luanvan.R;

import info.hoang8f.widget.FButton;

public class AccountViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener  {
    public TextView txtPhone, txtName, txtPhanquyen ;
    public FButton  btnXoa ;
    public AccountViewHolder(View itemView) {
        super(itemView);

        txtPhone =itemView.findViewById(R.id.txtPhone_itemAccount);
        txtName = itemView.findViewById(R.id.txtTen_itemAccount);
        txtPhanquyen = itemView.findViewById(R.id.txtPhanquyen_itemAccount);
        btnXoa = itemView.findViewById(R.id.btnDelete_itemAccount);

        itemView.setOnCreateContextMenuListener(this
        );

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("Vui lòng chọn !");
        menu.add(0,0,getAdapterPosition(), Common.Update);
//        menu.add(0,1,getAdapterPosition(),Common.Delete);

    }
}
