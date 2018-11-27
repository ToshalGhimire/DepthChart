package io.github.toshalghimire.depthchartv2.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

/**
 * This activity is to test activities before making a separate class for them. Used to make sure I have everything correct.
 */
public class testActivity extends AppCompatActivity {

    private String mCity;
    private String mTeam;
    private int mteamColor;


    private TextView testTextview;

    private String teamString,offenseString,defenseString;



    /**
     * Method used for the layout and methods connection
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_page);

        testTextview = (TextView)findViewById(R.id.testTextview);

        // Getting data from Intent
        Intent intent = getIntent();
        String url = intent.getStringExtra("ScraperLink");
        mTeam = intent.getStringExtra("teamName");
        mCity = intent.getStringExtra("teamCity");
        mteamColor = Color.parseColor(intent.getStringExtra("teamColor")); // not used
        setTitle("The " + mTeam);


        // Singleton object and seting offense
        DataSingleton obj = DataSingleton.getInstance();
        List<String> strings = obj.getOffenseStats(mTeam);


        String temp = "The defense are ranked " +  strings.get(1) + " overall "
                + "with " + strings.get(2) + " PASSING Yds/G (" + strings.get(3) + ") and "
                + strings.get(4) +" RUSHING Yds/G ("+ strings.get(5) + "). They Allow " + strings.get(0) + " points per game.";



        testTextview.setText(temp);
    }




}
