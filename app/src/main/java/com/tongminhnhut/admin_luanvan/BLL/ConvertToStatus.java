package com.tongminhnhut.admin_luanvan.BLL;

public class ConvertToStatus {
    //0: dang xu ly , 1: Da soan xong hang, 2: Don hang dang duoc giao
    public static String convertCodeStatus (String code){
        if (code.equals("0"))
            return "Đang xử lý" ;
        else
            return "Đơn hàng đang được giao đến khách hàng" ;
    }
}
