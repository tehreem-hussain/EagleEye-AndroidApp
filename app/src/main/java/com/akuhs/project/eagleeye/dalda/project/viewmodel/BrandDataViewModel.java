package com.akuhs.project.eagleeye.dalda.project.viewmodel;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.akuhs.project.eagleeye.dalda.project.model.brand.BrandData;
import com.akuhs.project.eagleeye.dalda.project.repository.BrandDataRepository;

import java.util.List;

public class BrandDataViewModel extends AndroidViewModel {
    private BrandDataRepository repository;
    public LiveData<List<BrandData>> getAllBrandData;

    public BrandDataViewModel(@NonNull Application application) {
        super(application);
        repository=new BrandDataRepository(application);
        getAllBrandData=repository.getAllBrandData();
    }

    public void insert(List<BrandData> brandData){
        repository.insert(brandData);
    }

    public LiveData<List<BrandData>> getAllBrandData()
    {
        return getAllBrandData;
    }
}
