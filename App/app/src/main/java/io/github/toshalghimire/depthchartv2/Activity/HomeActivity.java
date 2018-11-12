package io.github.toshalghimire.depthchartv2.Activity;

import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toolbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import io.github.toshalghimire.depthchartv2.Models.TeamModel;
import io.github.toshalghimire.depthchartv2.R;
import io.github.toshalghimire.depthchartv2.ListAdapters.TeamAdapter;

public class HomeActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    private List<TeamModel> teamList = new ArrayList<>();

    private RecyclerView recyclerView;
    private TeamAdapter teamAdapter;
    private Boolean injuryPressed;
    Intent NewsIntent;
    private android.support.v7.widget.Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.home_toolbar);
        setSupportActionBar(toolbar);

        injuryPressed = false;

        NewsIntent = new Intent(this, News.class);

        recyclerView = (RecyclerView) findViewById(R.id.teamRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<String> code = getJSON("code");
        List<String> city = getJSON("city");
        List<String> teamName = getJSON("teamName");
        List<String> website = getJSON("website");
        List<String> color = getJSON("color");

        TeamModel temp = null;
        for (int i = 0; i < 32; i++) {
            temp = new TeamModel(code.get(i), city.get(i), teamName.get(i), website.get(i), color.get(i));
            teamList.add(temp);
        }

        teamAdapter = new TeamAdapter(this, teamList);
        recyclerView.setAdapter(teamAdapter);



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item,menu);
        MenuItem SearchItem = menu.findItem(R.id.action_searh);
        final MenuItem NewsItem = menu.findItem(R.id.action_news);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(SearchItem);

        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewsItem.setVisible(false);
            }
        });
        // Detect SearchView close
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                NewsItem.setVisible(true);
                return false;
            }
        });


        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_news:
                this.startActivity(NewsIntent);
                return true;
        }
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        newText = newText.toLowerCase().trim();
        ArrayList<TeamModel> newList = new ArrayList<>();
        for(TeamModel tm : teamList){
            String team = tm.getTeamName().toLowerCase();
            String city = tm.getTeamCity().toLowerCase();
            String full = tm.getTeamFullName().toLowerCase();
            String code = tm.getTeamCode().toLowerCase();
            if(team.contains(newText) || city.contains(newText) || full.contains(newText) || code.contains(newText)){
                newList.add(tm);
            }
        }
        TextView notfound = (TextView)findViewById(R.id.notFound);

        if(newList.size() == 0){
            notfound.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }else{
            notfound.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
        teamAdapter.setFilter(newList);


        return true;
    }

    /**
     * @Breif returns data from the json file given specific key
     *
     * @param key key you want to retrive
     * @return Arraylist of JSON values
     */
    public ArrayList<String> getJSON(String key){
        String json;
        ArrayList<String> temp_arraylist = new ArrayList<>();
        try {
            InputStream is = getAssets().open("teams.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            json = new String(buffer,"UTF-8");
            JSONArray jsonArray = new JSONArray(json);

            for( int i = 0 ; i < jsonArray.length(); i++){
                JSONObject obj = jsonArray.getJSONObject(i);
                temp_arraylist.add(obj.getString(key));
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e){
            e.printStackTrace();
        }

        return temp_arraylist;
    }





}
