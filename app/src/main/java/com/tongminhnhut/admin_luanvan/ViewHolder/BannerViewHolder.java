package com.tongminhnhut.admin_luanvan.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tongminhnhut.admin_luanvan.BLL.Common;
import com.tongminhnhut.admin_luanvan.R;

public class BannerViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
    public ImageView imgBanner ;
    public TextView txtName ;

    public BannerViewHolder(View itemView) {
        super(itemView);
        imgBanner = itemView.findViewById(R.id.img_Banner);
        txtName = itemView.findViewById(R.id.txtName_Banner);

        itemView.setOnCreateContextMenuListener(this);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("Vui lòng chọn !");
        menu.add(0,0,getAdapterPosition(), Common.Update);
        menu.add(0,1,getAdapterPosition(),Common.Delete);
    }
}
