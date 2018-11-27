package io.github.toshalghimire.depthchartv2.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import NFL.Position;
import io.github.toshalghimire.depthchartv2.Models.DataSingleton;
import io.github.toshalghimire.depthchartv2.R;


/**
 * Activity used to display offense and defense stats and the teams current depth chart.
 */
public class TeamPageActivity extends AppCompatActivity {

    private TextView teamName_textview;
    private ArrayList<Position> mDepthChart;


    private TextView O_Rank_textview;
    private TextView O_Points_textview;
    private TextView O_Passing_textview;
    private TextView O_Rushing_textview;

    private TextView D_Rank_textview;
    private TextView D_Points_textview;
    private TextView D_Passing_textview;
    private TextView D_Rushing_textview;

    private String mCity;
    private String mTeam;
    private ImageView teamLogo_imageview;
    private int teamColor;
    private Intent intent;

    DataSingleton obj;

    /**
     * Creates view
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_page__v02);
        intent = getIntent();

        //sets color of status bar
        setStatusBarColor();

        mDepthChart = new ArrayList<Position>();

        mTeam = intent.getStringExtra("teamName");
        mCity = intent.getStringExtra("teamCity");


        teamColor = Color.parseColor(intent.getStringExtra("teamColor"));

        // Offenseive Stats textview initilization
        O_Rank_textview = (TextView) findViewById(R.id.O_Rank_textview);

        O_Points_textview = (TextView) findViewById(R.id.O_Points_textview);
        O_Points_textview.setTextColor(teamColor);

        O_Passing_textview = (TextView) findViewById(R.id.O_Passing_textview);
        O_Passing_textview.setTextColor(teamColor);

        O_Rushing_textview = (TextView) findViewById(R.id.O_Rushing_textview);
        O_Rushing_textview.setTextColor(teamColor);

        // Stats textview initilization
        D_Rank_textview = (TextView) findViewById(R.id.D_Rank_textview);

        D_Points_textview = (TextView) findViewById(R.id.D_Points_textview);
        D_Points_textview.setTextColor(teamColor);

        D_Passing_textview = (TextView) findViewById(R.id.D_Passing_textview);
        D_Passing_textview.setTextColor(teamColor);

        D_Rushing_textview = (TextView) findViewById(R.id.D_Rushing_textview);
        D_Rushing_textview.setTextColor(teamColor);

        teamName_textview = (TextView) findViewById(R.id.teamName_textview);
        teamLogo_imageview = (ImageView) findViewById(R.id.logo_imageView);

        teamLogo_imageview.setImageResource(intent.getIntExtra("teamLogo",0));
        setGradiantBackground();

        teamName_textview.setText("The " + intent.getStringExtra("teamName"));


        obj = DataSingleton.getInstance();

        List<String> offenseStats = obj.getOffenseStats(mTeam);
        setOffenseTextView(offenseStats);

        List<String> defenseStats = obj.getDefenseStats(mTeam);
        setDefenseTextView(defenseStats);

        String url = intent.getStringExtra("ScraperLink");
        new TeamPageActivity.getTeam().execute(url);

    }


    /**
     * Using the data change the values of the textview of offensive stats
     * @param Data offensive stats of team
     */
    private void setOffenseTextView(List<String> Data){
        O_Rank_textview.setText("Offense Ranked " + Data.get(1));
        O_Rank_textview.setTextColor(Color.parseColor("#19000A"));
        O_Passing_textview.setText(Data.get(2) + " Yds/G");
        O_Rushing_textview.setText(Data.get(4) + " Yds/G");
        O_Points_textview.setText(Data.get(0) + " P/G");
    }

    /**
     * Using the data change the values of the textview of defensive stats
     * @param Data defensive stats of team
     */
    private void setDefenseTextView(List<String> Data){
        D_Rank_textview.setText("Defense Ranked " + Data.get(1));
        D_Rank_textview.setTextColor(Color.parseColor("#19000A"));
        D_Passing_textview.setText(Data.get(2) + " Yds/G");
        D_Rushing_textview.setText(Data.get(4) + " Yds/G");
        D_Points_textview.setText(Data.get(0) + " P/G");
    }

    /**
     * Set the background color of the activity
     */
    private void setGradiantBackground(){
        LinearLayout linearLayout = (LinearLayout) this.findViewById(R.id.gradient_LinearLayout);
        int[] colors = {Color.parseColor("#D3D3D3"),Color.parseColor(intent.getStringExtra("teamColor"))};
        GradientDrawable gd = new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP,colors);
        linearLayout.setBackground(gd);

    }



    /**
     * AsyncTask to web scrape the teams depth chart and display it on the page.
     */
    private class getTeam extends AsyncTask<String, Integer, Void> {

        private ProgressBar pb;

        /**
         * Code to be executed before scraping the data
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pb = (ProgressBar)findViewById(R.id.progressBar_depthChart);
            pb.setVisibility(View.VISIBLE);



        }

        /**
         * code that scrapes the data
         * @param Str link of the website to scrape
         * @return Void
         */
        @Override
        protected Void doInBackground(String... Str) {
            try {
                Document teamSite = Jsoup.connect(Str[0]).get();
                Elements e = teamSite.select("table").select("tr").select("td");

                Position temp = null;
                for(Element row : e){
                    if (row.text().length() <= 4 && !row.text().equals("")) {
                        // parsing Positions from element e
                        if (temp != null)
                            mDepthChart.add(temp);

                        temp = new Position(row.text());

                    } else {
                        // Adding players to Arraylist
                        if (!row.text().equals(""))
                            temp.players.add(row.text());
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }



            return null;
        }

        /**
         * Code that gets executed after the web scraping is done
         * @param aVoid
         */
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            TextView positionText = (TextView)findViewById(R.id.Player_pos);

            pb.setVisibility(View.INVISIBLE);
            positionText.setText(sortPositions());
        }

        /**
         * Used to sort the data of fantasy football related positions.
         * @return
         */
        public String sortPositions(){
            String temp = "";
            for(Position p : mDepthChart){

                if( p.playerPos.equals("QB")){

                    temp += p.toString() + "\n";
                }
            }

            for(Position p : mDepthChart){

                if (p.playerPos.equals("RB")) {
                    temp += p.toString() + "\n";
                }
            }

            for(Position p : mDepthChart){

                if (p.playerPos.equals("WR")) {
                    temp += p.toString() + "\n";
                }
            }

            for(Position p : mDepthChart){

                if (p.playerPos.equals("TE")) {
                    temp += p.toString() + "\n";
                }
            }
            for(Position p : mDepthChart){

                if (p.playerPos.equals("K")) {
                    temp += p.toString() + "\n";
                }
            }
            return temp;
        }


    }


    /**
     * Set the status bar color to the same color as the teams primary color.
     */
    public void setStatusBarColor(){
        Window window = this.getWindow();

        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        // finally change the color
        window.setStatusBarColor(Color.parseColor(intent.getStringExtra("teamColor")));

    }



}
