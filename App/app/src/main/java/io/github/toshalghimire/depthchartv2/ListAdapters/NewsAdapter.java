package io.github.toshalghimire.depthchartv2.ListAdapters;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import io.github.toshalghimire.depthchartv2.Models.NewsModel;
import io.github.toshalghimire.depthchartv2.Models.TeamModel;
import io.github.toshalghimire.depthchartv2.R;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder>{

    private Context Contex;
    Resources res = null;
    private List<NewsModel> newsModel_list;

    public NewsAdapter(Context mContex, List<NewsModel> newsModel) {
        this.Contex = mContex;
        this.newsModel_list = newsModel;
        res = this.Contex.getResources();
    }

    @NonNull
    @Override
    public NewsAdapter.NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(Contex);
        View view = inflater.inflate(R.layout.news_list_layout,null);

        return new NewsAdapter.NewsViewHolder(view,Contex);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.NewsViewHolder holder, int position) {
        NewsModel model = newsModel_list.get(position);

        holder.title.setText(model.getTitle());
        holder.title.setBackgroundResource(TeamModel.getColorRes(model.getTeamName()));
        Log.d("TeamColorcheck","Team background color is" + model.getTeamBackgroundColor());


        holder.shortDescription.setText(model.getShortDescription());
        holder.longDescription.setText(model.getLongDescription());

        if(model.getTeamName().equals("FA")){
            holder.playerImageView.setVisibility(View.GONE);
        }else{
            holder.playerImageView.setImageResource(TeamModel.getLogoRes(model.getTeamName()));

        }

    }

    @Override
    public int getItemCount() {
        return newsModel_list.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder{
        ImageView playerImageView;
        TextView title, shortDescription,longDescription;
        CardView cv;
        Context contex;
        LinearLayout detailsLinearLayout;
        Boolean isPressed = false;



        public NewsViewHolder(View itemView, final Context c) {
            super(itemView);
            contex = c;

            title = (TextView) itemView.findViewById(R.id.news_Title);
            shortDescription = (TextView) itemView.findViewById(R.id.news_ShortInfo);
            longDescription= (TextView) itemView.findViewById(R.id.news_longDesc);
            detailsLinearLayout = (LinearLayout)itemView.findViewById(R.id.news_detailsLinearLayout);
            //teamLogoImageView = (ImageView) itemView.findViewById(R.id.news_PlayerIMG);
            playerImageView = (ImageView) itemView.findViewById(R.id.news_PlayerIMG);
            cv = (CardView) itemView.findViewById(R.id.news_cardview);


            cv.setOnClickListener(new View.OnClickListener() {

                CharSequence shortTemp = "";

                @Override
                public void onClick(View v) {
                    if(!isPressed){
                        isPressed = true;
                        Log.d("CardView", "onClick: Desplay should be long Desc");
                        shortTemp = shortDescription.getText();
                        shortDescription.setText(longDescription.getText());
                        playerImageView.setVisibility(View.GONE);
                    }else{
                        isPressed = false;
                        detailsLinearLayout.setVisibility(View.VISIBLE);
                        shortDescription.setText(shortTemp);
                        playerImageView.setVisibility(View.VISIBLE);


                    }
                }

            });

        }

    }
}
