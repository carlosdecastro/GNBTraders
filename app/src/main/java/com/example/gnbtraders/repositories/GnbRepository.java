package com.example.gnbtraders.repositories;

import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.example.gnbtraders.model.CurrencyValue;
import com.example.gnbtraders.model.Product;
import com.example.gnbtraders.network.GnbApiClient;

import java.util.List;

public class GnbRepository {

    private static final String TAG = "GnbRepository";

    private static GnbRepository instance;
    private static GnbApiClient mGnbApiClient;

    public static GnbRepository getInstance() {
        if (instance == null) {
            instance = new GnbRepository();
        }

        return instance;
    }

    private GnbRepository() {
        mGnbApiClient = GnbApiClient.getInstance();
    }

    public LiveData<List<Product>> getProducts() {
        return mGnbApiClient.getProducts();
    }

    public LiveData<List<CurrencyValue>> getCurrenciesValues() {
        return mGnbApiClient.getCurrencyValues();
    }

    public LiveData<Boolean> isDataFetchTimeOut() { return mGnbApiClient.isDataFetchTimeOut(); }


    public void getProductsApi() {
        Log.d(TAG, "getProductsApi API call");
        mGnbApiClient.getProductsApi();
    }

    public void getCurrenciesValuesApi() {
        mGnbApiClient.getCurrenciesValuesApi();
    }
}
