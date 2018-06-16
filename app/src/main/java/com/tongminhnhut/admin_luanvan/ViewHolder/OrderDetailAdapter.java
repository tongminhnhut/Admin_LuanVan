package com.tongminhnhut.admin_luanvan.ViewHolder;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tongminhnhut.admin_luanvan.Model.Order;
import com.tongminhnhut.admin_luanvan.R;

import java.util.List;

class MyViewHolder extends RecyclerView.ViewHolder {
    public TextView txtName, txtPrice, txtSoluong, txtDis;
    public ImageView img;

    public MyViewHolder(View itemView) {
        super(itemView);
        txtName = itemView.findViewById(R.id.txtName_itemOrderDetail);
        txtPrice = itemView.findViewById(R.id.txtPrice_itemOrderDetail);
        txtSoluong = itemView.findViewById(R.id.txtSoluong_itemOrderDetail);
        txtDis = itemView.findViewById(R.id.txtDiscount_itemOrderDetail);
        img = itemView.findViewById(R.id.imgDetail_itemOrderDetail);
    }
}

public class OrderDetailAdapter extends RecyclerView.Adapter<MyViewHolder> {
    List<Order> orderList ;
    Context context ;

    public OrderDetailAdapter(List<Order> orderList, Context context) {
        this.orderList = orderList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_orderdetail, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Order order = orderList.get(position);
        holder.txtName.setText(String.format("TÊN ĐỒNG HỒ: %s",order.getProductName()));
        holder.txtSoluong.setText(String.format("SỐ LƯỢNG: %s", order.getQuantity()));
        holder.txtDis.setText(String.format("GIẢM GIÁ: %s", order.getDiscount()));
        holder.txtPrice.setText(String.format("ĐƠN GIÁ: %s", order.getPrice()));
        Picasso.with(context).load(order.getImage()).into(holder.img);

    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }
}
