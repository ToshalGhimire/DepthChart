package io.github.toshalghimire.depthchartv2.Activity;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.github.toshalghimire.depthchartv2.ListAdapters.NewsAdapter;
import io.github.toshalghimire.depthchartv2.Models.NewsModel;
import io.github.toshalghimire.depthchartv2.R;

public class News extends AppCompatActivity {

    private RecyclerView recyclerView;
    private NewsAdapter newsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        centerTitle();

//        recyclerView = (RecyclerView) findViewById(R.id.news_RecyclerView);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        //new GetNews().execute("http://www.rotoworld.com/headlines/nfl/0/football-headlines?rw=1");
//
//        List<NewsModel> nm = new ArrayList<>();
//
//        nm.add(new NewsModel("Test  1 ", "This is a short description for 1 ", "Broncos"));
//        nm.add(new NewsModel("Test  2 ", "This is a short description for 2 ", "Bills"));
//        nm.add(new NewsModel("Test  3 ", "This is a short description for 3 ", "Chargers"));
//        nm.add(new NewsModel("Test  4 ", "This is a short description for 4 ", "49ers"));
//        nm.add(new NewsModel("Test  5 ", "This is a short description for 5 ", "Bears"));
//        nm.add(new NewsModel("Test  6 ", "This is a short description for 6 ", "Browns"));
//        nm.add(new NewsModel("Test  7 ", "This is a short description for 7 ", "Rams"));
//
//        newsAdapter = new NewsAdapter(this, nm);
//        recyclerView.setAdapter(newsAdapter);


        new GetNews(this).execute("http://www.rotoworld.com/headlines/nfl/0/football-headlines?rw=1");
    }


    private void centerTitle() {
        ArrayList<View> textViews = new ArrayList<>();

        getWindow().getDecorView().findViewsWithText(textViews, getTitle(), View.FIND_VIEWS_WITH_TEXT);

        if(textViews.size() > 0) {
            AppCompatTextView appCompatTextView = null;
            if(textViews.size() == 1) {
                appCompatTextView = (AppCompatTextView) textViews.get(0);
            } else {
                for(View v : textViews) {
                    if(v.getParent() instanceof Toolbar) {
                        appCompatTextView = (AppCompatTextView) v;
                        break;
                    }
                }
            }

            if(appCompatTextView != null) {
                ViewGroup.LayoutParams params = appCompatTextView.getLayoutParams();
                params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                appCompatTextView.setLayoutParams(params);
                appCompatTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            }
        }
    }

    /**
     * AsyncTask to get the Latest NFL news stats and display it on offense_textview.
     */
    private class GetNews extends AsyncTask<String, Integer, Void> {

        TextView notfound;
        Elements e;
        String title,description,code,longDescription;
        Context C;
        List<NewsModel> newsModel= null;

        public GetNews(Context c) {
            this.C = c;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            notfound = (TextView)findViewById(R.id.notFound);
            notfound.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(String... Str) {
            newsModel = new ArrayList<>();
            try {
                Document teamSite = Jsoup.connect(Str[0]).get();
                e = teamSite.select("div.RW_pn");

                String style ="";
                Log.d("testingROTO", "doInBackground: style was "+style);


                title = "";
                for(Element i: e.select("div.pb")){
                    //Get title of post
                    title = i.select("div.headline").text() ;


                    //Regex to find team code
                    style = i.select("div.headline").attr("style");
                    Matcher m = Pattern.compile(
                            Pattern.quote("NFL/teams/icons/")
                                    + "(.*?)"
                                    + Pattern.quote(".gif)")
                    ).matcher(style);

                    while(m.find()){
                        String match = m.group(1);
                        code = match;
                    }

                    // Get description from post
                    description = i.select("div.report").text();

                    longDescription = i.select("div.impact").text();
                    // Add models to list
                    newsModel.add(new NewsModel(title,description,longDescription,code));

                }





            } catch (IOException e) {
                e.printStackTrace();
            }



            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            notfound.setVisibility(View.GONE);

            recyclerView = (RecyclerView) findViewById(R.id.news_RecyclerView);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this.C));

            newsAdapter = new NewsAdapter(this.C, newsModel);
            recyclerView.setAdapter(newsAdapter);
        }



    }

}
