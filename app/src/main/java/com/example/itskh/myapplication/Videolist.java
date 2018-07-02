package com.example.itskh.myapplication;

public class Videolist {
    private String VideoTitle;
    private String VideoID;
    private String VideoImage;

    public Videolist(String videoTitle, String videoID, String videoImage) {
        VideoTitle = videoTitle;
        VideoID = videoID;
        VideoImage = videoImage;
    }

    public String getVideoTitle() {
        return VideoTitle;
    }

    public String getVideoID() {
        return VideoID;
    }

    public String getVideoImage() {
        return VideoImage;
    }
}
