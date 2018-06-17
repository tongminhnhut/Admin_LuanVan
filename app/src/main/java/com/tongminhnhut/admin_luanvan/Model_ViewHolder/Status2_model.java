package com.tongminhnhut.admin_luanvan.Model_ViewHolder;

import com.tongminhnhut.admin_luanvan.Model.Order;

import java.util.List;

public class Status2_model {
    private String Phone ;
    private String Name;
    private String Address;
    private String Total;
    private String Status;
    private List<Order> OrderList;

    public Status2_model() {
    }

    public Status2_model(String phone, String name, String address, String total, List<Order> orderList) {
        Phone = phone;
        Name = name;
        Address = address;
        Total = total;
        OrderList = orderList;
        Status ="0";
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getTotal() {
        return Total;
    }

    public void setTotal(String total) {
        Total = total;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public List<Order> getOrderList() {
        return OrderList;
    }

    public void setOrderList(List<Order> orderList) {
        OrderList = orderList;
    }
}
