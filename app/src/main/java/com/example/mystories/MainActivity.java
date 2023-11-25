package com.example.mystories;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;



public class MainActivity extends AppCompatActivity implements ActionTransfer{
    private ViewPager viewPager;
    private AHBottomNavigation ahBottomNavigation;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkPermission();
        initView();
        setAhBottomNavigation();
        setUpViewPager();
    }
    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                    100);
        }

    }
    public void initView(){
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        ahBottomNavigation = findViewById(R.id.navigation);
    }
    public void setUpViewPager(){  // my function
        MyViewPagerAdapter viewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        ahBottomNavigation.setCurrentItem(0);
                        break;
                    case 1:
                        ahBottomNavigation.setCurrentItem(1);
                        break;
                    case 2:
                        ahBottomNavigation.setCurrentItem(2);
                        break;
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {}
        });
    }
    public void setAhBottomNavigation(){

        AHBottomNavigationItem item1 = new AHBottomNavigationItem("Home", R.drawable.home, R.color.white);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem("Camera", R.drawable.camera, R.color.white);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem("Setting", R.drawable.setting, R.color.white);

        ahBottomNavigation.addItem(item1);
        ahBottomNavigation.addItem(item2);
        ahBottomNavigation.addItem(item3);

        // Set background color
        ahBottomNavigation.setColored(false);
        ahBottomNavigation.setDefaultBackgroundColor(Color.parseColor("#2c3e50"));
        // Change colors
        ahBottomNavigation.setAccentColor(Color.parseColor("#FFFFFF"));
        ahBottomNavigation.setInactiveColor(Color.parseColor("#747474"));

        ahBottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                switch (position){
                    case 0:
                        viewPager.setCurrentItem(0);
                        break;
                    case 1:
                        viewPager.setCurrentItem(1);
                        break;
                    case 2:
                        viewPager.setCurrentItem(2);
                        break;
                    default:
                        viewPager.setCurrentItem(0);
                }
                return true;
            }
        });
    }


    @Override
    public void goHomeAction() {
        viewPager.setCurrentItem(0);
    }


}