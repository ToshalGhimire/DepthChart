package io.github.toshalghimire.depthchartandroid;

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

    public String getmTeam() {
        return mTeam;
    }

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

        if(teamName == "49ers"){
            return R.drawable.fortyniners;
        }
        if(teamName == "Cardinals"){
            return R.drawable.cardinals;
        }
        if(teamName == "Falcons"){
            return R.drawable.falcons;
        }
        if(teamName == "Ravens"){
            return R.drawable.ravens;
        }
        if(teamName == "Bills"){
            return R.drawable.bills;
        }
        if(teamName == "Panthers"){
            return R.drawable.panthers;
        }
        if(teamName == "Bears"){
            return R.drawable.bears;
        }
        if(teamName == "Bengals"){
            return R.drawable.bengals;
        }
        if(teamName == "Browns"){
            return R.drawable.browns;
        }
        if(teamName == "Cowboys"){
            return R.drawable.cowboys;
        }
        if(teamName == "Broncos"){
            return R.drawable.broncos;
        }
        if(teamName == "Lions"){
            return R.drawable.lions;
        }
        if(teamName == "Packers"){
            return R.drawable.packers;
        }
        if(teamName == "Texans"){
            return R.drawable.texans;
        }
        if(teamName == "Colts"){
            return R.drawable.colts;
        }
        if(teamName == "Jaguars"){
            return R.drawable.jaguars;
        }
        if(teamName == "Chiefs"){
            return R.drawable.chiefs;
        }
        if(teamName == "Chargers"){
            return R.drawable.chargers;
        }
        if(teamName == "Rams"){
            return R.drawable.rams;
        }
        if(teamName == "Dolphins"){
            return R.drawable.dolphins;
        }
        if(teamName == "Vikings"){
            return R.drawable.vikings;
        }
        if(teamName == "Patriots"){
            return R.drawable.patriots;
        }
        if(teamName == "Saints"){
            return R.drawable.saints;
        }
        if(teamName == "Giants") {
            return R.drawable.giants;
        }
        if(teamName == "Jets"){
            return R.drawable.jets;
        }
        if(teamName == "Raiders"){
            return R.drawable.raiders;
        }
        if(teamName == "Eagles"){
            return R.drawable.eagles;
        }
        if(teamName == "Steelers"){
            return R.drawable.steelers;
        }
        if(teamName == "Seahawks"){
            return R.drawable.seahawks;
        }
        if(teamName == "Buccaneers"){
            return R.drawable.buccaneers;
        }
        if(teamName == "Titans"){
            return R.drawable.titans;
        }
        if(teamName == "Redskins"){
            return R.drawable.redskins;
        }
        return 0;
    }
}
