package com.akuhs.project.eagleeye.dalda.project.activities.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.akuhs.project.eagleeye.dalda.R;
import com.akuhs.project.eagleeye.dalda.project.model.productpricing.ProductPriceResponse;
import com.akuhs.project.eagleeye.dalda.project.model.shop.ShopResponse;

import java.util.ArrayList;
import java.util.List;

public class ProductPriceAdapter extends RecyclerView.Adapter<ProductPriceAdapter.ShopHolder> implements Filterable {
    List<ProductPriceResponse> list;
    List<ProductPriceResponse> listFull;

    public ProductPriceAdapter(List<ProductPriceResponse> list) {

        this.list = list;
        this.listFull= new ArrayList<>(list);

    }

    @NonNull
    @Override
    public ShopHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.eagleeye_activity_product_price_list_row, parent, false);
        return new ShopHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopHolder holder, int position) {
        ProductPriceResponse shop = list.get(position);
        holder.tvProDesc.setText(shop.getProductDescription());
        holder.tvTradePrice.setText(shop.getTradePrice());
        holder.tvRetailPrice.setText(shop.getRetailPrice());
        holder.tvInvoicePrice.setText(shop.getInvoicePrice());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void getAllDatas(List<ProductPriceResponse> list)
    {
        this.list=list;
    }


    public class ShopHolder extends RecyclerView.ViewHolder {

        TextView tvInvoicePrice;
        TextView tvRetailPrice;
        TextView tvTradePrice;
        TextView tvProDesc;

        public ShopHolder(View itemView) {
            super(itemView);
            tvProDesc = itemView.findViewById(R.id.tvProDesc);
            tvTradePrice = itemView.findViewById(R.id.tvTradePrice);
            tvRetailPrice = itemView.findViewById(R.id.tvRetailPrice);
            tvInvoicePrice = itemView.findViewById(R.id.tvInvoicePrice);

        }
    }


    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<ProductPriceResponse> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(listFull);
            } else {
                String filterPattern = constraint.toString();
                for (ProductPriceResponse item : listFull) {
                    if (item.getProductDescription().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            list.clear();
            list.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };




}
