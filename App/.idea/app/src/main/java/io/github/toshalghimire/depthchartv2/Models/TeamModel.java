package io.github.toshalghimire.depthchartv2.Models;

import android.util.Log;

import io.github.toshalghimire.depthchartv2.R;


/**
 * Team model class for the Team list view. This class has some useful static methods that can be used in other classes.
 */
public class TeamModel {
    private String teamCode;
    private String teamCity;
    private String teamName;
    private String teamWebsite;
    private String teamFullName;
    private String teamBackgroundColor;
    private int logo;


    /**
     * Constructor for the team model
     * @param teamCode Team code (IE DEN)
     * @param teamCity Team city
     * @param teamName Team name
     * @param mWebsite Teams website
     * @param teamBackgroundColor Background color of team
     */
    public TeamModel(String teamCode, String teamCity, String teamName, String mWebsite, String teamBackgroundColor) {
        this.teamCode = teamCode;
        this.teamCity = teamCity;
        this.teamName = teamName;
        this.teamWebsite = mWebsite;
        this.teamBackgroundColor = teamBackgroundColor;
        this.logo = getLogoRes(teamName);

        this.teamFullName = teamCity + " " + teamName;
    }


    public int getLogo() { return logo; }

    public String getTeamCode() {
        return teamCode;
    }

    public String getTeamCity() {
        return teamCity;
    }

    public String getTeamName() { return teamName; }

    public String getTeamWebsite() {
        return teamWebsite;
    }

    public String getTeamFullName() {
        return teamFullName;
    }

    public String getTeamBackgroundColor() {
        return teamBackgroundColor;
    }

    public static int getLogoRes(String teamName){

        if(teamName.equals("49ers")){
            return R.drawable.ers;
        }
        if(teamName.equals("Cardinals")){
            return R.drawable.cardinals;
        }
        if(teamName.equals("Falcons")){
            return R.drawable.falcons;
        }
        if(teamName.equals("Ravens")){
            return R.drawable.ravens;
        }
        if(teamName.equals("Bills")){
            return R.drawable.bills;
        }
        if(teamName.equals("Panthers")){
            return R.drawable.panthers;
        }
        if(teamName.equals("Bears")){
            return R.drawable.bears;
        }
        if(teamName.equals("Bengals")){
            return R.drawable.bengals;
        }
        if(teamName.equals("Browns")){
            return R.drawable.browns;
        }
        if(teamName.equals("Cowboys")){
            return R.drawable.cowboys;
        }
        if(teamName.equals("Broncos")){
            return R.drawable.broncos;
        }
        if(teamName.equals("Lions")){
            return R.drawable.lions;
        }
        if(teamName.equals("Packers")){
            return R.drawable.packers;
        }
        if(teamName.equals("Texans")){
            return R.drawable.texans;
        }
        if(teamName.equals("Colts")){
            return R.drawable.colts;
        }
        if(teamName.equals("Jaguars")){
            return R.drawable.jaguars;
        }
        if(teamName.equals("Chiefs")){
            return R.drawable.chiefs;
        }
        if(teamName.equals("Chargers")){
            return R.drawable.chargers;
        }
        if(teamName.equals("Rams")){
            return R.drawable.rams;
        }
        if(teamName.equals("Dolphins")){
            return R.drawable.dolphins;
        }
        if(teamName.equals("Vikings")){
            return R.drawable.vikings;
        }
        if(teamName.equals("Patriots")){
            return R.drawable.patriots;
        }
        if(teamName.equals("Saints")){
            return R.drawable.saints;
        }
        if(teamName.equals("Giants")) {
            return R.drawable.giants;
        }
        if(teamName.equals("Jets")){
            return R.drawable.jets;
        }
        if(teamName.equals("Raiders")){
            return R.drawable.raiders;
        }
        if(teamName.equals("Eagles")){
            return R.drawable.eagles;
        }
        if(teamName.equals("Steelers")){
            return R.drawable.steelers;
        }
        if(teamName.equals("Seahawks")){
            return R.drawable.seahawks;
        }
        if(teamName.equals("Buccaneers")){
            return R.drawable.buccaneers;
        }
        if(teamName.equals("Titans")){
            return R.drawable.titans;
        }
        if(teamName.equals("Redskins")){
            return R.drawable.redskins;
        }
        return 0;
    }
    public static int getColorRes(String teamName){

        Log.d("COLOR RES", "getColorRes: " + teamName);

        if(teamName == null || teamName.equals("FA"))
            return R.color.color_Raiders;

        if(teamName.equals("49ers")){
            return R.color.color_49ers;
        }
        if(teamName.equals("Cardinals")){
            return R.color.color_Cardinals;
        }
        if(teamName.equals("Falcons")){
            return R.color.color_Falcons;
        }
        if(teamName.equals("Ravens")){
            return R.color.color_Ravens;
        }
        if(teamName.equals("Bills")){
            return R.color.color_Bills;
        }
        if(teamName.equals("Panthers")){
            return R.color.color_Panthers;
        }
        if(teamName.equals("Bears")){
            return R.color.color_Bears;
        }
        if(teamName.equals("Bengals")){
            return R.color.color_Bengals;
        }
        if(teamName.equals("Browns")){
            return R.color.color_Browns;
        }
        if(teamName.equals("Cowboys")){
            return R.color.color_Cowboys;
        }
        if(teamName.equals("Broncos")){
            return R.color.color_Broncos;
        }
        if(teamName.equals("Lions")){
            return R.color.color_Lions;
        }
        if(teamName.equals("Packers")){
            return R.color.color_Packers;
        }
        if(teamName.equals("Texans")){
            return R.color.color_Texans;
        }
        if(teamName.equals("Colts")){
            return R.color.color_Colts;
        }
        if(teamName.equals("Jaguars")){
            return R.color.color_Jaguars;
        }
        if(teamName.equals("Chiefs")){
            return R.color.color_Chiefs;
        }
        if(teamName.equals("Chargers")){
            return R.color.color_Chargers;
        }
        if(teamName.equals("Rams")){
            return R.color.color_Rams;
        }
        if(teamName.equals("Dolphins")){
            return R.color.color_Dolphins;
        }
        if(teamName.equals("Vikings")){
            return R.color.color_Vikings;
        }
        if(teamName.equals("Patriots")){
            return R.color.color_Patriots;
        }
        if(teamName.equals("Saints")){
            return R.color.color_Saints;
        }
        if(teamName.equals("Giants")) {
            return R.color.color_Giants;
        }
        if(teamName.equals("Jets")){
            return R.color.color_Jets;
        }
        if(teamName.equals("Raiders")){
            return R.color.color_Raiders;
        }
        if(teamName.equals("Eagles")){
            return R.color.color_Eagles;
        }
        if(teamName.equals("Steelers")){
            return R.color.color_Steelers;
        }
        if(teamName.equals("Seahawks")){
            return R.color.color_Seahawks;
        }
        if(teamName.equals("Buccaneers")){
            return R.color.color_Buccaneers;
        }
        if(teamName.equals("Titans")){
            return R.color.color_Titans;
        }
        if(teamName.equals("Redskins")){
            return R.color.color_Redskins;
        }
        return 0;
    }

    public static int getColorRes(String teamName,Boolean code){
        if(code == true){
            teamName = CodeToName(teamName);
        }
        if(teamName.equals("49ers")){
            return R.color.color_49ers;
        }
        if(teamName.equals("Cardinals")){
            return R.color.color_Cardinals;
        }
        if(teamName.equals("Falcons")){
            return R.color.color_Falcons;
        }
        if(teamName.equals("Ravens")){
            return R.color.color_Ravens;
        }
        if(teamName.equals("Bills")){
            return R.color.color_Bills;
        }
        if(teamName.equals("Panthers")){
            return R.color.color_Panthers;
        }
        if(teamName.equals("Bears")){
            return R.color.color_Bears;
        }
        if(teamName.equals("Bengals")){
            return R.color.color_Bengals;
        }
        if(teamName.equals("Browns")){
            return R.color.color_Browns;
        }
        if(teamName.equals("Cowboys")){
            return R.color.color_Cowboys;
        }
        if(teamName.equals("Broncos")){
            return R.color.color_Broncos;
        }
        if(teamName.equals("Lions")){
            return R.color.color_Lions;
        }
        if(teamName.equals("Packers")){
            return R.color.color_Packers;
        }
        if(teamName.equals("Texans")){
            return R.color.color_Texans;
        }
        if(teamName.equals("Colts")){
            return R.color.color_Colts;
        }
        if(teamName.equals("Jaguars")){
            return R.color.color_Jaguars;
        }
        if(teamName.equals("Chiefs")){
            return R.color.color_Chiefs;
        }
        if(teamName.equals("Chargers")){
            return R.color.color_Chargers;
        }
        if(teamName.equals("Rams")){
            return R.color.color_Rams;
        }
        if(teamName.equals("Dolphins")){
            return R.color.color_Dolphins;
        }
        if(teamName.equals("Vikings")){
            return R.color.color_Vikings;
        }
        if(teamName.equals("Patriots")){
            return R.color.color_Patriots;
        }
        if(teamName.equals("Saints")){
            return R.color.color_Saints;
        }
        if(teamName.equals("Giants")) {
            return R.color.color_Giants;
        }
        if(teamName.equals("Jets")){
            return R.color.color_Jets;
        }
        if(teamName.equals("Raiders")){
            return R.color.color_Raiders;
        }
        if(teamName.equals("Eagles")){
            return R.color.color_Eagles;
        }
        if(teamName.equals("Steelers")){
            return R.color.color_Steelers;
        }
        if(teamName.equals("Seahawks")){
            return R.color.color_Seahawks;
        }
        if(teamName.equals("Buccaneers")){
            return R.color.color_Buccaneers;
        }
        if(teamName.equals("Titans")){
            return R.color.color_Titans;
        }
        if(teamName.equals("Redskins")){
            return R.color.color_Redskins;
        }
        return 0;
    }

    public static String  CodeToName(String teamName){

        if(teamName.equals("ARZ")) {return "Cardinals";}
        if(teamName.equals("ARI")) {return "Cardinals";}
        if(teamName.equals("ATL")) {return "Falcons";}
        if(teamName.equals("BAL")) {return "Ravens";}
        if(teamName.equals("BUF")) {return "Bills";}
        if(teamName.equals("CAR")) {return "Panthers";}
        if(teamName.equals("CHI")) {return "Bears";}
        if(teamName.equals("CIN")) {return "Bengals";}
        if(teamName.equals("CLE")) {return "Browns";}
        if(teamName.equals("DAL")) {return "Cowboys";}
        if(teamName.equals("DEN")) {return "Broncos";}
        if(teamName.equals("DET")) {return "Lions";}
        if(teamName.equals("GB")) {return "Packers";}
        if(teamName.equals("HOU")) {return "Texans";}
        if(teamName.equals("IND")) {return "Colts";}
        if(teamName.equals("JAC")) {return "Jaguars";}
        if(teamName.equals("KC")) {return "Chiefs";}
        if(teamName.equals("LAC")) {return "Chargers";}
        if(teamName.equals("LAR")) {return "Rams";}
        if(teamName.equals("MIA")) {return "Dolphins";}
        if(teamName.equals("MIN")) {return "Vikings";}
        if(teamName.equals("NE")) {return "Patriots";}
        if(teamName.equals("NO")) {return "Saints";}
        if(teamName.equals("NYG")) {return "Giants";}
        if(teamName.equals("NYJ")) {return "Jets";}
        if(teamName.equals("OAK")) {return "Raiders";}
        if(teamName.equals("PHI")) {return "Eagles";}
        if(teamName.equals("PIT")) {return "Steelers";}
        if(teamName.equals("SF")) {return "49ers";}
        if(teamName.equals("SEA")) {return "Seahawks";}
        if(teamName.equals("TB")) {return "Buccaneers";}
        if(teamName.equals("TEN")) {return "Titans";}
        if(teamName.equals("WAS")) {return "Redskins";}
        if(teamName.equals("FA")) {return "FA";}

        return null;
    }

}
