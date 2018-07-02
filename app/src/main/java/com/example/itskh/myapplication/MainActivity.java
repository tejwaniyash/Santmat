package com.example.itskh.myapplication;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<playlistData> Ytdata;

    private static final String playlistUrl = "https://www.googleapis.com/youtube/v3/playlists?part=snippet&channelId=UCEfqzmMwibbzRK879P9kEPw&key=AIzaSyDEYPFMLPlAhKf3Fw8hPtLE4jcZFgGYXCY&maxResults=50";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Ad Section
        String AppId="ca-app-pub-2406081447963977~4589747823";
        MobileAds.initialize(this, AppId);
        AdView mAdView = findViewById(R.id.ad);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        //Splash Screen

        //Recycler View Section
        recyclerView = findViewById(R.id.playlist_item_item);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Ytdata = new ArrayList<>();
        loadRecyclerViewData();
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
                    adapter = new playlistDataAdapter(Ytdata, getApplicationContext());
                    recyclerView.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "No Internet Access", Toast.LENGTH_LONG).show();

            }
        });


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }


}

