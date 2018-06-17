package com.tongminhnhut.admin_luanvan;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.tongminhnhut.admin_luanvan.BLL.Common;
import com.tongminhnhut.admin_luanvan.Fragment.Fragment0;
import com.tongminhnhut.admin_luanvan.Fragment.Fragment1;
import com.tongminhnhut.admin_luanvan.Fragment.ViewPageAdapter;
import com.tongminhnhut.admin_luanvan.Remote.APIService;

public class StatusActivity extends AppCompatActivity {

    MaterialSpinner spinner;
    TabLayout tabLayout ;
    ViewPager viewPager;
    ViewPageAdapter adapter ;
    APIService mService ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        tabLayout = findViewById(R.id.tabLayout_Status);
        viewPager =findViewById(R.id.viewPage);
        adapter = new ViewPageAdapter(getSupportFragmentManager());

        mService = Common.getFCMClient();
        //add Fragment
        adapter.AddFragment(new Fragment0(),"Đơn hàng mới");
        adapter.AddFragment(new Fragment1(),"Đã hoàn thành");
//        adapter.AddFragment(new Fragment2(),"Giao đến khách");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_archive_black_24dp);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_access_time_black_24dp);
//        tabLayout.getTabAt(2).setIcon(R.drawable.ic_notifications_black_24dp);

    }



}
