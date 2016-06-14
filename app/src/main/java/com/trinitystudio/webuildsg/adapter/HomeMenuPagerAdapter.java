package com.trinitystudio.webuildsg.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.trinitystudio.webuildsg.R;
import com.trinitystudio.webuildsg.fragment.BaseFragment;
import com.trinitystudio.webuildsg.fragment.RepoFragment;
import com.trinitystudio.webuildsg.fragment.EventFragment;

import java.util.ArrayList;

/**
 * Created by liccowee on 10/28/15.
 */
public class HomeMenuPagerAdapter extends FragmentPagerAdapter
{
    private Context context;
    public ArrayList<BaseFragment> homeTabFragments;
    private ArrayList<View> tabViews;
    private ArrayList<Integer> pageIcons;
    private ArrayList<Integer> pageSelectedIcons;
    private ArrayList<String> pageNames;

    public HomeMenuPagerAdapter(Context context, FragmentManager fm) {
        super(fm);

        this.context = context;
        homeTabFragments = new ArrayList<>();
        tabViews = new ArrayList<>();
        pageNames = new ArrayList<>();
        pageIcons = new ArrayList<>();
        pageSelectedIcons = new ArrayList<>();


        pageNames.add(context.getString(R.string.events));
        pageIcons.add(R.drawable.ic_calendar_off);
        pageSelectedIcons.add(R.drawable.ic_calendar);
        homeTabFragments.add(new EventFragment());

        pageNames.add(context.getString(R.string.repos));
        pageIcons.add(R.drawable.ic_repo_off);
        pageSelectedIcons.add(R.drawable.ic_repo);
        homeTabFragments.add(new RepoFragment());


        for(int i = 0; i < homeTabFragments.size(); ++i)
        {
            View v = LayoutInflater.from(context).inflate(R.layout.layout_home_tab, null);
            ImageView icon = (ImageView) v.findViewById(R.id.icon);
            icon.setImageResource(getPageIcon(i));
            tabViews.add(v);
        }
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return homeTabFragments.get(position);
    }

    @Override
    public int getCount() {
        return tabViews.size();
    }

    public View getTabView(int position)
    {
        return tabViews.get(position);
    }

    public void setSelected(int position)
    {
        for(int i = 0; i < tabViews.size(); ++i) {

            View v = tabViews.get(i);
            ImageView icon = (ImageView) v.findViewById(R.id.icon);
            icon.setImageResource(getPageIcon(i));

            TextView name = (TextView) v.findViewById(R.id.name);
            name.setText(getPageName(i));
            name.setTextColor(ContextCompat.getColor(context, R.color.black));

            if (position == i) {
                icon.setImageResource(getSelectedPageIcon(i));
                name.setTextColor(ContextCompat.getColor(context, R.color.white));
            }
        }
    }

    private int getPageIcon(int position) {
        return pageIcons.get(position);
    }

    private String getPageName(int position)
    {
        return pageNames.get(position);
    }

    private int getSelectedPageIcon(int position) {
        return pageSelectedIcons.get(position);
    }
}
