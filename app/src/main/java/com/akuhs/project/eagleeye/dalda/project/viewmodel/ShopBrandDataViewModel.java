package com.akuhs.project.eagleeye.dalda.project.viewmodel;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.akuhs.project.eagleeye.dalda.project.model.brand.ShopBrandData;
import com.akuhs.project.eagleeye.dalda.project.repository.ShopBrandDataRepository;

import java.util.List;

public class ShopBrandDataViewModel extends AndroidViewModel {
    private ShopBrandDataRepository repository;
    public LiveData<List<ShopBrandData>> getAllShopBrandData;

    public ShopBrandDataViewModel(@NonNull Application application) {
        super(application);
        repository=new ShopBrandDataRepository(application);
        getAllShopBrandData=repository.getAllBrandData();
    }

    public void insert(List<ShopBrandData> brandData){
        repository.insert(brandData);
    }

    public LiveData<List<ShopBrandData>> getAllShopBrandData()
    {
        return getAllShopBrandData;
    }
}
