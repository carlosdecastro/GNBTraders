package com.example.gnbtraders.network;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import java.io.IOException;
import java.util.concurrent.Future;

import com.example.gnbtraders.AppExecutors;
import com.example.gnbtraders.model.CurrencyValue;
import com.example.gnbtraders.model.Product;
import com.example.gnbtraders.model.Products;
import com.example.gnbtraders.model.Transaction;
import com.example.gnbtraders.utils.Config;

import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

public class GnbApiClient {

    private static final String TAG = "GnbApiClient";
    private static GnbApiClient instance;
    private MutableLiveData<List<Product>> mProducts;
    private MutableLiveData<List<CurrencyValue>> mCurrenciesValues;
    private RetrieveProductsRunnable mRetrieveProductsRunnable;
    private RetrieveCurrenciesValuesRunnable mRetrieveCurrenciesValuesRunnable;
    private MutableLiveData<Boolean> mDataRequestTimeOut = new MutableLiveData<>();


    public static GnbApiClient getInstance() {
        if (instance == null) {
            instance = new GnbApiClient();
        }
        return instance;
    }

    private GnbApiClient() {

        mProducts = new MutableLiveData<>();
        mCurrenciesValues = new MutableLiveData<>();
    }

    public LiveData<List<Product>> getProducts() {
        return mProducts;
    }
    public LiveData<List<CurrencyValue>> getCurrencyValues() { return mCurrenciesValues; }
    public LiveData<Boolean> isDataFetchTimeOut() { return mDataRequestTimeOut; }


    public void getProductsApi() {

        if (mRetrieveProductsRunnable != null) {
            mRetrieveProductsRunnable = null;
        }

        Log.d(TAG, "estoy casi en la llamada");

        mRetrieveProductsRunnable = new RetrieveProductsRunnable();
        final Future handler = AppExecutors.getInstance().networkIO().submit(mRetrieveProductsRunnable);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                mDataRequestTimeOut.postValue(true);
                handler.cancel(true);
            }
        }, Config.NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);

    }

    public void getCurrenciesValuesApi() {

        if (mRetrieveCurrenciesValuesRunnable != null) {
            mRetrieveCurrenciesValuesRunnable = null;
        }

        Log.d(TAG, "estoy casi en la llamada");

        mRetrieveCurrenciesValuesRunnable = new RetrieveCurrenciesValuesRunnable();
        final Future handler = AppExecutors.getInstance().networkIO().submit(mRetrieveCurrenciesValuesRunnable);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                handler.cancel(true);
            }
        }, Config.NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);

    }

    private class RetrieveProductsRunnable implements Runnable {

        boolean cancelRequest;

        public RetrieveProductsRunnable() {
            cancelRequest = false;
        }

        @Override
        public void run() {
            try {
                Response response = getTransactions().execute();

                if (response.code() == 200) {

                    List<Transaction> transactions = (List<Transaction>) response.body();

                    mProducts.postValue(Products.createProducts(transactions));
                } else {
                    Log.d(TAG, "Error al recibir datos");

                    String error = response.errorBody().string();
                    Log.e(TAG, "run: " + error);
                    mProducts.postValue(null);
                }

            } catch (IOException e) {
                e.printStackTrace();
                mProducts.postValue(null);
            }
        }



        private Call<List<Transaction>> getTransactions(){
            return ServiceGenerator.getGnbApi().getTransactions();
        }
    }

    private class RetrieveCurrenciesValuesRunnable implements Runnable {

        boolean cancelRequest;

        public RetrieveCurrenciesValuesRunnable() {
            cancelRequest = false;
        }

        @Override
        public void run() {
            try {
                Response response = getCurrenciesValues().execute();

                if (response.code() == 200) {

                    List<CurrencyValue> currenciesValues = (List<CurrencyValue>) response.body();

                    Log.d(TAG, "CurrenciesValues size: " + currenciesValues.size());

                    mCurrenciesValues.postValue(currenciesValues);
                } else {
                    String error = response.errorBody().string();
                    Log.e(TAG, "run: " + error);
                    mCurrenciesValues.postValue(null);
                }

            } catch (IOException e) {
                e.printStackTrace();
                mCurrenciesValues.postValue(null);
            }
        }

        private Call<List<CurrencyValue>> getCurrenciesValues(){
            return ServiceGenerator.getGnbApi().getCurrencyValues();
        }

    }
}