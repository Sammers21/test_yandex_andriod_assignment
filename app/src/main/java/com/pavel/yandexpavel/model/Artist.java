package com.pavel.yandexpavel.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Модель для парсинга JSON
 */
public class Artist implements Parcelable {

    private int id;
    private String name;
    private List<String> genres;
    private int tracks;
    private int albums;
    private String link;
    private String description;
    private Cover cover;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public int getTracks() {
        return tracks;
    }

    public void setTracks(int tracks) {
        this.tracks = tracks;
    }

    public int getAlbums() {
        return albums;
    }

    public void setAlbums(int albums) {
        this.albums = albums;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Cover getCover() {
        return cover;
    }

    public void setCover(Cover cover) {
        this.cover = cover;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeStringList(this.genres);
        dest.writeInt(this.tracks);
        dest.writeInt(this.albums);
        dest.writeString(this.link);
        dest.writeString(this.description);
        dest.writeParcelable(this.cover, flags);
    }

    public Artist() {
    }

    protected Artist(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.genres = in.createStringArrayList();
        this.tracks = in.readInt();
        this.albums = in.readInt();
        this.link = in.readString();
        this.description = in.readString();
        this.cover = in.readParcelable(Cover.class.getClassLoader());
    }

    public static final Parcelable.Creator<Artist> CREATOR = new Parcelable.Creator<Artist>() {
        @Override
        public Artist createFromParcel(Parcel source) {
            return new Artist(source);
        }

        @Override
        public Artist[] newArray(int size) {
            return new Artist[size];
        }
    };
}