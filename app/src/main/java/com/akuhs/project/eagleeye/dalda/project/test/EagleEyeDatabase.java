package com.akuhs.project.eagleeye.dalda.project.test;

import android.content.Context;
import android.os.AsyncTask;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import com.akuhs.project.eagleeye.dalda.project.dao.BrandDataDao;
import com.akuhs.project.eagleeye.dalda.project.dao.ProductPriceDao;
import com.akuhs.project.eagleeye.dalda.project.dao.ShopBrandDataDao;
import com.akuhs.project.eagleeye.dalda.project.dao.ShopDao;
import com.akuhs.project.eagleeye.dalda.project.model.brand.BrandData;
import com.akuhs.project.eagleeye.dalda.project.model.brand.ShopBrandData;
import com.akuhs.project.eagleeye.dalda.project.model.productpricing.ProductPriceResponse;
import com.akuhs.project.eagleeye.dalda.project.model.shop.ShopResponse;


@Database(entities = {ProductPriceResponse.class, BrandData.class, ShopResponse.class, ShopBrandData.class},version = 11)
public abstract class EagleEyeDatabase extends RoomDatabase {

    private static final String DATABASE_NAME="eagleEye";
    public abstract ProductPriceDao productPriceDao();
    public abstract BrandDataDao brandDataDao();
    public abstract ShopDao shopDataDao();
    public abstract ShopBrandDataDao shopBrandDataDao();

    public static volatile EagleEyeDatabase INSTANCE=null;

    public static EagleEyeDatabase getInstance(Context context){
        if(INSTANCE == null){
            synchronized(EagleEyeDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context, EagleEyeDatabase.class, DATABASE_NAME)
                            .addCallback(callback)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }

        }
        return INSTANCE;
    }
    public static Callback callback=new Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateDbAsyn(INSTANCE);

        }
    };

    static  class  PopulateDbAsyn extends AsyncTask<Void,Void,Void>{
        private ProductPriceDao productPriceDao;
        private BrandDataDao brandDataDao;
        private ShopDao shopDataDao;
        private ShopBrandDataDao shopBrandDataDao;

        public PopulateDbAsyn(EagleEyeDatabase eagleEyeDatabase)
        {
            productPriceDao= eagleEyeDatabase.productPriceDao();
            brandDataDao= eagleEyeDatabase.brandDataDao();
            shopDataDao= eagleEyeDatabase.shopDataDao();
            shopBrandDataDao= eagleEyeDatabase.shopBrandDataDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
//            productPriceDao.deleteAll();
//            brandDataDao.deleteAll();
//            shopDataDao.deleteAll();
            return null;
        }
    }

}
