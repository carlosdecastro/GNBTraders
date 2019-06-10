package com.example.gnbtraders.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CurrencyValue implements Parcelable {

    @SerializedName("from")
    @Expose
    private String from;
    @SerializedName("to")
    @Expose
    private String to;
    @SerializedName("rate")
    @Expose
    private String rate;


    protected CurrencyValue(Parcel in) {
        from = in.readString();
        to = in.readString();
        rate = in.readString();
    }

    public static final Creator<CurrencyValue> CREATOR = new Creator<CurrencyValue>() {
        @Override
        public CurrencyValue createFromParcel(Parcel in) {
            return new CurrencyValue(in);
        }

        @Override
        public CurrencyValue[] newArray(int size) {
            return new CurrencyValue[size];
        }
    };

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(from);
        dest.writeString(to);
        dest.writeString(rate);
    }
}