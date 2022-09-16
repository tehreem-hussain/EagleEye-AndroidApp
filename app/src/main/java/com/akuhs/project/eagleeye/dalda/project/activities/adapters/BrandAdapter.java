package com.akuhs.project.eagleeye.dalda.project.activities.adapters;

import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.akuhs.project.eagleeye.dalda.R;
import com.akuhs.project.eagleeye.dalda.project.model.brand.BrandData;
import com.akuhs.project.eagleeye.dalda.project.model.productpricing.ProductPriceResponse;
import com.akuhs.project.eagleeye.dalda.project.services.InputFilterMinMax;

import java.util.List;

public class BrandAdapter extends RecyclerView.Adapter<BrandAdapter.MyViewHolder> {

    private List<BrandData> brandsList;
    Context context;

    public BrandAdapter(Context context, List<BrandData> brandsList) {
        this.brandsList = brandsList;
        this.context = context;
    }

    public List<BrandData> getArrayList() {
        return brandsList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView tvBrandNum, tvBrandABR,tvBrandName,etSkuAvailable,etSkuOrder;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBrandABR= itemView.findViewById(R.id.tvBrandABR);
            etSkuAvailable= itemView.findViewById(R.id.etSkuAvial);
            etSkuOrder= itemView.findViewById(R.id.etSkuOrder);

            etSkuAvailable.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    BrandData brands1= brandsList.get(getAdapterPosition());
                    brands1.setBrandSku(s + "");
                    brandsList.set(getAdapterPosition(),brands1);

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });


            etSkuOrder.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    BrandData brands1= brandsList.get(getAdapterPosition());
                    brands1.setBrandOrder(s + "");
                    brandsList.set(getAdapterPosition(),brands1);
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

        }
    }

    public BrandAdapter(List<BrandData> brandsList) {
        this.brandsList = brandsList;
        notifyDataSetChanged();

    }

    public void getAllDatas(List<BrandData> list)
    {

        this.brandsList=list;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.brand_list_row,parent,false);
        return  new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BrandAdapter.MyViewHolder holder, int position) {
        BrandData brands= brandsList.get(position);
        holder.tvBrandABR.setText((brands.getBrandAbr()));
        holder.etSkuAvailable.setFilters(new InputFilter[]{ new InputFilterMinMax("0", String.valueOf(brands.getLimit()))});
        holder.etSkuOrder.setFilters(new InputFilter[]{ new InputFilterMinMax("0", String.valueOf(brands.getLimit()))});

   }

    @Override
    public int getItemCount() {
        return brandsList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
