package com.hiisniper.sizer.data;

/**
 * Created by developer on 29/10/2013.
 */
public class Product {
    public String mUUID;
    public String mGroupName;
    public String mBrandImageUri;
    public String mBrandName;
    public String mProductImageUri;
    public String mProductName;
    public String mSize;
    public String mSizeCountryCode;
    public String mAddDate;
    public String mNote;

    public void setUUID(String uuid) {
        mUUID = uuid;
    }

    public String getUUID() {
        return mUUID;
    }

    public void setGroupName(String groupName) {
        mGroupName = groupName;
    }

    public String getGroupName() {
        return mGroupName;
    }

    public void setBrandImageUri(String brandImageUri) {
        mBrandImageUri = brandImageUri;
    }

    public String getBrandImageUri() {
        return mBrandImageUri;
    }

    public void setBrandName(String brandName) {
        mBrandName = brandName;
    }

    public String getBrandName() {
        return mBrandName;
    }

    public void setProductImageUri(String productImageUri) {
        mProductImageUri = productImageUri;
    }

    public String getProductImageUri() {
        return mProductImageUri;
    }

    public void setProductName(String productName) {
        mProductName = productName;
    }

    public String getProductName() {
        return mProductName;
    }

    public void setSize(String size) {
        mSize = size;
    }

    public String getSize() {
        return mSize;
    }

    public void setSizeCountryCode(String sizeCountryCode) {
        mSizeCountryCode = sizeCountryCode;
    }

    public String getSizeCountryCode() {
        return mSizeCountryCode;
    }

    public void setAddDate(String addDate) {
        mAddDate = addDate;
    }

    public String getAddDate() {
        return mAddDate;
    }

    public void setNote(String note) {
        mNote = note;
    }

    public String getNote() {
        return mNote;
    }
}
