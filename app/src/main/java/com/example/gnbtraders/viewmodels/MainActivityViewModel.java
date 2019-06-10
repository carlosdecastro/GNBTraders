package com.example.gnbtraders.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.gnbtraders.model.CurrencyValue;
import com.example.gnbtraders.model.Product;
import com.example.gnbtraders.repositories.GnbRepository;

import java.util.List;

public class MainActivityViewModel extends ViewModel {

    private GnbRepository mGnbRepository;
    private boolean mDidRetrieveData;


    public MainActivityViewModel() {
            mGnbRepository = GnbRepository.getInstance();
            mGnbRepository.getProductsApi();
            mGnbRepository.getCurrenciesValuesApi();
            mDidRetrieveData = false;
    }

    public LiveData<List<Product>> getProducts() {
        return mGnbRepository.getProducts();
    }


    public LiveData<List<CurrencyValue>> getCurrenciesValues() {
        return mGnbRepository.getCurrenciesValues();
    }


    public void getProductsApi() {
        mGnbRepository.getProductsApi();
    }

    public void getCurrenciesValuesApi() {
        mGnbRepository.getCurrenciesValuesApi();
    }

    public LiveData<Boolean> isDataFechTimeOut() { return mGnbRepository.isDataFetchTimeOut(); }

    public void setRetrievedData (boolean retrievedData) {
        mDidRetrieveData = retrievedData;
    }

    public boolean getDidRetrievedData() {
        return mDidRetrieveData;
    }

}
