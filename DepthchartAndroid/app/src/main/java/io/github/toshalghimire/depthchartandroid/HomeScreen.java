package io.github.toshalghimire.depthchartandroid;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Toolbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class HomeScreen extends AppCompatActivity {

    List<TeamModel> mTeamList = new ArrayList<>();

    RecyclerView recyclerView;
    TeamAdapter teamAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home_screen);


        recyclerView = (RecyclerView) findViewById(R.id.teamRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<String> code = getJSON("code");
        List<String> city = getJSON("city");
        List<String> teamName = getJSON("teamName");
        List<String> website = getJSON("website");
        List<String> color = getJSON("color");


        for(int i = 0; i < 32; i++){
            TeamModel temp = new TeamModel(code.get(i),city.get(i),teamName.get(i),website.get(i),color.get(i));
            mTeamList.add(temp);
        }

        teamAdapter = new TeamAdapter(this,mTeamList);
        recyclerView.setAdapter(teamAdapter);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_items, menu);
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
