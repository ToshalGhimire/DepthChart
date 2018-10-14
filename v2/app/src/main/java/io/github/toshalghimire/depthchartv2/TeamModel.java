package io.github.toshalghimire.depthchartv2;

public class TeamModel {
    private String mCode;
    private String mCity;
    private String mTeam;
    private String mWebsite;
    private String mFullName;
    private String mBackgroundColor;
    private int logo;



    public TeamModel(String mCode, String mCity, String mTeam, String mWebsite, String mBackgroundColor) {
        this.mCode = mCode;
        this.mCity = mCity;
        this.mTeam = mTeam;
        this.mWebsite = mWebsite;
        this.mBackgroundColor = mBackgroundColor;
        this.logo = getLogoRes(mTeam);

        this.mFullName = mCity + " " + mTeam;
    }


    public int getLogo() { return logo; }

    public String getmCode() {
        return mCode;
    }

    public String getmCity() {
        return mCity;
    }

    public String getmTeam() { return mTeam; }

    public String getmWebsite() {
        return mWebsite;
    }

    public String getmFullName() {
        return mFullName;
    }

    public String getmBackgroundColor() {
        return mBackgroundColor;
    }

    public int getLogoRes(String teamName){

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
}
