package com.example.gnbtraders.model;

import java.util.ArrayList;
import java.util.List;

public class Products {

    public static List<Product> createProducts(final List<Transaction> transactions) {

        List<Product> mProducts = new ArrayList<>();

        for (Transaction t : transactions) {
            Product product = new Product(t.getSku());
            mProducts.add(product);
        }

        List<Product> noRepeat = new ArrayList<>();

        for (Product p : mProducts) {
            boolean isFound = false;

            for (Product e : noRepeat) {
                if (e.getSku().equals(p.getSku()) || (e.equals(p))) {
                    isFound = true;
                    break;
                }
            }
            if (!isFound)
                noRepeat.add(p);
        }
        for (Transaction t : transactions) {
            for (Product p : noRepeat) {
                if (p.getSku().equals(t.getSku())) {
                    p.setTransaction(t);
                }
            }
        }

        return noRepeat;
    }
}
