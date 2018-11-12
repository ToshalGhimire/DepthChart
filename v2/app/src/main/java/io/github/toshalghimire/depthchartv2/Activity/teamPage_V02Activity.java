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

        String[] Offense_URL = {"http://www.nfl.com/stats/categorystats?tabSeq=2&offensiveStatisticCategory=GAME_STATS&conference=ALL&role=TM&season=2018&seasonType=REG&d-447263-s=TOTAL_YARDS_GAME_AVG&d-447263-o=2&d-447263-n=1",
                "http://www.nfl.com/stats/categorystats?tabSeq=2&offensiveStatisticCategory=TEAM_PASSING&conference=ALL&role=TM&season=2018&seasonType=REG&d-447263-s=PASSING_NET_YARDS_GAME_AVG&d-447263-o=2&d-447263-n=1",
                "http://www.nfl.com/stats/categorystats?tabSeq=2&offensiveStatisticCategory=RUSHING&conference=ALL&role=TM&season=2018&seasonType=REG&d-447263-s=RUSHING_YARDS_PER_GAME_AVG&d-447263-o=2&d-447263-n=1",
        };

        new teamPage_V02Activity.getOffense().execute(Offense_URL[0],Offense_URL[1],Offense_URL[2]);

        String[] Defense_URL = {"http://www.nfl.com/stats/categorystats?tabSeq=2&defensiveStatisticCategory=GAME_STATS&conference=ALL&role=OPP&season=2018&seasonType=REG&d-447263-s=TOTAL_YARDS_GAME_AVG&d-447263-o=1&d-447263-n=1",
                "http://www.nfl.com/stats/categorystats?tabSeq=2&defensiveStatisticCategory=TEAM_PASSING&conference=ALL&role=OPP&season=2018&seasonType=REG&d-447263-s=PASSING_NET_YARDS_GAME_AVG&d-447263-o=1&d-447263-n=1",
                "http://www.nfl.com/stats/categorystats?tabSeq=2&defensiveStatisticCategory=RUSHING&conference=ALL&role=OPP&season=2018&seasonType=REG&d-447263-s=RUSHING_YARDS_PER_GAME_AVG&d-447263-o=1&d-447263-n=1",
        };

        new teamPage_V02Activity.getDefense().execute(Defense_URL[0],Defense_URL[1],Defense_URL[2]);


        String url = intent.getStringExtra("ScraperLink");
        new teamPage_V02Activity.getTeam().execute(url);

    }

    private void setGradiantBackground(){
        LinearLayout linearLayout = (LinearLayout) this.findViewById(R.id.gradient_LinearLayout);
        int[] colors = {Color.parseColor("#D3D3D3"),Color.parseColor(intent.getStringExtra("teamColor"))};
        GradientDrawable gd = new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP,colors);
        linearLayout.setBackground(gd);

    }

    /**
     * AsyncTask to get the teams Offense stats and display it on offense_textview.
     */
    public class getOffense extends AsyncTask<String,Void,List<String>> {
        private List<String> stats = null;
        private Scanner S = null;
        private Elements tempElement = null;

        TextView O_ranked = (TextView)findViewById(R.id.O_Rank_textview);
        TextView O_Points = (TextView)findViewById(R.id.O_Points_textview);
        TextView O_Passing = (TextView)findViewById(R.id.O_Passing_textview);
        TextView O_Rushing = (TextView)findViewById(R.id.O_Rushing_textview);
        TextView O_Points_label = (TextView)findViewById(R.id.O_Points_label_textview);
        TextView O_Passing_label = (TextView)findViewById(R.id.O_Passing_label_textview);
        TextView O_Rushing_label = (TextView)findViewById(R.id.O_Rushing_label_textview);

        ProgressBar pb = (ProgressBar)findViewById(R.id.progressBar_stats);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            O_Points_label.setVisibility(View.INVISIBLE);
            O_Passing_label.setVisibility(View.INVISIBLE);
            O_Rushing_label.setVisibility(View.INVISIBLE);
            O_ranked.setVisibility(View.INVISIBLE);
            O_Passing.setVisibility(View.INVISIBLE);
            O_Rushing.setVisibility(View.INVISIBLE);
            O_Points.setVisibility(View.INVISIBLE);

            pb.setVisibility(View.VISIBLE);


        }

        @Override
        protected List<String> doInBackground(String... strings) {

            stats = new ArrayList<>();

            try {
                // TOTAL DEFENSE
                Document totalDefence_doc = Jsoup.connect(strings[0]).get();
                tempElement = totalDefence_doc.select("tbody").select("tr");

                String Ranked = "";
                for (int i = 0; i < 33; i++) {
                    String ElementString = tempElement.get(i).text();

                    if(ElementString.contains(mTeam)) {
                        S = new Scanner(ElementString);
                        Ranked = S.next();

                        while(!S.hasNext(mTeam))
                            S.next();

                        break;
                    }
                }

                // Skiping to Points per game
                for(int i = 0; i < 2; i ++)
                    S.next();

                stats.add(S.next());
                stats.add(ordinal(Ranked));


                // PASSing DEFENSE
                Document passDefence_doc = Jsoup.connect(strings[1]).get();
                tempElement = passDefence_doc.select("tbody").select("tr");


                Ranked = "";
                for (int i = 0; i < 33; i++) {
                    String ElementString = tempElement.get(i).text();

                    if(ElementString.contains(mTeam)) {
                        S = new Scanner(ElementString);
                        Ranked = S.next();

                        while(!S.hasNext(mTeam))
                            S.next();

                        break;
                    }
                }

                for(int i = 0; i < 10; i ++)
                    S.next();

                stats.add(S.next());
                stats.add(ordinal(Ranked));

                // RUSHING DEFENSE
                Document rushDefence_doc  = Jsoup.connect(strings[2]).get();
                tempElement = rushDefence_doc.select("tbody").select("tr");


                Ranked = "";
                for (int i = 0; i < 33; i++) {
                    String ElementString = tempElement.get(i).text();

                    if(ElementString.contains(mTeam)) {
                        S = new Scanner(ElementString);
                        Ranked = S.next();

                        while(!S.hasNext(mTeam))
                            S.next();

                        break;
                    }
                }

                for(int i = 0; i < 8; i ++)
                    S.next();

                stats.add(S.next());
                stats.add(ordinal(Ranked));

            } catch (IOException e) {
                e.printStackTrace();
            }


            return stats;
        }


        @Override
        protected void onPostExecute(List<String> strings) {
            super.onPostExecute(strings);


//            String temp = "The defense are ranked " +  strings.get(1) + " overall "
//                    + "with " + strings.get(2) + " PASSING Yds/G (" + strings.get(3) + ") and "
//                    + strings.get(4) +" RUSHING Yds/G ("+ strings.get(5) + "). They Allow " + strings.get(0) + " points per game.";



            O_Passing_label.setText("Pass (" + strings.get(3) + ")");
            O_Rushing_label.setText("Rush (" + strings.get(5) + ")");

            O_Points_label.setVisibility(View.VISIBLE);
            O_Passing_label.setVisibility(View.VISIBLE);
            O_Rushing_label.setVisibility(View.VISIBLE);

            O_ranked.setVisibility(View.VISIBLE);
            O_Passing.setVisibility(View.VISIBLE);
            O_Rushing.setVisibility(View.VISIBLE);
            O_Points.setVisibility(View.VISIBLE);
            pb.setVisibility(View.GONE);

            O_ranked.setText("Offense Ranked " + strings.get(1));
            O_ranked.setTextColor(Color.parseColor("#19000A"));
            O_Passing.setText(strings.get(2) + " Yds/G");//(" + strings.get(3) + ")");
            O_Rushing.setText(strings.get(4) + " Yds/G");// (" + strings.get(5) + ")");
            O_Points.setText(strings.get(0) + " P/G");


        }

        public  String ordinal(String input) {
            int i = Integer.parseInt(input);
            String[] sufixes = new String[] { "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th" };
            switch (i % 100) {
                case 11:
                case 12:
                case 13:
                    return i + "th";
                default:
                    return i + sufixes[i % 10];
            }
        }
    }


    /**
     * AsyncTask to get the teams Defense stats and display it on defense_textview.
     */
    public class getDefense extends AsyncTask<String,Void,List<String>>{
        private List<String> stats = null;
        private Scanner S = null;
        private Elements tempElement = null;

        TextView D_ranked = (TextView)findViewById(R.id.D_Rank_textview);
        TextView D_Points = (TextView)findViewById(R.id.D_Points_textview);
        TextView D_Passing = (TextView)findViewById(R.id.D_Passing_textview);
        TextView D_Rushing = (TextView)findViewById(R.id.D_Rushing_textview);

        TextView D_Points_label = (TextView)findViewById(R.id.D_Points_label_textview);
        TextView D_Passing_label = (TextView)findViewById(R.id.D_Passing_label_textview);
        TextView D_Rushing_label = (TextView)findViewById(R.id.D_Rushing_label_textview);


        @Override
        protected List<String> doInBackground(String... strings) {

            stats = new ArrayList<>();

            try {
                // TOTAL DEFENSE
                Document totalDefence_doc = Jsoup.connect(strings[0]).get();
                tempElement = totalDefence_doc.select("tbody").select("tr");

                String Ranked = "";
                for (int i = 0; i < 33; i++) {
                    String ElementString = tempElement.get(i).text();

                    if(ElementString.contains(mTeam)) {
                        S = new Scanner(ElementString);
                        Ranked = S.next();

                        while(!S.hasNext(mTeam))
                            S.next();

                        break;
                    }
                }

                // Skiping to Points per game
                for(int i = 0; i < 2; i ++)
                    S.next();

                stats.add(S.next());
                stats.add(ordinal(Ranked));

                // PASSing DEFENSE
                Document passDefence_doc = Jsoup.connect(strings[1]).get();
                tempElement = passDefence_doc.select("tbody").select("tr");


                Ranked = "";
                for (int i = 0; i < 33; i++) {
                    String ElementString = tempElement.get(i).text();

                    if(ElementString.contains(mTeam)) {
                        S = new Scanner(ElementString);
                        Ranked = S.next();

                        while(!S.hasNext(mTeam))
                            S.next();

                        break;
                    }
                }

                for(int i = 0; i < 10; i ++)
                    S.next();

                stats.add(S.next());
                stats.add(ordinal(Ranked));

                // RUSHING DEFENSE
                Document rushDefence_doc  = Jsoup.connect(strings[2]).get();
                tempElement = rushDefence_doc.select("tbody").select("tr");


                Ranked = "";
                for (int i = 0; i < 33; i++) {
                    String ElementString = tempElement.get(i).text();

                    if(ElementString.contains(mTeam)) {
                        S = new Scanner(ElementString);
                        Ranked = S.next();

                        while(!S.hasNext(mTeam))
                            S.next();

                        break;
                    }
                }

                for(int i = 0; i < 8; i ++)
                    S.next();

                stats.add(S.next());
                stats.add(ordinal(Ranked));

            } catch (IOException e) {
                e.printStackTrace();
            }


            return stats;
        }


        @Override
        protected void onPostExecute(List<String> strings) {
            super.onPostExecute(strings);

//            String temp = "The defense are ranked " +  strings.get(1) + " overall "
//                    + "with " + strings.get(2) + " PASSING Yds/G (" + strings.get(3) + ") and "
//                    + strings.get(4) +" RUSHING Yds/G ("+ strings.get(5) + "). They Allow " + strings.get(0) + " points per game.";


            D_Passing_label.setText("Pass (" + strings.get(3) + ")");
            D_Rushing_label.setText("Rush (" + strings.get(5) + ")");

            D_ranked.setText("Defense Ranked " + strings.get(1));
            D_ranked.setTextColor(Color.parseColor("#19000A"));
            D_Passing.setText(strings.get(2) + " Yds/G");// (" + strings.get(3) + ")");
            D_Rushing.setText(strings.get(4) + " Yds/G");// (" + strings.get(5) + ")");
            D_Points.setText(strings.get(0) +" P/G");

        }

        public  String ordinal(String input) {
            int i = Integer.parseInt(input);
            String[] sufixes = new String[] { "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th" };
            switch (i % 100) {
                case 11:
                case 12:
                case 13:
                    return i + "th";
                default:
                    return i + sufixes[i % 10];
            }
        }
    }

    /**
     * AsyncTask to get the teams Offense stats and display it on offense_textview.
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
