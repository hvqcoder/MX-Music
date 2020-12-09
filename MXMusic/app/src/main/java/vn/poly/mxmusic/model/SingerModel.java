package vn.poly.mxmusic.model;

public class SingerModel {
    private String id;
    private String name;
    private String countSong;

    public SingerModel(String id, String name, String countSong) {
        this.id = id;
        this.name = name;
        this.countSong = countSong;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getCountSong() {
        return countSong;
    }

}
