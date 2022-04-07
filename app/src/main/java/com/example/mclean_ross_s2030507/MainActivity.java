package com.example.mclean_ross_s2030507;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    // Store Traffic Scotland links
    private String roadworksUrl = "https://trafficscotland.org/rss/feeds/roadworks.aspx";
    private String plannedRoadworksUrl = "https://trafficscotland.org/rss/feeds/plannedroadworks.aspx";
    private String currentIncidentsUrl = "https://trafficscotland.org/rss/feeds/currentincidents.aspx";
    private String urlValue;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Make toolbar actionBar
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.toolbar_title);
        setSupportActionBar(toolbar);


    }
}