package com.example.mclean_ross_s2030507;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Date;

public class ListComponent {
    // Values are based on request response data values of Traffic Scotland feeds
    private int id;
    private String title;
    private String description;
    private String link;
    private String geoRssPoint;
    private String author;
    private String comments;
    private Date publicationDate;


    public ListComponent(String title,
                         String description,
                         String link,
                         String geoRssPoint,
                         String author,
                         String comments,
                         Date publicationDate) {
        this.title = title;
        this.description = description;
        this.link = link;
        this.geoRssPoint = geoRssPoint;
        this.author = author;
        this.comments = comments;
        this.publicationDate = publicationDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getGeoRssPoint() {
        return geoRssPoint;
    }

    public void setGeoRssPoint(String geoRssPoint) {
        this.geoRssPoint = geoRssPoint;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    @NonNull
    public String toString() {
        return title;
    }
}
