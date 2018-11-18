package io.github.toshalghimire.depthchartv2.Fragments.TeamsFragment.FragmentPageAdapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import io.github.toshalghimire.depthchartv2.Fragments.TeamsFragment.InjuryFragment.InjuryList;
import io.github.toshalghimire.depthchartv2.Fragments.TeamsFragment.TeamList;

public class TabFragmentAdapter extends FragmentStatePagerAdapter {

    public TabFragmentAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                TeamList teamList = new TeamList();
                return teamList;
            case 1:
                InjuryList injuryList = new InjuryList();
                return injuryList;
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return 2;
    }
}
