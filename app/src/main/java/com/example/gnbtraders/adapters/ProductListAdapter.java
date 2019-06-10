package com.example.gnbtraders.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.gnbtraders.R;
import com.example.gnbtraders.model.Product;

import java.util.List;



public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder> {

    private List<Product> mData;
    private LayoutInflater mInflater;
    private ListItemClickListener mOnClickListener;


    public interface ListItemClickListener {
        void onListItemClick (Product product);
    }

    public ProductListAdapter(Context context, ListItemClickListener listener) {
        this.mInflater = LayoutInflater.from(context);
        mOnClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.product_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.productName.setText(mData.get(position).getSku());

    }

    @Override
    public int getItemCount() {
        return (mData == null) ? 0 : mData.size();
    }


    public void setProducts(List<Product> products) {
        mData = products;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView productName;

        ViewHolder(View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.text_view_product_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(mData.get(clickedPosition));
        }
    }

}
