package com.example.itskh.myapplication;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.itskh.myapplication.stutiFragment.eveningStuti;
import com.example.itskh.myapplication.stutiFragment.morningStuti;

public class stutiActivityAdapter extends FragmentPagerAdapter {

    stutiActivityAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i)
        {
            case 0:
            {
                morningStuti Morning = new morningStuti();
                return Morning;
            }
            case 1:
            {
                eveningStuti Evening = new eveningStuti();
                return Evening;
            }
        }

        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position)
        {
            case 0:
            {
                return "प्रातःकालीन";
            }
            case 1:
            {
                return "अपराह्न कालीन";
            }
        }
        return super.getPageTitle(position);
    }
}
