package com.example.gnbtraders.network;

import com.example.gnbtraders.model.CurrencyValue;
import com.example.gnbtraders.model.Transaction;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface GnbApi {

    @Headers({"Accept: application/json"})
    @GET("rates")
    Call<List<CurrencyValue>> getCurrencyValues();

    @Headers({"Accept: application/json"})
    @GET("transactions")
    Call<List<Transaction>> getTransactions();
}
