package com.example.gnbtraders;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.gnbtraders.adapters.TransactionListAdapter;
import com.example.gnbtraders.model.Currencies;
import com.example.gnbtraders.model.CurrencyValue;
import com.example.gnbtraders.model.Product;
import com.example.gnbtraders.utils.Config;
import com.example.gnbtraders.utils.Utils;

import java.util.List;

public class ProductActivity extends AppCompatActivity {

    private Product sProduct;
    private List<CurrencyValue> sCurrenciesValues;
    private Bundle mProductBundle;

    private static final String TAG = "ProductActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

       RecyclerView mTransactionRecyclerView = findViewById(R.id.recycler_view_transactions);
       TextView totalAmountTransactions = findViewById(R.id.total_amount_text);


        if (getIntent().getExtras() != null) {
            mProductBundle = getIntent().getExtras();
            sProduct = mProductBundle.getParcelable(Config.INTENT_KEY_SELECTED_PRODUCT);
            sCurrenciesValues = mProductBundle.getParcelableArrayList(Config.INTENT_KEY_CURRENCIES);
        }


        if ((sProduct == null) || (Utils.isEmptyString(sProduct.getSku()))) return;
        setTitle("Product: " + sProduct.getSku());

        Currencies currencies = new Currencies(sCurrenciesValues);
        currencies.createCurrencies();

        LinearLayoutManager llm = new LinearLayoutManager(this);
        mTransactionRecyclerView.setLayoutManager(llm);

        TransactionListAdapter mTransactionListAdapter = new TransactionListAdapter(getApplicationContext());
        mTransactionRecyclerView.setAdapter(mTransactionListAdapter);

        mTransactionListAdapter.setProducts(sProduct.getTransactions());
        mTransactionListAdapter.notifyDataSetChanged();

        totalAmountTransactions.setText("Total transactions amount: " + sProduct.calculateTotalTransactionsValue("EUR", currencies) + "€");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}