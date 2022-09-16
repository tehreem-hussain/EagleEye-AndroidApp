package com.akuhs.project.eagleeye.dalda.project.activities.adapters;

import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.akuhs.project.eagleeye.dalda.R;
import com.akuhs.project.eagleeye.dalda.project.model.brand.BrandData;
import com.akuhs.project.eagleeye.dalda.project.model.productpricing.ProductPriceResponse;
import com.akuhs.project.eagleeye.dalda.project.model.stockposition.StockPositionResponse;
import com.akuhs.project.eagleeye.dalda.project.services.InputFilterMinMax;

import java.util.List;

public class StockPositionAddAdapter extends RecyclerView.Adapter<StockPositionAddAdapter.MyViewHolder> {

    List<StockPositionResponse> list;
    Context context;

    public StockPositionAddAdapter(Context context, List<StockPositionResponse> list) {
        this.list = list;
        this.context = context;
    }

    public List<StockPositionResponse> getArrayList() {
        return list;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView tvSKU,etStockClosing;
        public TextView etStockReceiving,etStockOpening,etStockSales;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSKU= itemView.findViewById(R.id.tvSKU);
            etStockReceiving= itemView.findViewById(R.id.etStockReceiving);
            etStockOpening= itemView.findViewById(R.id.etStockOpening);
            etStockSales= itemView.findViewById(R.id.etStockSales);
            etStockClosing= itemView.findViewById(R.id.etStockClosing);

//
//            try{
//                int opening =0,receiving=0,sales=0,closing;
//                if(!etStockOpening.getText().toString().isEmpty())
//                {
//                    try
//                    {
//                        opening = Integer.parseInt(etStockOpening.getText().toString());
//
//                    }
//                    catch(NumberFormatException ex) { // handle your exception
//                        opening=0;
//
//                    }
//
//                }
//                else
//                {
//                    opening=0;
//                }
//                if(!etStockReceiving.getText().toString().isEmpty())
//                {
//                    try
//                    {
//                        receiving=Integer.parseInt(etStockReceiving.getText().toString());
//
//                    }
//                    catch(NumberFormatException ex) { // handle your exception
//                        receiving=0;
//
//                    }
//
//                }
//                else
//                {
//                    receiving=0;
//                }
//
//                if(!etStockSales.getText().toString().isEmpty())
//                {
//                    try
//                    {
//                        sales=Integer.parseInt(etStockSales.getText().toString());
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
//                closing= opening+receiving-sales;
//                Log.d("222",closing+"test");
//                etStockClosing.setText(closing);
//
//            }
//
//            catch(NumberFormatException ex){ // handle your exception
//
//            }
//


            etStockReceiving.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    StockPositionResponse brands1= list.get(getAdapterPosition());
                    brands1.setReceiving(s + "");
                    list.set(getAdapterPosition(),brands1);

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });


            etStockOpening.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    StockPositionResponse brands1= list.get(getAdapterPosition());
                    brands1.setOpening(s + "");
                    list.set(getAdapterPosition(),brands1);
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            etStockSales.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    StockPositionResponse brands1= list.get(getAdapterPosition());
                    brands1.setSales(s + "");
                    list.set(getAdapterPosition(),brands1);
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });



        }
    }

    public StockPositionAddAdapter(List<StockPositionResponse> list) {
        this.list = list;
        notifyDataSetChanged();

    }

    public void getAllDatas(List<StockPositionResponse> list)
    {

        this.list=list;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.stock_position_list_row,parent,false);
        return  new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StockPositionAddAdapter.MyViewHolder holder, int position) {
        StockPositionResponse brands= list.get(position);
        holder.tvSKU.setText((brands.getSku()).trim());
        holder.etStockOpening.setText("");
        holder.etStockReceiving.setText("");
//        holder.etStockSales.setText("");
        holder.setIsRecyclable(false);
//        holder.etStockSales.setText(list.get(position).getEditTextValue());

        holder.etStockSales.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "9000")});

//        holder.etStockReceiving.setFilters(new InputFilter[]{ new InputFilterMinMax("0", String.valueOf(brands.getLimit()))});
//        holder.etStockOpening.setFilters(new InputFilter[]{ new InputFilterMinMax("0", String.valueOf(brands.getLimit()))});


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
