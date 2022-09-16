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

public class TradeMarketingAddAdapter extends RecyclerView.Adapter<TradeMarketingAddAdapter.MyViewHolder> {

    List<TradeMarketingResponse> list;

    Context context;

    public TradeMarketingAddAdapter(Context context, List<TradeMarketingResponse> list) {
        this.list = list;
        this.context = context;
    }

    public List<TradeMarketingResponse> getArrayList() {
        return list;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView tvSKU;
        public EditText etStockFacing,etStockQuantity,etPrices;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSKU= itemView.findViewById(R.id.tvSKU);
            etStockFacing= itemView.findViewById(R.id.etStockFacing);
            etStockQuantity= itemView.findViewById(R.id.etStockQuantity);
            etPrices= itemView.findViewById(R.id.etPrices);

//
//            try{
//                int Quantity =0,Facing=0,sales=0,closing;
//                if(!etStockQuantity.getText().toString().isEmpty())
//                {
//                    try
//                    {
//                        Quantity = Integer.parseInt(etStockQuantity.getText().toString());
//
//                    }
//                    catch(NumberFormatException ex) { // handle your exception
//                        Quantity=0;
//
//                    }
//
//                }
//                else
//                {
//                    Quantity=0;
//                }
//                if(!etStockFacing.getText().toString().isEmpty())
//                {
//                    try
//                    {
//                        Facing=Integer.parseInt(etStockFacing.getText().toString());
//
//                    }
//                    catch(NumberFormatException ex) { // handle your exception
//                        Facing=0;
//
//                    }
//
//                }
//                else
//                {
//                    Facing=0;
//                }
//
//                if(!etPrices.getText().toString().isEmpty())
//                {
//                    try
//                    {
//                        sales=Integer.parseInt(etPrices.getText().toString());
//
//                    }
//                    catch(NumberFormatException ex) { // handle your exception
//                        sales=0;
//
//                    }
//
//                }
//                else
//                {
//                    sales=0;
//                }
//                closing= Quantity+Facing-sales;
//                Log.d("222",closing+"test");
//                etStockClosing.setText(closing);
//
//            }
//
//            catch(NumberFormatException ex){ // handle your exception
//
//            }
//


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

    public TradeMarketingAddAdapter(List<TradeMarketingResponse> list) {
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
    public void onBindViewHolder(@NonNull TradeMarketingAddAdapter.MyViewHolder holder, int position) {
        TradeMarketingResponse brands= list.get(position);
        holder.tvSKU.setText((brands.getSku()).trim());
        holder.etStockQuantity.setText("");
        holder.etStockFacing.setText("");
        holder.etPrices.setText("");
//        holder.etStockFacing.setFilters(new InputFilter[]{ new InputFilterMinMax("0", String.valueOf(brands.getLimit()))});
//        holder.etStockQuantity.setFilters(new InputFilter[]{ new InputFilterMinMax("0", String.valueOf(brands.getLimit()))});

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
