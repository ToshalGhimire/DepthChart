package io.github.toshalghimire.depthchartv2.Fragments.NFL.NewsFagment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.github.toshalghimire.depthchartv2.Activity.testActivity;
import io.github.toshalghimire.depthchartv2.Models.NewsModel;
import io.github.toshalghimire.depthchartv2.Models.TeamModel;
import io.github.toshalghimire.depthchartv2.R;


/**
 * Adapter class that takes the data and links it to the views in the news layout and populates a RecycleView for News articles.
 */
public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.ViewHolder> {

    List<NewsModel> newslist;
    public NewsListAdapter(List<NewsModel> data){
        newslist = data;
    }

    /**
     * Sets the view layout to inflate the news data
     * @param parent contex news fragment
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.news_list_layout,null);
        return new ViewHolder(view,parent.getContext());
    }

    /**
     * Sets the items on the layout with the data
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NewsModel newsModel = newslist.get(position);

        holder.titleTextview.setText(newsModel.getTitle());
        holder.descTextview.setText(newsModel.getDescription());
        holder.dateTextview.setText(newsModel.getDate());
        holder.setArticleUrl(newsModel.getLink());

    }

    /**
     * Number of list items
     * @return size of list
     */
    @Override
    public int getItemCount() {
        return newslist.size();
    }

    /**
     * Class that holds the data for each of the RecycleView item. Handles the Logic for opening the link to the article, once clicked.
     */
    public class ViewHolder extends RecyclerView.ViewHolder{
        CardView cv;
        TextView titleTextview, descTextview,dateTextview;

        String title;
        String desc;
        String date;
        String articleUrl;
        Context contexVH;

        /**
         *
         * @return article link
         */
        public String getArticleUrl() {
            return articleUrl;
        }

        /**
         * Set article link
         * @param articleUrl
         */
        public void setArticleUrl(String articleUrl) {
            this.articleUrl = articleUrl;
        }

        /**
         *
         * @return Published date
         */
        public String getDate() {
            return date;
        }

        /**
         * Sets the Published date
         * @param date
         */
        public void setDate(String date) {
            this.date = date;
        }

        /**
         *
         * @return Title of article
         */
        public String getTitle() {
            return title;
        }

        /**
         * Sets the title of the article
         * @param title
         */
        public void setTitlee(String title) {
            this.title = title;
        }

        /**
         *
         * @return Description of article
         */
        public String getDesc() {
            return desc;
        }

        /**
         * Sets the title of the article
         * @param desc
         */
        public void setDesc(String desc) {
            this.desc = desc;
        }


        /**
         * Finds views and links it to the layout, Handles the logic for opening article on tap.
         * @param itemView
         * @param contex
         */
        public ViewHolder(View itemView, final Context contex) {
            super(itemView);

            titleTextview = (TextView) itemView.findViewById(R.id.news_title);
            descTextview = (TextView) itemView.findViewById(R.id.news_desc);
            dateTextview = (TextView) itemView.findViewById(R.id.news_date);


            this.contexVH = contex;
            cv = itemView.findViewById(R.id.news_cardview);

            cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                    browserIntent.setData(Uri.parse(articleUrl));
                    contexVH.startActivity(browserIntent);
                }

            });

        }



    }



}
