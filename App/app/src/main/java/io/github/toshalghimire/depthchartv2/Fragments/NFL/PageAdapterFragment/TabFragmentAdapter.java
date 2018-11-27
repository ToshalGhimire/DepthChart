package io.github.toshalghimire.depthchartv2.Fragments.NFL.PageAdapterFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import io.github.toshalghimire.depthchartv2.Fragments.NFL.NewsFagment.NewsList;
import io.github.toshalghimire.depthchartv2.Fragments.NFL.TeamFragment.TeamList;

/**
 * Class that sets up the fragment PageViewer for the HomeActivity.
 */
public class TabFragmentAdapter extends FragmentStatePagerAdapter {

    public TabFragmentAdapter(FragmentManager fm) {
        super(fm);
    }


    /**
     * returns the fragment to display on which tab, based on position.
     * @param position position of tab
     * @return fragment to display
     */
    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                TeamList teamList = new TeamList();
                return teamList;
            case 1:
                NewsList newsList = new NewsList();
                return newsList;
            default:
                return null;
        }

    }

    /**
     * Total number of fragments
     * @return number of fragmetns
     */
    @Override
    public int getCount() {
        return 2;
    }
}
