package io.github.toshalghimire.depthchartv2.Models;

import android.util.Log;


/**
 * Model class to store the data for the Injury updates
 */
public class InjuryModel {
    private String title;
    private String shortDescription;
    private String longDescription;
    private String teamName;
    private int teamLogo;
    private int PlayerImage;
    private int teamBackgroundColor;

    @Override
    public String toString() {
        return "InjuryModel{" +
                "title='" + title + '\'' +
                ", shortDescription='" + shortDescription + '\'' +
                ", teamName='" + teamName + '\'' +
                '}';
    }

    /**
     * Constructor for model
     * @param title title for injury
     * @param shortDescription short info
     * @param longDescription long info
     * @param teamName team name
     */
    public InjuryModel(String title, String shortDescription, String longDescription, String teamName) {
        this.title = title;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.teamName = TeamModel.CodeToName(teamName);
        this.teamLogo = TeamModel.getLogoRes(teamName);

        //PlayerImage = playerImage;
        this.teamBackgroundColor = TeamModel.getColorRes(teamName);
        Log.d("news", "InjuryModel: Created: " + title);
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }
    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public int getTeamLogo() {
        return teamLogo;
    }

    public void setTeamLogo(int teamLogo) {
        this.teamLogo = teamLogo;
    }

    public int getPlayerImage() {
        return PlayerImage;
    }

    public void setPlayerImage(int playerImage) {
        PlayerImage = playerImage;
    }

    public int getTeamBackgroundColor() {
        return teamBackgroundColor;
    }

    public void setTeamBackgroundColor(int teamBackgroundColor) {
        this.teamBackgroundColor = teamBackgroundColor;
    }





}
