package com.akuhs.project.eagleeye.dalda.project.activities.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.akuhs.project.eagleeye.dalda.R;
import com.akuhs.project.eagleeye.dalda.project.activities.store.ActUpdateStockPosition;
import com.akuhs.project.eagleeye.dalda.project.activities.trademarketing.ActUpdateTradeMarketing;
import com.akuhs.project.eagleeye.dalda.project.model.getSalesShopDetail.GetSalesShopResponse;

import java.util.List;

public class SalesShopTMViewAdapter extends RecyclerView.Adapter<SalesShopTMViewAdapter.ShopHolder> {
    List<GetSalesShopResponse> list;

    public SalesShopTMViewAdapter(List<GetSalesShopResponse> list) {

        this.list = list;

    }


    @NonNull
    @Override
    public ShopHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.act_view_stock_shops_item, parent, false);
        return new ShopHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopHolder holder, int position) {
        GetSalesShopResponse shop = list.get(position);
//
        holder.textViewShopName.setText(shop.getShopName());
        holder.textViewShopAddress.setText(shop.getShopAddress());
        holder.tvAddedDate.setText("Date Added: "+shop.getShopDateAdded()+" Time: "+shop.getShopAddedTime());
        holder.llViewShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(), ActUpdateTradeMarketing.class);
                intent.putExtra("shop_key",shop.getShopKey());
                v.getContext().startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void getAllDatas(List<GetSalesShopResponse> list)
    {
        this.list=list;
    }


    public class ShopHolder extends RecyclerView.ViewHolder {
        TextView textViewShopName;
        TextView textViewShopAddress;
        TextView tvAddedDate;
        LinearLayout llViewShop;


        public ShopHolder(View itemView) {
            super(itemView);
            textViewShopName = itemView.findViewById(R.id.tvShopName);
            textViewShopAddress = itemView.findViewById(R.id.tvShopAddress);
            tvAddedDate = itemView.findViewById(R.id.tvAddedDate);
            llViewShop = itemView.findViewById(R.id.llViewShop);


        }
    }

}
