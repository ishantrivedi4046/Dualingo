package com.example.dualingo;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class CategoryPageAdapter extends FragmentPagerAdapter {
    String title[]={"Number","Family","Colors","Phrases"};
    public CategoryPageAdapter(FragmentManager fm)
    {
        super(fm);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }

    @Override
    public int getCount() {
        return 4;
    }
    @Override
    public Fragment getItem(int i) {
        switch (i)
        {
            case 0:
                return new number_fragment();
            case 1:
                return new family_fragment();
            case 2:
                return new color_fragment();
            case 3:
                return new phrases_fragment();
                default:
                    return null;
        }
    }
}
