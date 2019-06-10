package com.example.gnbtraders.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class Product implements Parcelable {

    private String mSku;
    private List<Transaction> mTransactions = new ArrayList<>();


    public Product(String sku) {
        this.mSku = sku;
    }

    protected Product(Parcel in) {
        mSku = in.readString();
        mTransactions = in.createTypedArrayList(Transaction.CREATOR);
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    public void setTransaction(Transaction transaction) {
        mTransactions.add(transaction);
    }

    public String getSku() {
        return mSku;
    }

    public List<Transaction> getTransactions() {
        return mTransactions;
    }

    public BigDecimal calculateTotalTransactionsValue(String currency, Currencies currencies) {

        BigDecimal total = new BigDecimal(0);

        for (Transaction t : mTransactions) {
            if (!t.getCurrency().equals(currency)) {
                total = total.add(currencies.convertAmount(t.getCurrency(), currency, t.getAmount()));
            } else {
                total = total.add(new BigDecimal(t.getAmount()));
            }
        }

        return total.setScale(2, RoundingMode.HALF_EVEN);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mSku);
        dest.writeTypedList(mTransactions);
    }
}
