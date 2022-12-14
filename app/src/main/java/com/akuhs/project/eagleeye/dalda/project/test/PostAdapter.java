package com.akuhs.project.eagleeye.dalda.project.test;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.akuhs.project.eagleeye.dalda.R;
import com.akuhs.project.eagleeye.dalda.project.model.productpricing.ProductPriceResponse;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder>{

    private Context context;
    private List<ProductPriceResponse> postsList;

    public PostAdapter(Context context, List<ProductPriceResponse> postsList) {
        this.context = context;
        this.postsList = postsList;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PostViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.each_row,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        ProductPriceResponse posts=postsList.get(position);
        holder.id.setText("Id : " +posts.getInvoicePrice()+"pos:"+position);
        holder.name.setText("Name : "+posts.getProductDescription());
        holder.age.setText("Age : "+posts.getTradePrice());

    }

    @Override
    public int getItemCount() {
        return postsList.size();
    }

    public void getAllDatas(List<ProductPriceResponse> postsList)
    {
        this.postsList=postsList;
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder
    {
        public TextView id,name,age;
        public ImageView image;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            id=itemView.findViewById(R.id.id);
            name=itemView.findViewById(R.id.name);
            image=itemView.findViewById(R.id.image);
            age=itemView.findViewById(R.id.age);
        }
    }
}
