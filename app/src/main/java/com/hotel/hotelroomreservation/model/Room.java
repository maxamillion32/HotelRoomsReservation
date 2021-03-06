package com.hotel.hotelroomreservation.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Room implements Parcelable {
    private String name;
    private int number;
    private int rating;
    private int visitors;
    private String url;
    private int price;

    public static final Creator<Room> CREATOR = new Creator<Room>() {
        @Override
        public Room createFromParcel(Parcel in) {
            return new Room(in);
        }

        @Override
        public Room[] newArray(int size) {
            return new Room[size];
        }
    };

    public Room() {

    }

    protected Room(Parcel in) {
        name = in.readString();
        number = in.readInt();
        rating = in.readInt();
        visitors = in.readInt();
        price = in.readInt();
        url = in.readString();
    }

    @JsonCreator
    public Room(@JsonProperty("name") String name, @JsonProperty("number") int number,
                @JsonProperty("rating") int rating, @JsonProperty("visitors") int visitors,
                @JsonProperty("price") int price, @JsonProperty("url") String url) {
        this.name = name;
        this.number = number;
        this.rating = rating;
        this.visitors = visitors;
        this.price = price;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getVisitors() {
        return visitors;
    }

    public void setVisitors(int visitors) {
        this.visitors = visitors;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeInt(number);
        parcel.writeInt(rating);
        parcel.writeInt(visitors);
        parcel.writeInt(price);
        parcel.writeString(url);
    }
}
