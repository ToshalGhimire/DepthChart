package io.github.toshalghimire.depthchartv2.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
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
import java.util.Scanner;

import NFL.Position;
import io.github.toshalghimire.depthchartv2.Models.DataSingleton;
import io.github.toshalghimire.depthchartv2.R;

public class teamPage_V02Activity extends AppCompatActivity {

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

//        String[] Offense_URL = {"http://www.nfl.com/stats/categorystats?tabSeq=2&offensiveStatisticCategory=GAME_STATS&conference=ALL&role=TM&season=2018&seasonType=REG&d-447263-s=TOTAL_YARDS_GAME_AVG&d-447263-o=2&d-447263-n=1",
//                "http://www.nfl.com/stats/categorystats?tabSeq=2&offensiveStatisticCategory=TEAM_PASSING&conference=ALL&role=TM&season=2018&seasonType=REG&d-447263-s=PASSING_NET_YARDS_GAME_AVG&d-447263-o=2&d-447263-n=1",
//                "http://www.nfl.com/stats/categorystats?tabSeq=2&offensiveStatisticCategory=RUSHING&conference=ALL&role=TM&season=2018&seasonType=REG&d-447263-s=RUSHING_YARDS_PER_GAME_AVG&d-447263-o=2&d-447263-n=1",
//        };
//
//        new teamPage_V02Activity.getOffense().execute(Offense_URL[0],Offense_URL[1],Offense_URL[2]);

        DataSingleton obj = DataSingleton.getInstance();

        List<String> offenseStats = obj.getOffenseStats(mTeam);
        setOffenseTextView(offenseStats);

        List<String> defenseStats = obj.getDefenseStats(mTeam);
        setDefenseTextView(defenseStats);

        String url = intent.getStringExtra("ScraperLink");
        new teamPage_V02Activity.getTeam().execute(url);

    }


    private void setOffenseTextView(List<String> Data){
        O_Rank_textview.setText("Offense Ranked " + Data.get(1));
        O_Rank_textview.setTextColor(Color.parseColor("#19000A"));
        O_Passing_textview.setText(Data.get(2) + " Yds/G");
        O_Rushing_textview.setText(Data.get(4) + " Yds/G");
        O_Points_textview.setText(Data.get(0) + " P/G");
    }

    private void setDefenseTextView(List<String> Data){
        D_Rank_textview.setText("Defense Ranked " + Data.get(1));
        D_Rank_textview.setTextColor(Color.parseColor("#19000A"));
        D_Passing_textview.setText(Data.get(2) + " Yds/G");
        D_Rushing_textview.setText(Data.get(4) + " Yds/G");
        D_Points_textview.setText(Data.get(0) + " P/G");
    }

    private void setGradiantBackground(){
        LinearLayout linearLayout = (LinearLayout) this.findViewById(R.id.gradient_LinearLayout);
        int[] colors = {Color.parseColor("#D3D3D3"),Color.parseColor(intent.getStringExtra("teamColor"))};
        GradientDrawable gd = new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP,colors);
        linearLayout.setBackground(gd);

    }



    /**
     * AsyncTask to get the teams Depthchart and display it on the page.
     */
    private class getTeam extends AsyncTask<String, Integer, Void> {

        private ProgressBar pb;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pb = (ProgressBar)findViewById(R.id.progressBar_depthChart);
            pb.setVisibility(View.VISIBLE);



        }

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

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            TextView positionText = (TextView)findViewById(R.id.Player_pos);

            pb.setVisibility(View.INVISIBLE);
            positionText.setText(sortPositions());
        }

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
