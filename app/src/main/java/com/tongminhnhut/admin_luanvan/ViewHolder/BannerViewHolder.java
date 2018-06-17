package com.tongminhnhut.admin_luanvan.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tongminhnhut.admin_luanvan.R;

public class BannerViewHolder extends RecyclerView.ViewHolder {
    public ImageView imgBanner ;
    public TextView txtName ;

    public BannerViewHolder(View itemView) {
        super(itemView);
        imgBanner = itemView.findViewById(R.id.img_Banner);
        txtName = itemView.findViewById(R.id.txtName_Banner);
    }
}
