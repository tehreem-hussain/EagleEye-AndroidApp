package com.akuhs.project.eagleeye.dalda.project.activities.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.akuhs.project.eagleeye.dalda.R;
import com.akuhs.project.eagleeye.dalda.project.model.brand.BrandData;
import com.akuhs.project.eagleeye.dalda.project.model.shop.ShopResponse;


import java.util.List;

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ShopHolder> {
    List<ShopResponse> list;

    public ShopAdapter(List<ShopResponse> list) {

        this.list = list;

    }


    @NonNull
    @Override
    public ShopHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.eagleeye_act_shop_row, parent, false);
        return new ShopHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopHolder holder, int position) {
        ShopResponse shop = list.get(position);
//
        holder.textViewShopName.setText(shop.getShopName());
        holder.textViewShopAddress.setText(shop.getShopAddress());
        holder.tvShopAddressDetail.setText("Address Details: "+shop.getShop_address_details());
        holder.tvShopDSR.setText("DSR: "+ shop.getDsr());
        holder.tvAddedDate.setText("Date Added: "+shop.getShopDateAdded()+" Time: "+shop.getShopAddedTime());
        String skuAvailable="SKU Available: ";
        String skuOrder="SKU Order: ";
        holder.tvSKUAvailable.setText(skuAvailable+ String.valueOf(shop.getShopTotalSkuAvailable()));
        holder.SKUorder.setText(skuOrder+ String.valueOf(shop.getShopTotalSkuOrder()));
        holder.tvShopCategory.setText("Category: "+shop.getCategoryName());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void getAllDatas(List<ShopResponse> list)
    {
        this.list=list;
    }


    public class ShopHolder extends RecyclerView.ViewHolder {
        TextView textViewShopName;
        TextView textViewShopAddress;
        TextView tvAddedDate;
        TextView tvSKUAvailable;
        TextView SKUorder;
        TextView tvShopCategory;
        TextView tvShopDSR;
        TextView tvShopAddressDetail;

        public ShopHolder(View itemView) {
            super(itemView);
            textViewShopName = itemView.findViewById(R.id.tvShopName);
            textViewShopAddress = itemView.findViewById(R.id.tvShopAddress);
            tvShopAddressDetail = itemView.findViewById(R.id.tvShopAddressDetail);
            tvAddedDate = itemView.findViewById(R.id.tvAddedDate);
            tvSKUAvailable = itemView.findViewById(R.id.tvSKUAvailable);
            SKUorder = itemView.findViewById(R.id.SKUorder);
            tvShopCategory = itemView.findViewById(R.id.tvShopCategory);
            tvShopDSR = itemView.findViewById(R.id.tvShopDSR);

        }
    }

}
