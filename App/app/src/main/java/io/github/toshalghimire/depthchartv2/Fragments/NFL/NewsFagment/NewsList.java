package io.github.toshalghimire.depthchartv2.Fragments.NFL.NewsFagment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import io.github.toshalghimire.depthchartv2.Activity.InjuryActivity;
import io.github.toshalghimire.depthchartv2.Models.NewsModel;
import io.github.toshalghimire.depthchartv2.R;

/**
 * Fragment class for latest league news.
 */
public class NewsList extends Fragment {

    final private String TAG = "Debug: ";

    // List of all articles (NewsModel)
    List<NewsModel> newsList;

    // Android view init
    View view;
    private TextView loadingNews;
    private RecyclerView recyclerView;
    private NewsListAdapter newsListAdapter;
    private SearchView menuSearch;
    private Elements NewsPageElement = null;

    // Firebase refrence
    private DatabaseReference mDatabase;
    private final String NEWS_URL = "http://www.nfl.com/news";


    /**
     * Super class method that is used to create the view
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        view = inflater.inflate(R.layout.fragment_news_list, container, false);

        newsList = new ArrayList<>();

        recyclerView = (RecyclerView) view.findViewById(R.id.newsRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        loadingNews = (TextView)view.findViewById(R.id.loadingNews);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        new getNews(newsList).execute(NEWS_URL);

        return view;

    }

    /**
     * onStart is a method that runs when you first open the app. It loads the data from the internet
     */
    @Override
    public void onStart() {
        super.onStart();

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String,Map<String,String>> val = (Map<String,Map<String,String>>) dataSnapshot.getValue();

                if(val == null)
                    return;

                Map<String,String> inner =  val.get("news");


                Log.d(TAG, "Val.get(news): " + val.get("news"));
                Log.d(TAG, "inner.toString: " + inner.toString());

                JSONObject obj = new JSONObject(inner);

                Log.d(TAG, "JSONObject: " + obj.toString());

                Iterator<String> keys = obj.keys();

                // Loop through keys not anything else

                while (keys.hasNext()){
                    try {
                        JSONObject innerObject = obj.getJSONObject(keys.next());
                        Log.d(TAG, "\nInner JSONObject: " + innerObject);

                        String Title = innerObject.getString("Title").toString();
                        String Description = innerObject.getString("description").toString();
                        String Date = innerObject.getString("date").toString();
                        String Link = innerObject.getString("link").toString();

                        NewsModel temp = new NewsModel(Title,Description,Date,Link);
                        newsList.add(temp);
                        //Log.d(TAG, "\n\n Title: " + Title + " Description: " + Description + " Date: " + Date + " Link: " + Link);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    /**
     * AsyncTask to get the nfl news and display it on the view.
     */
    public class getNews extends AsyncTask<String,Void,Void> {
        private Scanner S = null;
        private Elements totalElem = null;
        private Elements passingElem = null;
        private Elements rushingElem = null;
        List<NewsModel> AsynkTaskList = null;
        ProgressBar pb;

        /**
         * Constructor that takes in the news items list and sets the RecycleView
         * @param asynkTaskList list of news items for the AsynkTask
         */
        public getNews(List<NewsModel> asynkTaskList) {
            AsynkTaskList = asynkTaskList;
        }

        /**
         * Code to be executed before scraping the data
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pb = (ProgressBar) view.findViewById(R.id.progressBar_News);
            pb.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            loadingNews.setVisibility(View.VISIBLE);
        }


        /**
         * code that scrapes the data
         * @param strings link of the website to scrape
         * @return Void
         */
        @Override
        protected Void doInBackground(String... strings) {

            // Getting data
            try {
                // TOTAL OFFENSE
                Document news = Jsoup.connect(strings[0]).get();
                totalElem = news.select("div.news-stream-module news-stream-writer-archive cf");

            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }

        /**
         * Code that gets executed after we get the data from the web.
         * sets values of views
         * @param aVoid
         */
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            recyclerView.setVisibility(View.VISIBLE);
            loadingNews.setVisibility(View.GONE);
            pb.setVisibility(View.GONE);
            NewsPageElement = totalElem;

            // newsList
            Collections.sort(newsList,Collections.reverseOrder());
            newsListAdapter = new NewsListAdapter(newsList);
            recyclerView.setAdapter(newsListAdapter);

        }

    }


    /**
     * Android method that is used to set the menu items (Search, injuries, ect..)
     * @param menu
     * @param inflater
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();

        inflater.inflate(R.menu.menu_item, menu);
        MenuItem SearchItem = menu.findItem(R.id.action_searh);
        MenuItem InjuryItem = menu.findItem(R.id.action_news);
        SearchItem.setVisible(false);

        InjuryItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent injuryIntent = new Intent(getContext(), InjuryActivity.class);
                startActivity(injuryIntent);
                return false;
            }
        });

        Log.d(TAG, "onCreateOptionsMenu: End of oncreate");
    }


}
