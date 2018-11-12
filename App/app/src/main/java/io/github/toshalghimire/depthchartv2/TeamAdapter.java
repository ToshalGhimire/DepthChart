package io.github.toshalghimire.depthchartv2;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
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

public class TeamAdapter extends  RecyclerView.Adapter<TeamAdapter.TeamViewHolder> {

    private Context mContex;
    Resources res = null;
    private List<TeamModel> mTeamList;


    public TeamAdapter(Context mContex, List<TeamModel> mTeamList) {
        this.mContex = mContex;
        this.mTeamList = mTeamList;
        res = mContex.getResources();
    }

    @NonNull
    @Override
    public TeamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContex);
        View view = inflater.inflate(R.layout.team_list_layout,null);

        return new TeamViewHolder(view,mContex);
    }

    @Override
    public void onBindViewHolder(@NonNull TeamViewHolder holder, int position) {
        TeamModel team = mTeamList.get(position);

        holder.textViewCity.setText(team.getTeamCity());
        holder.textViewTeam.setText(team.getTeamName());
        holder.imageView.setImageResource(team.getLogo());

        holder.setScraperLink(team.getTeamWebsite());
        holder.setTeamCity(team.getTeamCity());
        holder.setTeamName(team.getTeamName());
        holder.setTeamColor(team.getTeamBackgroundColor());

        int color = Color.parseColor(team.getTeamBackgroundColor());
        holder.cv.setCardBackgroundColor(color);
    }

    @Override
    public int getItemCount() {
        return mTeamList.size();
    }


    public class TeamViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textViewCity, textViewTeam;
        CardView cv;

        String scraperLink;
        String teamName;
        String teamCity;
        String teamColor;

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
        public TeamViewHolder(View itemView,Context contex) {
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

                    Log.d("onClick","Started New activiy");
                    contexVH.startActivity(i);
                }
            });
        }





    }

    public void setFilter(ArrayList<TeamModel> newList){
        mTeamList = new ArrayList<>();
        mTeamList.addAll(newList);
        notifyDataSetChanged();
    }

}
