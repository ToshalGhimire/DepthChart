package io.github.toshalghimire.depthchartv2.Fragments.NFL.TeamFragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.github.toshalghimire.depthchartv2.Activity.TeamPageActivity;
import io.github.toshalghimire.depthchartv2.Models.TeamModel;
import io.github.toshalghimire.depthchartv2.R;


/**
 * Adapter class that takes the data and links it to the views in the news layout and populates a RecycleView for NFL Teams.
 */
public class TeamListAdapter extends RecyclerView.Adapter<TeamListAdapter.ViewHolder> {

    List<TeamModel> teamList;

    /**
     * Constructor that takes a list of TeamModels
     * @param data TeamModel object List
     */
    public TeamListAdapter(List<TeamModel> data){

        teamList = data;
    }

    /**
     * Sets the view layout to inflate the Team data
     * @param parent contex news fragment
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.team_list_layout,null);

        return new ViewHolder(view,parent.getContext());
    }

    /**
     * Sets the items on the layout with the data
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TeamModel team = teamList.get(position);

        holder.textViewCity.setText(team.getTeamCity());
        holder.textViewTeam.setText(team.getTeamName());
        holder.imageView.setImageResource(team.getLogo());

        holder.setLogo(team.getLogo());
        holder.setScraperLink(team.getTeamWebsite());
        holder.setTeamCity(team.getTeamCity());
        holder.setTeamName(team.getTeamName());
        holder.setTeamColor(team.getTeamBackgroundColor());

        int color = Color.parseColor(team.getTeamBackgroundColor());
        holder.cv.setCardBackgroundColor(color);
    }


    /**
     * Number of list items
     * @return size of list
     */
    @Override
    public int getItemCount() {
        return teamList.size();
    }

    /**
     * Class that holds the data for each of the RecycleView item.
     * Handles the Logic for opening the the team page based on the team that was clicked.
     */
    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textViewCity, textViewTeam;
        CardView cv;

        String scraperLink;
        String teamName;
        String teamCity;
        String teamColor;
        int logo;
        Context contexVH;


        /**
         *
         * @return logo resource of the team
         */
        public int getLogo(){
            return logo;
        }

        /**
         * Sets logo resource for the team
         * @param resource
         */
        public void setLogo(int resource){
            logo = resource;
        }

        /**
         *
         * @return Team color of current team
         */
        public String getTeamColor() {
            return teamColor;
        }

        /**
         * Sets team color of current team
         * @param teamColor
         */
        public void setTeamColor(String teamColor) {
            this.teamColor = teamColor;
        }

        /**
         *
         * @return The link to the teams website with the depth chart url appended to it.
         */
        public String getScraperLink() {
            return scraperLink + "team/depth-chart";
        }

        /**
         * Set the team website of the team.
         * @param scraperLink
         */
        public void setScraperLink(String scraperLink) {
            this.scraperLink = scraperLink;
        }

        /**
         *
         * @return The team name
         */
        public String getTeamName() {
            return teamName;
        }

        /**
         * Sets the team name
         * @param teamName
         */
        public void setTeamName(String teamName) {
            this.teamName = teamName;
        }

        /**
         *
         * @return The city which the team is located in
         */
        public String getTeamCity() {
            return teamCity;
        }

        /**
         * Set the location city of the team
         * @param teamCity
         */
        public void setTeamCity(String teamCity) {
            this.teamCity = teamCity;
        }




        /**
         * Finds views and links it to the layout, Handles the logic for opening team page on tap.
         * @param itemView
         * @param contex
         */
        public ViewHolder(View itemView,Context contex) {
            super(itemView);

            this.scraperLink = "";
            this.contexVH = contex;
            imageView = itemView.findViewById(R.id.logo_imageView);
            textViewCity = itemView.findViewById(R.id.city_textview);
            textViewTeam = itemView.findViewById(R.id.team_textview);
            cv = itemView.findViewById(R.id.teamCardview);

            cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("onClick","Started OnClick");

                    Intent i = new Intent(contexVH,TeamPageActivity.class);
                    i.putExtra("ScraperLink",getScraperLink());
                    i.putExtra("teamName",getTeamName());
                    i.putExtra("teamCity",getTeamCity());
                    i.putExtra("teamColor",getTeamColor());
                    i.putExtra("teamLogo",getLogo());


                    Log.d("onClick","Started New activiy");
                    contexVH.startActivity(i);
                }

            });

        }



    }

    /**
     * Helper function used to update the List based on what the user types in search box.
     * @param newList
     */
    public void setFilter(ArrayList<TeamModel> newList){
        teamList = new ArrayList<>();
        teamList.addAll(newList);
        notifyDataSetChanged();
    }

}
