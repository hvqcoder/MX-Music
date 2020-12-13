package vn.poly.mxmusic.model;

public class SongModel {
    private String id;
    private String title;
    private String singer;
    private String album;
    private String duration;
    private String songUri;
    private String size;
    private String year;
    private String composer;
    private String albumArtist;
    private String displayName;
    private String albumID;
    private String singerID;

    public SongModel(String id, String title, String singer, String album, String duration, String songUri, String size, String year, String composer, String albumArtist, String displayName, String albumID, String singerID) {
        this.id = id;
        this.title = title;
        this.singer = singer;
        this.album = album;
        this.duration = duration;
        this.songUri = songUri;
        this.size = size;
        this.year = year;
        this.composer = composer;
        this.albumArtist = albumArtist;
        this.displayName = displayName;
        this.albumID = albumID;
        this.singerID = singerID;
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

    public String getSinger() {
        return singer;
    }

    public String getAlbum() {
        return album;
    }

    public String getDuration() {
        return duration;
    }

    public String getSongUri() {
        return songUri;
    }

    public String getSize() {
        return size;
    }

    public String getYear() {
        return year;
    }

    public String getComposer() {
        return composer;
    }

    public String getAlbumArtist() {
        return albumArtist;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getAlbumID() {
        return albumID;
    }

    public String getSingerID() {
        return singerID;
    }
}
