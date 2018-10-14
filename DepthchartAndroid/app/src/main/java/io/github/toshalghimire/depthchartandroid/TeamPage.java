package io.github.toshalghimire.depthchartandroid;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import NFL.Position;

public class TeamPage extends AppCompatActivity {

    private String mCity;
    private String mTeam;

    private ArrayList<Position> mDepthChart;

    private TextView fineprint;
    private  TextView teamText;
    private  TextView offenseText;
    private  TextView defenseText;

    private String teamString,offenseString,defenseString;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_page);

        mDepthChart = new ArrayList<Position>();
        fineprint = (TextView)findViewById(R.id.fineprint_textview);
        teamText = (TextView)findViewById(R.id.team_textview);
//        offenseText = (TextView)findViewById(R.id.offenseStats_textview);
//        defenseText = (TextView)findViewById(R.id.defenseStats_textview);


        // Getting data from Intent
        Intent intent = getIntent();
        String url = intent.getStringExtra("ScraperLink");
        mTeam = intent.getStringExtra("teamName");
        mCity = intent.getStringExtra("teamCity");


        String temp = "Data from the NFL and " + mTeam + " offical website.";
        fineprint.setText(temp);
        fineprint.setTextColor(Color.parseColor("#cc3300"));

//        String totalDefenseURL = "http://www.nfl.com/stats/categorystats?tabSeq=2&defensiveStatisticCategory=GAME_STATS&conference=ALL&role=OPP&season=2018&seasonType=REG&d-447263-s=TOTAL_YARDS_GAME_AVG&d-447263-o=1&d-447263-n=1";
//        String passDefenseURL = "http://www.nfl.com/stats/categorystats?tabSeq=2&defensiveStatisticCategory=TEAM_PASSING&conference=ALL&role=OPP&season=2018&seasonType=REG&d-447263-s=PASSING_NET_YARDS_GAME_AVG&d-447263-o=1&d-447263-n=1";
//        String rushDefenseURL = "http://www.nfl.com/stats/categorystats?tabSeq=2&defensiveStatisticCategory=RUSHING&conference=ALL&role=OPP&season=2018&seasonType=REG&d-447263-s=RUSHING_YARDS_PER_GAME_AVG&d-447263-o=1&d-447263-n=1";

        String[] Defense_URL = {"http://www.nfl.com/stats/categorystats?tabSeq=2&defensiveStatisticCategory=GAME_STATS&conference=ALL&role=OPP&season=2018&seasonType=REG&d-447263-s=TOTAL_YARDS_GAME_AVG&d-447263-o=1&d-447263-n=1",
        "http://www.nfl.com/stats/categorystats?tabSeq=2&defensiveStatisticCategory=TEAM_PASSING&conference=ALL&role=OPP&season=2018&seasonType=REG&d-447263-s=PASSING_NET_YARDS_GAME_AVG&d-447263-o=1&d-447263-n=1",
        "http://www.nfl.com/stats/categorystats?tabSeq=2&defensiveStatisticCategory=RUSHING&conference=ALL&role=OPP&season=2018&seasonType=REG&d-447263-s=RUSHING_YARDS_PER_GAME_AVG&d-447263-o=1&d-447263-n=1",
            };
        new getDefense().execute(Defense_URL[0],Defense_URL[1],Defense_URL[2]);

//        String totalOffenseURL = "http://www.nfl.com/stats/categorystats?tabSeq=2&offensiveStatisticCategory=GAME_STATS&conference=ALL&role=TM&season=2018&seasonType=REG&d-447263-s=TOTAL_YARDS_GAME_AVG&d-447263-o=2&d-447263-n=1";
//        String passOffenseURL = "http://www.nfl.com/stats/categorystats?tabSeq=2&offensiveStatisticCategory=TEAM_PASSING&conference=ALL&role=TM&season=2018&seasonType=REG&d-447263-s=PASSING_NET_YARDS_GAME_AVG&d-447263-o=2&d-447263-n=1";
//        String rushOffenseURL = "http://www.nfl.com/stats/categorystats?tabSeq=2&offensiveStatisticCategory=RUSHING&conference=ALL&role=TM&season=2018&seasonType=REG&d-447263-s=RUSHING_YARDS_PER_GAME_AVG&d-447263-o=2&d-447263-n=1";

        String[] Offense_URL = {"http://www.nfl.com/stats/categorystats?tabSeq=2&offensiveStatisticCategory=GAME_STATS&conference=ALL&role=TM&season=2018&seasonType=REG&d-447263-s=TOTAL_YARDS_GAME_AVG&d-447263-o=2&d-447263-n=1",
                "http://www.nfl.com/stats/categorystats?tabSeq=2&offensiveStatisticCategory=TEAM_PASSING&conference=ALL&role=TM&season=2018&seasonType=REG&d-447263-s=PASSING_NET_YARDS_GAME_AVG&d-447263-o=2&d-447263-n=1",
                "http://www.nfl.com/stats/categorystats?tabSeq=2&offensiveStatisticCategory=RUSHING&conference=ALL&role=TM&season=2018&seasonType=REG&d-447263-s=RUSHING_YARDS_PER_GAME_AVG&d-447263-o=2&d-447263-n=1",
        };

        new getOffense().execute(Offense_URL[0],Offense_URL[1],Offense_URL[2]);



        new getTeam().execute(url);


        //teamText.setText(teamString);
        //offenseText.setText(offenseString);
        //defenseText.setText(defenseString);

    }

    /**
     * AsyncTask to get the team depthchart and display it on position_textview
     * form the official team website.
     */
    private class getTeam extends AsyncTask<String, Integer, Void> {

        private ProgressBar pb;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pb = (ProgressBar)findViewById(R.id.progressBar);
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
            TextView positionText = (TextView)findViewById(R.id.teamPage_tv);

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

    /**
     * AsyncTask to get the teams Defense stats and display it on defense_textview.
     */
    public class getDefense extends AsyncTask<String,Void,List<String>>{
        private List<String> stats = null;
        private Scanner S = null;
        private Elements tempElement = null;


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

            TextView D_ranked = (TextView)findViewById(R.id.D_ranked);
            TextView D_Points = (TextView)findViewById(R.id.D_Points);
            TextView D_Passing = (TextView)findViewById(R.id.D_Passing);
            TextView D_Rushing = (TextView)findViewById(R.id.D_Rushing);

//            String temp = "The defense are ranked " +  strings.get(1) + " overall "
//                    + "with " + strings.get(2) + " PASSING Yds/G (" + strings.get(3) + ") and "
//                    + strings.get(4) +" RUSHING Yds/G ("+ strings.get(5) + "). They Allow " + strings.get(0) + " points per game.";


            D_ranked.setText(strings.get(1));
            D_Passing.setText(strings.get(2) + " Yds/G (" + strings.get(3) + ")");
            D_Rushing.setText(strings.get(4) + " Yds/G (" + strings.get(5) + ")");
            D_Points.setText("Allow " + strings.get(0));

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
    public class getOffense extends AsyncTask<String,Void,List<String>>{
        private List<String> stats = null;
        private Scanner S = null;
        private Elements tempElement = null;

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

            TextView O_ranked = (TextView)findViewById(R.id.O_ranked);
            TextView O_Points = (TextView)findViewById(R.id.O_Points);
            TextView O_Passing = (TextView)findViewById(R.id.O_Passing);
            TextView O_Rushing = (TextView)findViewById(R.id.O_Rushing);

//            String temp = "The defense are ranked " +  strings.get(1) + " overall "
//                    + "with " + strings.get(2) + " PASSING Yds/G (" + strings.get(3) + ") and "
//                    + strings.get(4) +" RUSHING Yds/G ("+ strings.get(5) + "). They Allow " + strings.get(0) + " points per game.";


            O_ranked.setText(strings.get(1));
            O_Passing.setText(strings.get(2) + " Yds/G (" + strings.get(3) + ")");
            O_Rushing.setText(strings.get(4) + " Yds/G (" + strings.get(5) + ")");
            O_Points.setText("Average " + strings.get(0));


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


}
