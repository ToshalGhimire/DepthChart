package io.github.toshalghimire.depthchartv2.Models;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DataSingleton {
    // Offense
    private Elements TotalOffenseElement = null;
    private Elements PassingOffenseElement = null;
    private Elements RushingOffenseElement = null;


    // Defense
    private Elements TotalDefenseElement = null;
    private Elements PassingDefenseElement = null;
    private Elements RushingDefenseElement = null;

    // Singleton instance
    private static DataSingleton instance;


    private DataSingleton(){
        String[] Offense_URL = {"http://www.nfl.com/stats/categorystats?tabSeq=2&offensiveStatisticCategory=GAME_STATS&conference=ALL&role=TM&season=2018&seasonType=REG&d-447263-s=TOTAL_YARDS_GAME_AVG&d-447263-o=2&d-447263-n=1",
                "http://www.nfl.com/stats/categorystats?tabSeq=2&offensiveStatisticCategory=TEAM_PASSING&conference=ALL&role=TM&season=2018&seasonType=REG&d-447263-s=PASSING_NET_YARDS_GAME_AVG&d-447263-o=2&d-447263-n=1",
                "http://www.nfl.com/stats/categorystats?tabSeq=2&offensiveStatisticCategory=RUSHING&conference=ALL&role=TM&season=2018&seasonType=REG&d-447263-s=RUSHING_YARDS_PER_GAME_AVG&d-447263-o=2&d-447263-n=1",
        };
        new getOffense().execute(Offense_URL[0],Offense_URL[1],Offense_URL[2]);

        String[] Defense_URL = {"http://www.nfl.com/stats/categorystats?tabSeq=2&defensiveStatisticCategory=GAME_STATS&conference=ALL&role=OPP&season=2018&seasonType=REG&d-447263-s=TOTAL_YARDS_GAME_AVG&d-447263-o=1&d-447263-n=1",
                "http://www.nfl.com/stats/categorystats?tabSeq=2&defensiveStatisticCategory=TEAM_PASSING&conference=ALL&role=OPP&season=2018&seasonType=REG&d-447263-s=PASSING_NET_YARDS_GAME_AVG&d-447263-o=1&d-447263-n=1",
                "http://www.nfl.com/stats/categorystats?tabSeq=2&defensiveStatisticCategory=RUSHING&conference=ALL&role=OPP&season=2018&seasonType=REG&d-447263-s=RUSHING_YARDS_PER_GAME_AVG&d-447263-o=1&d-447263-n=1",
        };

        new getDefense().execute(Defense_URL[0],Defense_URL[1],Defense_URL[2]);

    }

    public static DataSingleton getInstance(){
        if(instance == null){
            instance = new DataSingleton();
        }

        return instance;

    }

    public List<String> getOffenseStats(String mTeam){
        List<String> stats = new ArrayList<>();

        Scanner S = null;
        String Ranked = "";
        for (int i = 0; i < 33; i++) {
            String ElementString = TotalOffenseElement.get(i).text();

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

        stats.add(S.next()); // Points Per Game (0)
        stats.add(ordinal(Ranked)); // Overall offense Ranked (1)

        // ========================================================
        // PASSing OFFENSE

        Ranked = "";
        for (int i = 0; i < 33; i++) {
            String ElementString = PassingOffenseElement.get(i).text();

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

        stats.add(S.next()); // Passing yrds per game (2)
        stats.add(ordinal(Ranked)); // passing ranked (3)


        // ========================================================
        // RUSHING OFFENSE

        Ranked = "";
        for (int i = 0; i < 33; i++) {
            String ElementString = RushingOffenseElement.get(i).text();

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

        stats.add(S.next()); // Rusing Yds/game (4)
        stats.add(ordinal(Ranked)); // Rushing ranked (5)

        return stats;
    }

    public List<String> getDefenseStats(String mTeam){
        List<String> stats = new ArrayList<>();

        Scanner S = null;
        String Ranked = "";
        for (int i = 0; i < 33; i++) {
            String ElementString = TotalDefenseElement.get(i).text();

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

        // ========================================================
        // PASSING Defense

        Ranked = "";
        for (int i = 0; i < 33; i++) {
            String ElementString = PassingDefenseElement.get(i).text();

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



        // ========================================================
        // RUSHING Defense

        Ranked = "";
        for (int i = 0; i < 33; i++) {
            String ElementString = RushingDefenseElement.get(i).text();

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

        return stats;
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

    /**
     * AsyncTask to get the teams Offense stats and display it on offense_textview.
     */
    public class getOffense extends AsyncTask<String,Void,Void> {
        private Scanner S = null;
        private Elements totalElem = null;
        private Elements passingElem = null;
        private Elements rushingElem = null;




        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected Void doInBackground(String... strings) {

            try {
                // TOTAL OFFENSE
                Document totalDefence_doc = Jsoup.connect(strings[0]).get();
                totalElem = totalDefence_doc.select("tbody").select("tr");


                // PASSing OFFENSE
                Document passDefence_doc = Jsoup.connect(strings[1]).get();
                passingElem = passDefence_doc.select("tbody").select("tr");


                // RUSHING OFFENSE
                Document rushDefence_doc  = Jsoup.connect(strings[2]).get();
                rushingElem = rushDefence_doc.select("tbody").select("tr");


            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            TotalOffenseElement = totalElem;
            PassingOffenseElement = passingElem;
            RushingOffenseElement = rushingElem;

            /*
            String temp = "The defense are ranked " +  strings.get(1) + " overall "
                    + "with " + strings.get(2) + " PASSING Yds/G (" + strings.get(3) + ") and "
                    + strings.get(4) +" RUSHING Yds/G ("+ strings.get(5) + "). They Allow " + strings.get(0) + " points per game.";
            */
        }

    }

    /**
     * AsyncTask to get the teams Defense stats and display it on defense_textview.
     */
    public class getDefense extends AsyncTask<String, Void, Void> {
        private List<String> stats = null;
        private Scanner S = null;
        private Elements tempElement = null;

        private Elements totalElem = null;
        private Elements passingElem = null;
        private Elements rushingElem = null;

        @Override
        protected Void doInBackground(String... strings) {


            try {
                // TOTAL DEFENSE
                Document totalDefence_doc = Jsoup.connect(strings[0]).get();
                totalElem = totalDefence_doc.select("tbody").select("tr");

                // PASSing DEFENSE
                Document passDefence_doc = Jsoup.connect(strings[1]).get();
                passingElem = passDefence_doc.select("tbody").select("tr");

                // RUSHING DEFENSE
                Document rushDefence_doc  = Jsoup.connect(strings[2]).get();
                rushingElem = rushDefence_doc.select("tbody").select("tr");


            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            TotalDefenseElement = totalElem;
            RushingDefenseElement = rushingElem;
            PassingDefenseElement = passingElem;
        }
    }

}
