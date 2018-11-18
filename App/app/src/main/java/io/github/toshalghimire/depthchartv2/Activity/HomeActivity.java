package io.github.toshalghimire.depthchartv2.Activity;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import io.github.toshalghimire.depthchartv2.Fragments.TeamsFragment.FragmentPageAdapter.TabFragmentAdapter;
import io.github.toshalghimire.depthchartv2.Fragments.TeamsFragment.InjuryFragment.InjuryList;
import io.github.toshalghimire.depthchartv2.Fragments.TeamsFragment.TeamList;
import io.github.toshalghimire.depthchartv2.Fragments.TeamsFragment.TeamListAdapter;
import io.github.toshalghimire.depthchartv2.Models.DataSingleton;
import io.github.toshalghimire.depthchartv2.Models.TeamModel;
import io.github.toshalghimire.depthchartv2.R;
import io.github.toshalghimire.depthchartv2.ListAdapters.TeamAdapter;

public class HomeActivity extends AppCompatActivity implements TeamList.OnFragmentInteractionListener  {

    private List<TeamModel> teamList = new ArrayList<>();

    private RecyclerView recyclerView;

    private TeamAdapter teamAdapter;
    private Boolean injuryPressed;
    Intent NewsIntent;
    private android.support.v7.widget.Toolbar toolbar;

    private ViewPager viewPager;
    private TabFragmentAdapter tabFragmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // DataSingleton
        DataSingleton obj = DataSingleton.getInstance();

        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.home_toolbar);
        setSupportActionBar(toolbar);

        injuryPressed = false;

        NewsIntent = new Intent(this, News.class);


        //viewPager = findViewById(R.id.)
        tabFragmentAdapter = new TabFragmentAdapter(getSupportFragmentManager());

        TabLayout tabLayout = (TabLayout)findViewById(R.id.tablayout);
        tabLayout.addTab(tabLayout.newTab().setText("Team"));
        tabLayout.addTab(tabLayout.newTab().setText("injuy"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager)findViewById(R.id.viewPager);
        final TabFragmentAdapter tabFragmentAdapter = new TabFragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(tabFragmentAdapter);
        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

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

    }
}
