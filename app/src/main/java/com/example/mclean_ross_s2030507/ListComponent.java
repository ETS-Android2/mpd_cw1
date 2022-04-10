/*
Ross McLean-->
S2030507-->
10/04/22
*/
package com.example.mclean_ross_s2030507;

import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

public class ListComponent {
    // Values are based on request response data values of Traffic Scotland feeds
    private String title;
    private String description;
    private String link;
    private String geoRssPoint;
    private String author;
    private String comments;
    private LocalDate publicationDate;
    private long totalTimeAllotted;


    @RequiresApi(api = Build.VERSION_CODES.O)
    public ListComponent(String title,
                         String description,
                         String link,
                         String geoRssPoint,
                         String author,
                         String comments,
                         LocalDate publicationDate) {
        this.title = title;
        this.description = description;
        this.link = link;
        this.geoRssPoint = geoRssPoint;
        this.author = author;
        this.comments = comments;
        this.publicationDate = publicationDate;

        totalTimeAllotted = getTotalTimeFromDescription(description);
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

    public LocalDate getPublicationDate() { return publicationDate; }

    public void setPublicationDate(LocalDate publicationDate) { this.publicationDate = publicationDate; }

    public long getTotalTimeAllotted() {return totalTimeAllotted; }

    public void setTotalTimeAllotted(long totalTimeAllotted) { this.totalTimeAllotted = totalTimeAllotted; }

    @NonNull
    public String toString() {
        return title;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public long getTotalTimeFromDescription(String description) {
        long totalTimeAllottedResult = 0;
        try {
            int startDateBeginning = description.indexOf(": ");
            int startDateEnd = description.indexOf(" - 00:00");
            String startDate = description.substring(startDateBeginning + 2, startDateEnd).trim();

            int endDateBeginning = description.indexOf("End Date:");
            int endDateEnd = description.lastIndexOf(" - 00:00");
            String endDate = description.substring(endDateBeginning + 10, endDateEnd).trim();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("EEEE, d MMMM yyyy", Locale.ENGLISH);
            LocalDate firstDate = LocalDate.parse(startDate, dateTimeFormatter);
            LocalDate lastDate = LocalDate.parse(endDate, dateTimeFormatter);

            totalTimeAllottedResult = firstDate.atStartOfDay().until(lastDate.atStartOfDay(), ChronoUnit.DAYS);
            return totalTimeAllottedResult;
        } catch (Exception ex) {
            Log.e("Exception", "getTotalTimeFromDescription exception: " + ex);
        }
        return totalTimeAllottedResult;
    }
}
