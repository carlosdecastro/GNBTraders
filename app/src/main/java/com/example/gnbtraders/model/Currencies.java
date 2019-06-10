package com.example.gnbtraders.model;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Currencies {

    private List<CurrencyValue> mCurrenciesValues;
    private List<Currency> mCurrencies;

    public Currencies(List<CurrencyValue> currencyValues) {
        this.mCurrenciesValues = currencyValues;
    }


    public BigDecimal convertAmount(String from, String to, String value) {

        BigDecimal amount = new BigDecimal(value);

        if (from.equals(to))
            return new BigDecimal(value);

        for (Currency c : mCurrencies) {
            if (c.getName().equals(from)) {
                List<Currency> exchanges = c.getExchanges();

                for (Currency s : exchanges) {

                    if (s.getName().equals(to)) {
                        for (CurrencyValue cV : mCurrenciesValues) {
                            if (cV.getFrom().equals(from) && cV.getTo().equals(to)) {
                                return amount.multiply(new BigDecimal(cV.getRate()));
                            }
                        }
                    } else {
                        List<Currency> exchanges2 = s.getExchanges();
                        for (Currency s2 : exchanges2) {
                            if (s2.getName().equals(to)) {
                                for (CurrencyValue cV : mCurrenciesValues) {
                                    if (cV.getFrom().equals(from) && cV.getTo().equals(s.getName())) {
                                        return convertAmount(s.getName(), to, amount.multiply(new BigDecimal(cV.getRate())).toString());
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return new BigDecimal(-1);
    }

    public void createCurrencies() {

        List<String> tmp = new ArrayList<>();
        List<Currency> currencies = new ArrayList<>();

        for (CurrencyValue c : mCurrenciesValues) {
            tmp.add(c.getFrom());
        }

        Set<String> currenciesNames = new LinkedHashSet<>(tmp);

        for (String c : currenciesNames) {
            currencies.add(new Currency(c));
        }

        for (Currency c : currencies) {
            for (CurrencyValue cV : mCurrenciesValues) {
                if (c.getName().equals(cV.getFrom())) {
                    for (Currency c1 : currencies) {
                        if (c1.getName().equals(cV.getTo())) {
                            c.setCurrencyExchange(c1);
                        }
                    }
                }
            }
        }

        mCurrencies = currencies;
    }

}
