package com.example.itskh.myapplication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private static final String playlistUrl = "https://www.googleapis.com/youtube/v3/playlists?part=snippet&channelId=UCEfqzmMwibbzRK879P9kEPw&key=AIzaSyDEYPFMLPlAhKf3Fw8hPtLE4jcZFgGYXCY&maxResults=50";
    private ArrayList<playlistData> Ytdata;
    private ActionBarDrawerToggle toggle;
    private ProgressBar loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Loader section
        loader = findViewById(R.id.loader);

        //Ad Section
        /*
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        */
        //Splash Screen

        //Recycler View Section
        recyclerView = findViewById(R.id.playlist_item_item);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Ytdata = new ArrayList<>();
        loadRecyclerViewData();


        //Navigation
        DrawerLayout drawerLayout = findViewById(R.id.drawer);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView = findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
            case R.id.refresh: {
                loader.setVisibility(View.VISIBLE);
                Ytdata.clear();
                adapter.notifyDataSetChanged();
                loadRecyclerViewData();
                return true;
            }
            default:
                Toast.makeText(getApplicationContext(), "Something is Broken Try Again", Toast.LENGTH_LONG).show();
                return true;
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.home) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            //Toast.makeText(getApplicationContext(), "Home", Toast.LENGTH_SHORT).show();
        }
        if (item.getItemId() == R.id.about) {
            Intent intent = new Intent(this, aboutme.class);
            startActivity(intent);
            //Toast.makeText(getApplicationContext(), "about", Toast.LENGTH_SHORT).show();
        }

        return false;
    }

    private void loadRecyclerViewData() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, playlistUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject parentobject = new JSONObject(response);
                    JSONArray playlist = parentobject.getJSONArray("items");
                    for (int i = 0; i < playlist.length(); i++) {
                        JSONObject playlistitesm = playlist.getJSONObject(i);
                        JSONObject Snippet = playlistitesm.getJSONObject("snippet");
                        JSONObject Thumbnails = Snippet.getJSONObject("thumbnails");
                        JSONObject high = Thumbnails.getJSONObject("high");
                        playlistData youtube = new playlistData(Snippet.getString("title"), Snippet.getString("description"), high.getString("url"), playlistitesm.getString("id"));
                        Ytdata.add(youtube);
                    }
                    loader.setVisibility(View.GONE);
                    adapter = new playlistDataAdapter(Ytdata, getApplicationContext());
                    recyclerView.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Something unexpected happened! Try again?", Toast.LENGTH_LONG).show();

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}
