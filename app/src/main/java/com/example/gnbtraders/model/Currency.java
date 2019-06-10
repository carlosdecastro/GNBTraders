package com.example.gnbtraders.model;

import java.util.ArrayList;
import java.util.List;

public class Currency  {

    private String mCurrency;
    private List<Currency> mCurrenciesExchange = new ArrayList<>();

    public Currency (String name) {
        this.mCurrency = name;
    }


    public void setCurrencyExchange(Currency currency) {
        mCurrenciesExchange.add(currency);
    }

    public String getName() {return mCurrency;}

    public List<Currency> getExchanges() {return mCurrenciesExchange;}
}
