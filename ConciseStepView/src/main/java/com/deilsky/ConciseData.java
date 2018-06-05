package com.deilsky;

import android.os.Parcel;
import android.os.Parcelable;

public class ConciseData implements Parcelable {
    @Override
    public String toString() {
        return "ConciseData{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", finish=" + finish +
                '}';
    }

    private int id;
    private String name;
    private boolean finish = false;

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

    public boolean isFinish() {
        return finish;
    }

    public void setFinish(boolean finish) {
        this.finish = finish;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeByte(this.finish ? (byte) 1 : (byte) 0);
    }

    public ConciseData() {
    }

    protected ConciseData(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.finish = in.readByte() != 0;
    }

    public static final Parcelable.Creator<ConciseData> CREATOR = new Parcelable.Creator<ConciseData>() {
        @Override
        public ConciseData createFromParcel(Parcel source) {
            return new ConciseData(source);
        }

        @Override
        public ConciseData[] newArray(int size) {
            return new ConciseData[size];
        }
    };
}
