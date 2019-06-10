package com.example.gnbtraders.adapters;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gnbtraders.R;
import com.example.gnbtraders.model.Transaction;

import java.util.List;


public class TransactionListAdapter extends RecyclerView.Adapter<TransactionListAdapter.ViewHolder> {

    private List<Transaction> mData;
    private LayoutInflater mInflater;


    public TransactionListAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.transaction_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.transactionAmount.setText(mData.get(position).getAmount());
        holder.transactionCurrency.setText(mData.get(position).getCurrency());

    }

    @Override
    public int getItemCount() {
        return (mData == null) ? 0 : mData.size();
    }


    public void setProducts(List<Transaction> transactions) {
        mData = transactions;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView transactionName;
        private TextView transactionAmount;
        private TextView transactionCurrency;

        ViewHolder(View itemView) {
            super(itemView);
            transactionName = itemView.findViewById(R.id.text_view_transaction_name);
            transactionAmount = itemView.findViewById(R.id.text_view_transaction_amount);
            transactionCurrency = itemView.findViewById(R.id.text_view_transaction_currency);

        }
    }

}
