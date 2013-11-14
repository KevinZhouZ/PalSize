package com.hiisniper.sizer.activity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import com.hiisniper.sizer.R;
import com.hiisniper.sizer.data.Product;
import com.hiisniper.sizer.global.Global;
import com.hiisniper.sizer.provider.SizerProvider;

import java.util.UUID;

/**
 * Created by developer on 29/10/2013.
 */
public class SplashActivity extends Activity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_splash);

        if (Global.getIsFirstTimeOpenApp(SplashActivity.this)) {

            Global.setIsFirstTimeOpenApp(SplashActivity.this, false);

            // Init database
            initDatabase();
        }

        Intent homeIntent = new Intent(this, HomeActivity.class);
//            mainIntent.putExtra(PasskeyActivity.SPLASH_OPEN_KEY, 0);
        SplashActivity.this.startActivity(homeIntent);
        SplashActivity.this.finish();
    }

    private void initDatabase() {
        // Init head database
        initHeadDatabase();

        // Init head database
        initUpperDatabase();

        // Init head database
        initLowerDatabase();

        // Init head database
        initShoesDatabase();
    }

    private void initHeadDatabase() {
        Product product = new Product();

        product.setUUID(UUID.randomUUID().toString());
        product.setGroupName("Hat");
        product.setBrandImageUri("");
        product.setBrandName("");
        product.setProductImageUri("");
        product.setProductName("");
        product.setSize("");
        product.setSizeCountryCode("");
        product.setAddDate("");
        product.setNote("");

        ContentValues values = new ContentValues();

        values.put(SizerProvider.COLUMN_UUID, product.getUUID());
        values.put(SizerProvider.COLUMN_GROUP_NAME, product.getGroupName());
        values.put(SizerProvider.COLUMN_BRAND_IMAGE_URI, product.getBrandImageUri());
        values.put(SizerProvider.COLUMN_BRAND_NAME, product.getBrandName());
        values.put(SizerProvider.COLUMN_PRODUCT_IMAGE_URI, product.getProductImageUri());
        values.put(SizerProvider.COLUMN_PRODUCT_NAME, product.getProductName());
        values.put(SizerProvider.COLUMN_SIZE, product.getSize());
        values.put(SizerProvider.COLUMN_SIZE_COUNTRY_CODE, product.getSizeCountryCode());
        values.put(SizerProvider.COLUMN_ADD_DATE, product.getAddDate());
        values.put(SizerProvider.COLUMN_NOTE, product.getNote());

        getContentResolver().insert(Global.getHeadContentURI(this), values);
    }

    private void initUpperDatabase() {
        Product product = new Product();

        product.setUUID(UUID.randomUUID().toString());
        product.setGroupName("Shirt");
        product.setBrandImageUri("");
        product.setBrandName("");
        product.setProductImageUri("");
        product.setProductName("");
        product.setSize("");
        product.setSizeCountryCode("");
        product.setAddDate("");
        product.setNote("");

        ContentValues values = new ContentValues();

        values.put(SizerProvider.COLUMN_UUID, product.getUUID());
        values.put(SizerProvider.COLUMN_GROUP_NAME, product.getGroupName());
        values.put(SizerProvider.COLUMN_BRAND_IMAGE_URI, product.getBrandImageUri());
        values.put(SizerProvider.COLUMN_BRAND_NAME, product.getBrandName());
        values.put(SizerProvider.COLUMN_PRODUCT_IMAGE_URI, product.getProductImageUri());
        values.put(SizerProvider.COLUMN_PRODUCT_NAME, product.getProductName());
        values.put(SizerProvider.COLUMN_SIZE, product.getSize());
        values.put(SizerProvider.COLUMN_SIZE_COUNTRY_CODE, product.getSizeCountryCode());
        values.put(SizerProvider.COLUMN_ADD_DATE, product.getAddDate());
        values.put(SizerProvider.COLUMN_NOTE, product.getNote());

        getContentResolver().insert(Global.getUpperContentURI(this), values);
    }

    private void initLowerDatabase() {
        Product product = new Product();

        product.setUUID(UUID.randomUUID().toString());
        product.setGroupName("Pants");
        product.setBrandImageUri("");
        product.setBrandName("");
        product.setProductImageUri("");
        product.setProductName("");
        product.setSize("");
        product.setSizeCountryCode("");
        product.setAddDate("");
        product.setNote("");

        ContentValues values = new ContentValues();

        values.put(SizerProvider.COLUMN_UUID, product.getUUID());
        values.put(SizerProvider.COLUMN_GROUP_NAME, product.getGroupName());
        values.put(SizerProvider.COLUMN_BRAND_IMAGE_URI, product.getBrandImageUri());
        values.put(SizerProvider.COLUMN_BRAND_NAME, product.getBrandName());
        values.put(SizerProvider.COLUMN_PRODUCT_IMAGE_URI, product.getProductImageUri());
        values.put(SizerProvider.COLUMN_PRODUCT_NAME, product.getProductName());
        values.put(SizerProvider.COLUMN_SIZE, product.getSize());
        values.put(SizerProvider.COLUMN_SIZE_COUNTRY_CODE, product.getSizeCountryCode());
        values.put(SizerProvider.COLUMN_ADD_DATE, product.getAddDate());
        values.put(SizerProvider.COLUMN_NOTE, product.getNote());

        getContentResolver().insert(Global.getLowerContentURI(this), values);
    }

    private void initShoesDatabase() {
        Product product = new Product();

        product.setUUID(UUID.randomUUID().toString());
        product.setGroupName("Sneaker");
        product.setBrandImageUri("");
        product.setBrandName("");
        product.setProductImageUri("");
        product.setProductName("");
        product.setSize("");
        product.setSizeCountryCode("");
        product.setAddDate("");
        product.setNote("");

        ContentValues values = new ContentValues();

        values.put(SizerProvider.COLUMN_UUID, product.getUUID());
        values.put(SizerProvider.COLUMN_GROUP_NAME, product.getGroupName());
        values.put(SizerProvider.COLUMN_BRAND_IMAGE_URI, product.getBrandImageUri());
        values.put(SizerProvider.COLUMN_BRAND_NAME, product.getBrandName());
        values.put(SizerProvider.COLUMN_PRODUCT_IMAGE_URI, product.getProductImageUri());
        values.put(SizerProvider.COLUMN_PRODUCT_NAME, product.getProductName());
        values.put(SizerProvider.COLUMN_SIZE, product.getSize());
        values.put(SizerProvider.COLUMN_SIZE_COUNTRY_CODE, product.getSizeCountryCode());
        values.put(SizerProvider.COLUMN_ADD_DATE, product.getAddDate());
        values.put(SizerProvider.COLUMN_NOTE, product.getNote());

        getContentResolver().insert(Global.getShoesContentURI(this), values);
    }

}
