package com.akuhs.project.eagleeye.dalda.project.viewmodel;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.akuhs.project.eagleeye.dalda.project.model.productpricing.ProductPriceResponse;
import com.akuhs.project.eagleeye.dalda.project.repository.ProductPriceRepository;
import java.util.List;

public class ProductPriceViewModel extends AndroidViewModel {
    private ProductPriceRepository repository;
    public LiveData<List<ProductPriceResponse>> getAllProductPrice;

    public ProductPriceViewModel(@NonNull Application application) {
        super(application);
        repository=new ProductPriceRepository(application);
        getAllProductPrice=repository.getAllProductPrice();
    }

    public void insert(List<ProductPriceResponse> productPrice){
        repository.insert(productPrice);
    }

    public LiveData<List<ProductPriceResponse>> getAllProductPrice()
    {
        return getAllProductPrice;
    }
}
