package io.github.toshalghimire.depthchartv2.ListAdapters;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import io.github.toshalghimire.depthchartv2.Models.InjuryModel;
import io.github.toshalghimire.depthchartv2.Models.TeamModel;
import io.github.toshalghimire.depthchartv2.R;


/**
 * Adapter class that takes the data and links it to the views in the Injury layout and populates a RecycleView for latest injuries.
 */
public class InjuryAdapter extends RecyclerView.Adapter<InjuryAdapter.NewsViewHolder>{
    final private String TAG = "RECYCLE VIEW ADAPTER INJURY";
    private Context Contex;
    Resources res = null;
    private List<InjuryModel> injuryModel_list;

    /**
     * Constructor for the Adapter, takes in current contex and a Injury model list.
     * @param mContex current contex
     * @param injuryModel List of injury model objects
     */
    public InjuryAdapter(Context mContex, List<InjuryModel> injuryModel) {
        this.Contex = mContex;
        this.injuryModel_list = injuryModel;
        res = this.Contex.getResources();
    }

    /**
     * Sets the view layout to inflate the Injury data
     * @param parent contex news fragment
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public InjuryAdapter.NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(Contex);
        View view = inflater.inflate(R.layout.injury_list_layout,null);

        return new InjuryAdapter.NewsViewHolder(view,Contex);
    }

    /**
     * Sets the items on the layout with the data
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull InjuryAdapter.NewsViewHolder holder, int position) {
        InjuryModel model = injuryModel_list.get(position);

        holder.title.setText(model.getTitle());
        Log.d(TAG, "onBindViewHolder: " +model.getTitle() + " - " +  model.getTeamName());
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

    /**
     * Number of list items
     * @return size of list
     */
    @Override
    public int getItemCount() {
        return injuryModel_list.size();
    }

    /**
     * Class that holds the data for each of the RecycleView item.
     * Handles the Logic for displaying additional data when injury is clicked.
     */
    public class NewsViewHolder extends RecyclerView.ViewHolder{
        ImageView playerImageView;
        TextView title, shortDescription,longDescription;
        CardView cv;
        Context contex;
        LinearLayout detailsLinearLayout;
        Boolean isPressed = false;


        /**
         * Finds views and links it to the layout, Handles the logic for displaying more data on tap.
         * @param itemView
         * @param c
         */
        public NewsViewHolder(View itemView, final Context c) {
            super(itemView);
            contex = c;

            title = (TextView) itemView.findViewById(R.id.injury_Title);
            shortDescription = (TextView) itemView.findViewById(R.id.injury_ShortInfo);
            longDescription= (TextView) itemView.findViewById(R.id.injury_longDesc);
            detailsLinearLayout = (LinearLayout)itemView.findViewById(R.id.injury_detailsLinearLayout);
            //teamLogoImageView = (ImageView) itemView.findViewById(R.id.news_PlayerIMG);
            playerImageView = (ImageView) itemView.findViewById(R.id.injury_PlayerIMG);
            cv = (CardView) itemView.findViewById(R.id.injury_cardview);


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
