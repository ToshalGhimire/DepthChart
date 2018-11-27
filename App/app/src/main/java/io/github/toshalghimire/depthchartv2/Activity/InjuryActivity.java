package io.github.toshalghimire.depthchartv2.Activity;

import android.content.Context;
import android.os.AsyncTask;
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
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.github.toshalghimire.depthchartv2.ListAdapters.InjuryAdapter;
import io.github.toshalghimire.depthchartv2.Models.InjuryModel;
import io.github.toshalghimire.depthchartv2.R;

public class InjuryActivity extends AppCompatActivity {
    final private String TAG = "INJURY NEWS";

    private RecyclerView recyclerView;
    private InjuryAdapter injuryAdapter;

    /**
     * Method used for the layout and methods connection
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        centerTitle();

        // Get data from the web and setup a recycle view
        new GetNews(this).execute("http://www.rotoworld.com/headlines/nfl/0/football-headlines?rw=1");

    }


    /**
     * Method to center the title of the app bar
     */
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
     * AsyncTask to get the Latest injury and use it to populate a recycle view.
     */
    private class GetNews extends AsyncTask<String, Integer, Void> {

        TextView notfound;
        Elements e;
        String title,description,code,longDescription;
        Context C;
        List<InjuryModel> injuryModel = null;

        public GetNews(Context c) {
            this.C = c;
        }

        /**
         * Code to be executed before scraping the data
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            notfound = (TextView)findViewById(R.id.notFound);
            notfound.setVisibility(View.VISIBLE);
            Log.d(TAG, "onPreExecute: on asyntask");
        }


        /**
         * code that scrapes the data
         * @param Str link of the website to scrape
         * @return Void
         */
        @Override
        protected Void doInBackground(String... Str) {
            Log.d(TAG, "doInBackground: Start of doinBackground");
            injuryModel = new ArrayList<>();
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

                    Log.d(TAG, "doInBackground: CODE "+ code);
                    // college news to FA
                    if(code.equals("CLG"))
                        code = "FA";
                    // Get description from post
                    description = i.select("div.report").text();

                    longDescription = i.select("div.impact").text();
                    // Add models to list
                    injuryModel.add(new InjuryModel(title,description,longDescription,code));

                }





            } catch (IOException e) {
                e.printStackTrace();
            }


            Log.d(TAG, "doInBackground: End of do in background");
            return null;
        }

        /**
         * Code that gets executed after the web scraping is done
         * @param aVoid
         */
        @Override
        protected void onPostExecute(Void aVoid) {
            
            super.onPostExecute(aVoid);
            notfound.setVisibility(View.GONE);

            recyclerView = (RecyclerView) findViewById(R.id.news_RecyclerView);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this.C));

            injuryAdapter = new InjuryAdapter(this.C, injuryModel);
            recyclerView.setAdapter(injuryAdapter);

            Log.d(TAG, "onPostExecute: end of onPost");
        }



    }

}
