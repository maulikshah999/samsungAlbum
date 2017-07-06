package com.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by mauli on 7/4/2017.
 */

public class Album implements Parcelable {

    private int albumID;
    private ArrayList<String> albumImages = new ArrayList<String>();
    private String albumName;

    public Album() {

    }

    protected Album(Parcel in) {
        albumID = in.readInt();
        albumImages = in.createStringArrayList();
        albumName = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(albumID);
        dest.writeStringList(albumImages);
        dest.writeString(albumName);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Album> CREATOR = new Creator<Album>() {
        @Override
        public Album createFromParcel(Parcel in) {
            return new Album(in);
        }

        @Override
        public Album[] newArray(int size) {
            return new Album[size];
        }
    };

    public int getAlbumID() {
        return albumID;
    }

    public void setAlbumID(int albumID) {
        this.albumID = albumID;
    }

    public ArrayList<String> getAlbumImages() {
        return albumImages;
    }

    public void setAlbumImages(ArrayList<String> albumImages) {
        this.albumImages = albumImages;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }
}
