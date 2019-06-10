package com.example.gnbtraders;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.example.gnbtraders.adapters.ProductListAdapter;

import com.example.gnbtraders.model.CurrencyValue;
import com.example.gnbtraders.model.Product;
import com.example.gnbtraders.utils.Config;
import com.example.gnbtraders.viewmodels.MainActivityViewModel;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements ProductListAdapter.ListItemClickListener {

    private ProductListAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private RelativeLayout mRelativeLayout;
    private ProgressBar mProgressBar;
    private List<CurrencyValue> currenciesValues = new ArrayList<>();
    private static final String TAG = "MainActivity";

    private MainActivityViewModel mMainActivityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.recycler_view_products);
        mProgressBar = findViewById(R.id.progress_circular);
        mRelativeLayout = findViewById(R.id.error_layout);

        mMainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);

        initRecyclerView();

        subscribeObservers();
    }

    private void subscribeObservers() {
        mMainActivityViewModel.getProducts().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(@Nullable List<Product> products) {

                if (products != null) {
                    mProgressBar.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.VISIBLE);
                    mAdapter.setProducts(products);
                    mMainActivityViewModel.setRetrievedData(true);
                }
            }
        });

        mMainActivityViewModel.getCurrenciesValues().observe(this, new Observer<List<CurrencyValue>>() {
            @Override
            public void onChanged(@Nullable List<CurrencyValue> cV) {
                if (cV != null) {
                    currenciesValues = cV;
                }
            }
        });

        mMainActivityViewModel.isDataFechTimeOut().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean && !mMainActivityViewModel.getDidRetrievedData()) {
                    mProgressBar.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.GONE);
                    mRelativeLayout.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void initRecyclerView() {
        mRelativeLayout.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
        mAdapter = new ProductListAdapter(getApplicationContext(), this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void getProductsApi() {
        mMainActivityViewModel.getProductsApi();
    }

    private void getCurrenciesValuesApi() {
        mMainActivityViewModel.getCurrenciesValuesApi();
    }

    private void gnbApiRequest() {
        getProductsApi();
        getCurrenciesValuesApi();
    }

    @Override
    public void onListItemClick(Product product) {

        Bundle productBundle = new Bundle();
        productBundle.putParcelable(Config.INTENT_KEY_SELECTED_PRODUCT, product);

        ArrayList<CurrencyValue> tmp = new ArrayList<>(currenciesValues);
        productBundle.putParcelableArrayList(Config.INTENT_KEY_CURRENCIES, tmp);

        Intent intent = new Intent(this, ProductActivity.class);
        intent.putExtras(productBundle);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            mProgressBar.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
            mRelativeLayout.setVisibility(View.GONE);
            gnbApiRequest();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
