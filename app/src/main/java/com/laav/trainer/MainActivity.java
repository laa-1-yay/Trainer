package com.laav.trainer;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.laav.trainer.selection.SelectionFragment;
import com.laav.trainer.ui.SlidingTabLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager peoplePager;
        FragmentPagerAdapter fragmentPagerAdapter;
        ViewPagerAdapter adapter;
        SlidingTabLayout tabs;

        peoplePager = (ViewPager) findViewById(R.id.view_pager);
        adapter  =  new ViewPagerAdapter(getSupportFragmentManager());
        peoplePager.setAdapter(adapter);
        peoplePager.setOffscreenPageLimit(2);
        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true);
        tabs.setBackgroundColor(getResources().getColor(R.color.yellow));
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.gray);
            }
        });
        tabs.setViewPager(peoplePager);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class ViewPagerAdapter extends FragmentStatePagerAdapter {

        CharSequence TAB_TITLES[]={"Videos", "Updates"};
        int NUM_TAB =2;

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {


            switch (position) {
                case 0:
                    return (new SelectionFragment());
                case 1:
                    return (new SelectionFragment());

            }
            return (new SelectionFragment());

        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TAB_TITLES[position];
        }

        @Override
        public int getCount() {
            return NUM_TAB;
        }
    }


}
