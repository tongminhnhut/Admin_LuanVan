package com.tongminhnhut.admin_luanvan.BLL;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

import com.tongminhnhut.admin_luanvan.Model.RequestOrder;
import com.tongminhnhut.admin_luanvan.Remote.APIService;
import com.tongminhnhut.admin_luanvan.Remote.IGeoCoordinates;
import com.tongminhnhut.admin_luanvan.Remote.RetrofitClient;
import com.tongminhnhut.admin_luanvan.Remote.Retrofit_Direction;

public class Common {
    public static RequestOrder currentRe ;
    public static final String USER_KEY="User";
    public static final String PWD_KEY ="Password";
    public static final String Update = "Cập nhật thông tin";
    public static final String Delete = "Xoá sản phẩm";
    public static final String Update_Request = "Cập nhật trạng thái";
    public static final String Delete_request = "Xoá đơn hàng";


    public static final String baseUrl = "https://maps.googleapis.com/";
    public static final String BASE_URL = "https://fcm.googleapis.com/";

    public static IGeoCoordinates getGeoCodeService(){
        return Retrofit_Direction.getClient(baseUrl).create(IGeoCoordinates.class);
    }

    public static APIService getFCMClient(){
        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }

    public static Bitmap scaleBitmap (Bitmap bitmap, int newWith, int newHeight){
        Bitmap scaleBitmap = Bitmap.createBitmap(newWith, newHeight, Bitmap.Config.ARGB_8888);
        float scaleX = newWith/(float)bitmap.getWidth();
        float scaleY = newHeight/(float)bitmap.getHeight();
        float pivotX =0, pivotY=0;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(scaleX, scaleY,pivotX, pivotY);

        Canvas canvas = new Canvas(scaleBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bitmap, 0, 0, new Paint(Paint.FILTER_BITMAP_FLAG));

        return scaleBitmap;
    }
}
