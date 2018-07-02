package com.example.itskh.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class player extends YouTubeBaseActivity {
    private static final String TAG = "player";
    private static final String KEY="AIzaSyDEYPFMLPlAhKf3Fw8hPtLE4jcZFgGYXCY";
    String ID;
    YouTubePlayerView player;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player);

        if(getIntent().hasExtra("Video_Id"))
        {
            Log.d(TAG,"Intent Found");
            ID=getIntent().getStringExtra("Video_Id");
            Log.d(TAG,ID);
        }


        player=(YouTubePlayerView) findViewById(R.id.player);
        player.initialize(KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                if(!b)
                {
                    youTubePlayer.loadVideo(ID);
                    youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.MINIMAL);
                    youTubePlayer.setFullscreen(true);
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        });

    }
}
