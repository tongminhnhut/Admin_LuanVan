package com.tongminhnhut.admin_luanvan.ViewHolder;

import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tongminhnhut.admin_luanvan.BLL.ItemClickListener;
import com.tongminhnhut.admin_luanvan.R;

public class DongHoViewholder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView txtTen;
    public ImageView imgHinh ;
    private ItemClickListener itemClickListener ;
    public DongHoViewholder(View itemView) {
        super(itemView);
        txtTen = itemView.findViewById(R.id.txtTenDongHo_itemDongHo);
        imgHinh = itemView.findViewById(R.id.imgDongHo_itemDongHo);

        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {

        itemClickListener.onClick(v, getAdapterPosition(), false);

    }
}
