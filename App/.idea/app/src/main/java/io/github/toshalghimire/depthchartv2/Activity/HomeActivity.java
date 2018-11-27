package io.github.toshalghimire.depthchartv2.Activity;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import io.github.toshalghimire.depthchartv2.Fragments.NFL.PageAdapterFragment.TabFragmentAdapter;
import io.github.toshalghimire.depthchartv2.Fragments.NFL.TeamFragment.TeamList;
import io.github.toshalghimire.depthchartv2.Models.DataSingleton;
import io.github.toshalghimire.depthchartv2.Models.TeamModel;
import io.github.toshalghimire.depthchartv2.R;

/**
 * Home Activity class that is the main activity for the app
 * Uses ViewPager and Fragments
 */
public class HomeActivity extends AppCompatActivity implements TeamList.OnFragmentInteractionListener  {

    private List<TeamModel> teamList = new ArrayList<>();

    private RecyclerView recyclerView;

    private Boolean injuryPressed;
    Intent NewsIntent;
    private android.support.v7.widget.Toolbar toolbar;

    private ViewPager viewPager;
    private TabFragmentAdapter tabFragmentAdapter;

    DataSingleton obj;


    /**
     * Method used for the layout and methods connection
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        obj = DataSingleton.getInstance(this);

        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.home_toolbar);
        setSupportActionBar(toolbar);

        injuryPressed = false;

        NewsIntent = new Intent(this, InjuryActivity.class);


        // Setting up tabs
        tabFragmentAdapter = new TabFragmentAdapter(getSupportFragmentManager());
        TabLayout tabLayout = (TabLayout)findViewById(R.id.tablayout);
        tabLayout.addTab(tabLayout.newTab().setText("Team"));
        tabLayout.addTab(tabLayout.newTab().setText("News"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        // Setting up fragments to display for each tab
        viewPager = (ViewPager)findViewById(R.id.viewPager);
        tabFragmentAdapter = new TabFragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(tabFragmentAdapter);
        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.setCurrentItem(1);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }


    @Override
    public void onFragmentInteraction(Uri uri) {
        return;
    }
}
