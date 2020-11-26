package com.example.mxmusic.model;

public class SongModel {
    private String id;
    private String title;
    private String singer;
    private String albumID;
    private String duration;
    private String songUri;

    public SongModel(String id, String title, String singer, String albumID, String duration, String songUri) {
        this.id = id;
        this.title = title;
        this.singer = singer;
        this.albumID = albumID;
        this.duration = duration;
        this.songUri = songUri;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getAlbumID() {
        return albumID;
    }

    public void setAlbumID(String albumID) {
        this.albumID = albumID;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getSongUri() {
        return songUri;
    }

    public void setSongUri(String songUri) {
        this.songUri = songUri;
    }
}
