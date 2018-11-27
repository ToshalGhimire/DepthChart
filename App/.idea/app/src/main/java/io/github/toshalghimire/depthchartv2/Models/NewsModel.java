package io.github.toshalghimire.depthchartv2.Models;

import android.support.annotation.NonNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.SimpleFormatter;

/**
 * Model class to store the data for the news articles.
 */
public class NewsModel implements Comparable<NewsModel> {

    private String title;
    private String Description;
    private String link;
    private Date published;

    /**
     * Constructor for the news articles.
     * @param title Title of article
     * @param description Description of article
     * @param date published date
     * @param link link to full article
     */
    public NewsModel(String title, String description, String date, String link)  {
        this.title = title;
        this.Description = description;
        this.link = link;

        // Formating date
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
        try {
            this.published = dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    @Override
    public String toString() {
        return "NewsModel{" +
                "title='" + title + '\'' +
                ", Description='" + Description + '\'' +
                ", link='" + link + '\'' +
                ", published=" + getDate() +
                '}';
    }

    public Date getPublished() {
        return published;
    }

    public void setPublished(Date published) {
        this.published = published;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getDate() {
        DateFormat df = new SimpleDateFormat("MMMM dd, hh:mm a");
        return df.format(published);
    }

    @Override
    public int compareTo(@NonNull NewsModel o) {
        return getPublished().compareTo(o.getPublished());
    }
}
