package com.example.itskh.myapplication;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.facebook.shimmer.ShimmerFrameLayout;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class VideorecyclerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "VideorecyclerActivity";
    private String Videoidurl;
    private List<Videolist> videos;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private ActionBarDrawerToggle toggle;
    private ShimmerFrameLayout shimmerFrameLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.videorecycler);
        Log.d(TAG, "Video List:");


        shimmerFrameLayout=findViewById(R.id.shimmer_view_container);

        // Ads code
        /*
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        */

        //Navigation
        DrawerLayout drawerLayout = findViewById(R.id.drawer);
        toggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView = findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(this);

        //Previous Screen Data
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
    @Override
    protected void onPostResume() {
        super.onPostResume();
        shimmerFrameLayout.startShimmer();
    }

    @Override
    protected void onPause() {
        super.onPause();
        shimmerFrameLayout.stopShimmer();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actions, menu);
        ConnectivityManager cm = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if(activeNetwork!=null)
            return true;
        else
            return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(toggle.onOptionsItemSelected(item))
        {
            return true;
        }
        switch (item.getItemId())
        {
            case R.id.refresh:
                videos.clear();
                shimmerFrameLayout.setVisibility(View.VISIBLE);
                //loader_video.setVisibility(View.VISIBLE);
                adapter.notifyDataSetChanged();
                loadRecyclerViewData();
                return true;
            default:
                Toast.makeText(getApplicationContext(), "Something is Broken Try Again", Toast.LENGTH_LONG).show();
                return super.onOptionsItemSelected(item);
        }


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
                    //loader_video.setVisibility(View.GONE);
                    shimmerFrameLayout.setVisibility(View.GONE);
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.home)
        {
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }
        else if(item.getItemId()==R.id.about)
        {
            Intent intent = new Intent(this,aboutme.class);
            startActivity(intent);
        }
        else if (item.getItemId() == R.id.stuti)
        {
            Intent intent = new Intent(this,stutiActivity.class);
            startActivity(intent);
        }

        return false;
    }
}