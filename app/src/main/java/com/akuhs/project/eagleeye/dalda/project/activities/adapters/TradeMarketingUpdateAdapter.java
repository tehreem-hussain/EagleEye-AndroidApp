package com.akuhs.project.eagleeye.dalda.project.activities.adapters;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.akuhs.project.eagleeye.dalda.R;
import com.akuhs.project.eagleeye.dalda.project.model.stockposition.StockPositionResponse;
import com.akuhs.project.eagleeye.dalda.project.model.tradeMarketing.TradeMarketingResponse;

import java.util.List;

public class TradeMarketingUpdateAdapter extends RecyclerView.Adapter<TradeMarketingUpdateAdapter.MyViewHolder> {

    List<TradeMarketingResponse> list;

    Context context;
    private int closing;

    public TradeMarketingUpdateAdapter(Context context, List<TradeMarketingResponse> list) {
        this.list = list;
        this.context = context;
    }

    public List<TradeMarketingResponse> getArrayList() {
        return list;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView tvSKU,etStockClosing;
        public EditText etStockFacing,etStockQuantity,etPrices;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSKU= itemView.findViewById(R.id.tvSKU);
            etStockFacing= itemView.findViewById(R.id.etStockFacing);
            etStockQuantity= itemView.findViewById(R.id.etStockQuantity);
            etPrices= itemView.findViewById(R.id.etPrices);


            etStockFacing.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    TradeMarketingResponse brands1= list.get(getAdapterPosition());
                    brands1.setfacing(s + "");
                    list.set(getAdapterPosition(),brands1);

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });


            etStockQuantity.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    TradeMarketingResponse brands1= list.get(getAdapterPosition());
                    brands1.setquantity(s + "");
                    list.set(getAdapterPosition(),brands1);
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            etPrices.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    TradeMarketingResponse brands1= list.get(getAdapterPosition());
                    brands1.setprices(s + "");
                    list.set(getAdapterPosition(),brands1);
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });



        }
    }

    public TradeMarketingUpdateAdapter(List<TradeMarketingResponse> list) {
        this.list = list;
        notifyDataSetChanged();

    }

    public void getAllDatas(List<TradeMarketingResponse> list)
    {

        this.list=list;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.trade_marketing_list_row,parent,false);
        return  new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TradeMarketingUpdateAdapter.MyViewHolder holder, int position) {
        TradeMarketingResponse brands= list.get(position);
        holder.tvSKU.setText((brands.getSku()).trim());
        holder.etStockQuantity.setText(brands.getquantity());
        holder.etStockFacing.setText(brands.getfacing());
        holder.etPrices.setText(brands.getprices());

   }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
