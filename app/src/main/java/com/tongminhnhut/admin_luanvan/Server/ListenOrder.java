package com.tongminhnhut.admin_luanvan.Server;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tongminhnhut.admin_luanvan.Model.RequestOrder;
import com.tongminhnhut.admin_luanvan.OrderStatusActivity;
import com.tongminhnhut.admin_luanvan.R;

import java.util.Random;

public class ListenOrder extends Service implements ChildEventListener {

    DatabaseReference db_Request ;

    public ListenOrder() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return  null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        db_Request= FirebaseDatabase.getInstance().getReference("RequestOrder");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        db_Request.addChildEventListener(this);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        RequestOrder requestOrder = dataSnapshot.getValue(RequestOrder.class);
        if (requestOrder.getStatus().equals("0")){
            showNotification(dataSnapshot.getKey(), requestOrder);

        }
    }

    private void showNotification(String key, RequestOrder requestOrder) {
        Intent intent = new Intent(getApplicationContext(), OrderStatusActivity.class);
        intent.putExtra("userPhone", requestOrder.getPhone());
        PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent,0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
        builder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setTicker("Watch Store")
                .setContentInfo("Đã có đơn hàng mới")
                .setContentText("Đơn hàng mới #"+key)
                .setContentIntent(contentIntent)
                .setContentInfo("Info")
                .setSmallIcon(R.mipmap.ic_launcher);

        NotificationManager notificationManager = (NotificationManager) getBaseContext().getSystemService(Context.NOTIFICATION_SERVICE);
        int random = new Random().nextInt(999-1)+1;
        notificationManager.notify(random, builder.build());
    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {

    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
