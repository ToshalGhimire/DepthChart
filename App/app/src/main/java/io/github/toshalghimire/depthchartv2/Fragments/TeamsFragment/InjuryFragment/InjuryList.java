package io.github.toshalghimire.depthchartv2.Fragments.TeamsFragment.InjuryFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import io.github.toshalghimire.depthchartv2.Models.TeamModel;
import io.github.toshalghimire.depthchartv2.R;


public class InjuryList extends Fragment implements SearchView.OnQueryTextListener{

    final private String TAG = "Debug: ";
    private List<TeamModel> teamList = new ArrayList<>();
    private RecyclerView recyclerView;
    private InjuryListAdapter injuryListAdapter;
    private SearchView menuSearch;
    View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_team_list, container, false);

        setHasOptionsMenu(true);

        recyclerView = (RecyclerView) view.findViewById(R.id.teamRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        List<String> code = getJSON("code");
        List<String> city = getJSON("city");
        List<String> teamName = getJSON("teamName");
        List<String> website = getJSON("website");
        List<String> color = getJSON("color");
//
        TeamModel temp = null;
        for (int i = 0; i < 3; i++) {
            temp = new TeamModel(code.get(i), city.get(i), teamName.get(i), website.get(i), color.get(i));
            teamList.add(temp);
        }

        injuryListAdapter = new InjuryListAdapter(teamList);
        recyclerView.setAdapter(injuryListAdapter);

        return view;

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
            InputStream is = getContext().getAssets().open("teams.json");
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


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu_item, menu);
        MenuItem SearchItem = menu.findItem(R.id.action_searh);
        MenuItem InjuryItem = menu.findItem(R.id.action_news);
        SearchItem.setVisible(false);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(SearchItem);
        searchView.setOnQueryTextListener(this);

        Log.d(TAG, "onCreateOptionsMenu: End of oncreate");
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        Log.d(TAG, "onQueryTextSubmit Ran");
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Log.d(TAG, "onQueryTextChange: Start");
        newText = newText.toLowerCase().trim();
        ArrayList<TeamModel> newList = new ArrayList<>();
        Log.d(TAG, "onQueryTextChange: Array and string init");
        for(TeamModel tm : teamList){
            String team = tm.getTeamName().toLowerCase();
            String city = tm.getTeamCity().toLowerCase();
            String full = tm.getTeamFullName().toLowerCase();
            String code = tm.getTeamCode().toLowerCase();
            if(team.contains(newText) || city.contains(newText) || full.contains(newText) || code.contains(newText)){
                newList.add(tm);
            }
        }
        Log.d(TAG, "onQueryTextChange: made updated newList: list<TeamModel>");
        
        TextView notfound = view.findViewById(R.id.notFound);

        if(newList.size() == 0){
            notfound.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }else{
            notfound.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
        Log.d(TAG, "onQueryTextChange: messed with visiblity");
        injuryListAdapter.setFilter(newList);


        return true;
    }
}
