package com.example.itskh.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;


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

public class VideorecyclerActivity extends AppCompatActivity {
    private static final String TAG = "VideorecyclerActivity";

    private String Videoidurl;
    private List<Videolist> videos;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.videorecycler);
        Log.d(TAG, "onCreate: started");
        String AppId="ca-app-pub-2406081447963977~4589747823";
        MobileAds.initialize(this, AppId);
        AdView mAdView = findViewById(R.id.ad);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        if (getIntent().hasExtra("Plylist-id")) {
            String videolistp1 = "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId=";
            String Videolistp2 = "&key=AIzaSyDEYPFMLPlAhKf3Fw8hPtLE4jcZFgGYXCY&maxResults=50";
            String playlistid = getIntent().getStringExtra("Plylist-id");
            Videoidurl = videolistp1 + playlistid + Videolistp2;


        }

        recyclerView = findViewById(R.id.VideoListRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        videos = new ArrayList<>();

        loadRecyclerViewData();

    }


    private void loadRecyclerViewData() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Videoidurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject ParentObject = new JSONObject(response);
                    JSONArray VideoList = ParentObject.getJSONArray("items");

                    for (int i = 0; i < VideoList.length(); i++) {
                        JSONObject playlistitesm = VideoList.getJSONObject(i);
                        JSONObject Snippet = playlistitesm.getJSONObject("snippet");
                        JSONObject Thumbnails = Snippet.getJSONObject("thumbnails");
                        JSONObject VideoID = Snippet.getJSONObject("resourceId");
                        JSONObject high = Thumbnails.getJSONObject("high");

                        Videolist videodata = new Videolist(Snippet.getString("title"), VideoID.getString("videoId"), high.getString("url"));
                        videos.add(videodata);
                    }
                    adapter = new videolistAdapter(videos, getApplicationContext());
                    recyclerView.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}