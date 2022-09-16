package com.akuhs.project.eagleeye.dalda.project.viewmodel;


import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.akuhs.project.eagleeye.dalda.project.model.shop.ShopResponse;
import com.akuhs.project.eagleeye.dalda.project.repository.ShopDataRepository;
import java.util.List;

public class ShopDataViewModel extends AndroidViewModel {
    private ShopDataRepository repository;
    public LiveData<List<ShopResponse>> getAllShopResponse;
    public LiveData<List<ShopResponse>> getAllShopResponseFalse;

    public ShopDataViewModel(@NonNull Application application) {
        super(application);
        repository=new ShopDataRepository(application);
        getAllShopResponse=repository.getAllShopResponse();
        getAllShopResponseFalse=repository.getAllShopResponseFalse();

    }

    public void insert(ShopResponse brandData){
        repository.insert(brandData);
    }

    public LiveData<List<ShopResponse>> getAllShopResponse()
    {
        return getAllShopResponse;
    }
    public LiveData<List<ShopResponse>> getAllShopResponseFalse()
    {
        return getAllShopResponseFalse;
    }
}
