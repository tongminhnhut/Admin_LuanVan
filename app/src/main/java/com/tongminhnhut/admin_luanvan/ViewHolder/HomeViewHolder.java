package com.tongminhnhut.admin_luanvan.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tongminhnhut.admin_luanvan.BLL.ItemClickListener;
import com.tongminhnhut.admin_luanvan.R;

public class HomeViewHolder extends RecyclerView.ViewHolder implements
        View.OnClickListener,
        View.OnCreateContextMenuListener
{
    public TextView txtTen ;
    public ImageView imgHinh ;
    private ItemClickListener itemClickListener ;

    public HomeViewHolder(View itemView) {
        super(itemView);

        txtTen = itemView.findViewById(R.id.txtTenDongHo_itemHome);
        imgHinh = itemView.findViewById(R.id.imgDongHo_itemHome);

        itemView.setOnClickListener(this);

    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(), false);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("Vui lòng chọn !");
        menu.add(0,0,getAdapterPosition(),"Cập nhật thông tin");
        menu.add(0,1,getAdapterPosition(),"Xoá sản phẩm");

    }
}
