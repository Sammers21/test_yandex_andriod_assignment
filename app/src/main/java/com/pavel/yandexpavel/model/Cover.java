package com.pavel.yandexpavel.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Модель дополняющего основной объектка для парсинга JSON
 */
public class Cover implements Parcelable {
    private String small;
    private String big;

    public String getSmall() {
        return small;
    }

    public void setSmall(String small) {
        this.small = small;
    }

    public String getBig() {
        return big;
    }

    public void setBig(String big) {
        this.big = big;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.small);
        dest.writeString(this.big);
    }

    public Cover() {
    }

    protected Cover(Parcel in) {
        this.small = in.readString();
        this.big = in.readString();
    }

    public static final Parcelable.Creator<Cover> CREATOR = new Parcelable.Creator<Cover>() {
        @Override
        public Cover createFromParcel(Parcel source) {
            return new Cover(source);
        }

        @Override
        public Cover[] newArray(int size) {
            return new Cover[size];
        }
    };
}
