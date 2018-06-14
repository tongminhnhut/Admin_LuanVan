package com.tongminhnhut.admin_luanvan.ViewHolder;


import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.View;
import android.widget.TextView;

import com.tongminhnhut.admin_luanvan.BLL.Common;
import com.tongminhnhut.admin_luanvan.BLL.ItemClickListener;
import com.tongminhnhut.admin_luanvan.R;

public class OrderViewHolder extends RecyclerView.ViewHolder implements
        View.OnClickListener,
        View.OnCreateContextMenuListener
{

    public TextView txtId, txtPhone, txtStatus, txtName, txtAddress ;
    private ItemClickListener itemClickListener;


    public OrderViewHolder(View itemView) {
        super(itemView);
        txtId = itemView.findViewById(R.id.txtId_orderStatus);
        txtPhone = itemView.findViewById(R.id.txtPhone_orderStatus);
        txtStatus = itemView.findViewById(R.id.txtStatus_orderStatus);
        txtName = itemView.findViewById(R.id.txtName_orderStatus);
        txtAddress = itemView.findViewById(R.id.txtAddress_orderStatus);

        itemView.setOnClickListener(this);
        itemView.setOnCreateContextMenuListener(this);

    }

    public void setItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener ;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(), false);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("Vui lòng chọn !");
        menu.add(0,0,getAdapterPosition(), Common.Update_Request);
        menu.add(0,1,getAdapterPosition(),Common.Delete_request);
    }
}
