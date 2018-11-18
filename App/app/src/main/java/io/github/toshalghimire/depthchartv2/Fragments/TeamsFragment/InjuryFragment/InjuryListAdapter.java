package io.github.toshalghimire.depthchartv2.Fragments.TeamsFragment.InjuryFragment;

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

import io.github.toshalghimire.depthchartv2.Activity.TeamPage;
import io.github.toshalghimire.depthchartv2.Activity.teamPage_V02Activity;
import io.github.toshalghimire.depthchartv2.Models.TeamModel;
import io.github.toshalghimire.depthchartv2.R;

public class InjuryListAdapter extends RecyclerView.Adapter<InjuryListAdapter.ViewHolder> {

    List<TeamModel> teamList;
    public InjuryListAdapter(List<TeamModel> data){

        teamList = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.team_list_layout,null);

        return new ViewHolder(view,parent.getContext());
    }

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



    @Override
    public int getItemCount() {
        return teamList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textViewCity, textViewTeam;
        CardView cv;

        String scraperLink;
        String teamName;
        String teamCity;
        String teamColor;
        int logo;

        public int getLogo(){
            return logo;
        }
        public void setLogo(int resource){
            logo = resource;
        }

        public String getTeamColor() {
            return teamColor;
        }
        public void setTeamColor(String teamColor) {
            this.teamColor = teamColor;
        }


        public String getScraperLink() {
            return scraperLink + "team/depth-chart";
        }
        public void setScraperLink(String scraperLink) {
            this.scraperLink = scraperLink;
        }

        public String getTeamName() {
            return teamName;
        }
        public void setTeamName(String teamName) {
            this.teamName = teamName;
        }

        public String getTeamCity() {
            return teamCity;
        }
        public void setTeamCity(String teamCity) {
            this.teamCity = teamCity;
        }




        Context contexVH;
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

                    Intent i = new Intent(contexVH,TeamPage.class);
                    i.putExtra("ScraperLink",getScraperLink());
                    i.putExtra("teamName",getTeamName());
                    i.putExtra("teamCity",getTeamCity());
                    i.putExtra("teamColor",getTeamColor());

//                    Intent i = new Intent(contexVH,teamPage_V02Activity.class);
//                    i.putExtra("ScraperLink",getScraperLink());
//                    i.putExtra("teamName",getTeamName());
//                    i.putExtra("teamCity",getTeamCity());
//                    i.putExtra("teamColor",getTeamColor());
//                    i.putExtra("teamLogo",getLogo());


                    Log.d("onClick","Started New activiy");
                    contexVH.startActivity(i);
                }

            });

        }



    }

    public void setFilter(ArrayList<TeamModel> newList){
        teamList = new ArrayList<>();
        teamList.addAll(newList);
        notifyDataSetChanged();
    }

}
