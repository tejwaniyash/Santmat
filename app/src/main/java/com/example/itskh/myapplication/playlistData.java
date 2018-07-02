package com.example.itskh.myapplication;

public class playlistData {
    private String Name;
    private String Desc;
    private String Image;
    private String playlistID;


    public playlistData(String name, String desc, String image, String playlistID) {
        Name = name;
        Desc = desc;
        Image = image;
        this.playlistID = playlistID;
    }

    public String getName() {
        return Name;
    }

    public String getDesc() {
        return Desc;
    }

    public String getImage() {
        return Image;
    }

    public String getPlaylistID() {
        return playlistID;
    }
}
