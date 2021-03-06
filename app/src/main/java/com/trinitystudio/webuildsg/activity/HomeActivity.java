package com.trinitystudio.webuildsg.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.trinitystudio.core.util.DeviceUtil;
import com.trinitystudio.webuildsg.R;
import com.trinitystudio.webuildsg.adapter.HomeMenuPagerAdapter;

public class HomeActivity extends BaseActivity {

    private HomeMenuPagerAdapter homeMenuPagerAdapter;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        homeMenuPagerAdapter = new HomeMenuPagerAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(homeMenuPagerAdapter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initTabMenu();

        String appversion = String.format(getString(R.string.app_version), DeviceUtil.getAppVersion(this));
        Toast.makeText(this, appversion, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return false;
    }

    private void initTabMenu()
    {
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_main_menu);
        tabLayout.setupWithViewPager(viewPager);

        for(int i = 0; i < tabLayout.getTabCount(); ++i)
        {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(homeMenuPagerAdapter.getTabView(i));
        }

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                homeMenuPagerAdapter.setSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        homeMenuPagerAdapter.setSelected(0);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
