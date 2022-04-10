package com.example.mclean_ross_s2030507;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener,
        Serializable {
    private String result = "";

    DrawerLayout drawerLayout;
    ArrayList<ListComponent> componentsList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Make toolbar actionBar
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.toolbar_title);
        setSupportActionBar(toolbar);

        // Initialise componentsList
        componentsList = new ArrayList<>();

        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close
        );
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            HomeFragment homeFragment = new HomeFragment();
            transaction.replace(R.id.main_fragment_target, homeFragment);
            transaction.commit();

            navigationView.setCheckedItem(R.id.nav_home);
        }
    }

    @Override
    public void onBackPressed () {
        DrawerLayout drawer = findViewById(R.id. drawer_layout) ;
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed() ;
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.nav_home) {
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            HomeFragment homeFragment = new HomeFragment();
            transaction.replace(R.id.main_fragment_target, homeFragment);
            transaction.commit();
        }

        if (item.getItemId() == R.id.nav_current_roadworks) triggerParseRoadworksData(true);
        if (item.getItemId() == R.id.nav_planned_roadworks) triggerParseRoadworksData(false);
        if (item.getItemId() == R.id.nav_current_incidents) triggerParseIncidentsData();

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void triggerParseRoadworksData(boolean isCurrentRoadworks) {
        componentsList.clear();
        result = "";
        if (isCurrentRoadworks) {
            // Store Traffic Scotland links
            String roadworksUrl = "https://trafficscotland.org/rss/feeds/roadworks.aspx";
            new Thread(new Task(roadworksUrl)).start();
        }
        else {
            String plannedRoadworksUrl = "https://trafficscotland.org/rss/feeds/plannedroadworks.aspx";
            new Thread(new Task(plannedRoadworksUrl)).start();
        }
    }

    public void triggerParseIncidentsData() {
        componentsList.clear();
        result = "";
        String currentIncidentsUrl = "https://trafficscotland.org/rss/feeds/currentincidents.aspx";
        new Thread(new Task(currentIncidentsUrl)).start();
    }

    private class Task implements Runnable {
        private final String url;

        public Task(String url) {
            this.url = url;
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void run() {
            URL url;
            URLConnection urlConnection;
            BufferedReader bufferedReader;
            String inputLine;
            try {
                url = new URL(this.url);
                urlConnection = url.openConnection();
                bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                while ((inputLine = bufferedReader.readLine()) != null) result += inputLine;
                bufferedReader.close();
            } catch (IOException ioException) {
                Log.e("IOException Tag", "IOException in run()");
            }

            parseXmlData(result);

            MainActivity.this.runOnUiThread(() -> {
//                Intent intent = new Intent(getApplicationContext(), ComponentListActivity.class);
//                intent.putParcelableArrayListExtra("data", (ArrayList) componentsList);
//                startActivity(intent);

                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ComponentListFragment componentListFragment = new ComponentListFragment(componentsList);
                transaction.replace(R.id.main_fragment_target, componentListFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            });
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void parseXmlData(String input) {
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new StringReader(input));
            int eventType = xpp.getEventType();

            String title = "";
            String description = "";
            String link = "";
            String geoRssPoint = "";
            String author = "";
            String comments = "";
            String publishDate;
            String formattedDate = null;
            LocalDate actualDate = null;

            boolean isInsideXmlItem = false;
            LocalDate newDate = null;

            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    if (xpp.getName().equalsIgnoreCase("item")) {
                        isInsideXmlItem = true;
                    } else if (xpp.getName().equalsIgnoreCase("title")) {
                        if (isInsideXmlItem) title = xpp.nextText();
                    } else if (xpp.getName().equalsIgnoreCase("description")) {
                        if (isInsideXmlItem) {
                            description = xpp.nextText();
                            description = description.replace("<br />", "\n");
                            description = description.replace(" - 00:00", "");
                        }
                    } else if (xpp.getName().equalsIgnoreCase("link")) {
                        if (isInsideXmlItem) link = xpp.nextText();
                    } else if (xpp.getName().equalsIgnoreCase("point")) {
                        if (isInsideXmlItem) geoRssPoint = xpp.nextText();
                    } else if (xpp.getName().equalsIgnoreCase("author")) {
                        if (isInsideXmlItem) author = xpp.nextText();
                    } else if (xpp.getName().equalsIgnoreCase("comments")) {
                        if (isInsideXmlItem) comments = xpp.nextText();
                    }else if (xpp.getName().equalsIgnoreCase("pubdate")) {
                        if (isInsideXmlItem) {
                            publishDate = xpp.nextText();
                            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("EEE, d MMM yyyy HH:mm:ss zzz", Locale.ENGLISH);
                            actualDate = LocalDate.parse(publishDate, dtf);
                            formattedDate = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).format(actualDate);
                        }
                    }
                } else if (eventType == XmlPullParser.END_TAG) {
                    if (xpp.getName().equalsIgnoreCase("item")) {
                        isInsideXmlItem = false;
                        ListComponent component = new ListComponent(
                                title, description, link, geoRssPoint, author, comments, actualDate
                        );
                        componentsList.add(component);
                    }
                }
                eventType = xpp.next();
            }
        } catch (XmlPullParserException | IOException e) {
            Log.e("Exception tag", "parseRoadworksData failed: " + e);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) { return true; }
}