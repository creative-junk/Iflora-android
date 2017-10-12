package com.crysoft.me.iflora.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Maxx on 7/29/2016.
 */
public class CategoryModel implements Parcelable {

    private String categoryName;
    private String categoryImage;
    private String categoryType;

    public CategoryModel(Parcel in) {
        String[] array = new String[6];
        in.readStringArray(array);
        categoryName = array[0];
        categoryImage = array[1];
        categoryType = array[2];
    }
    public CategoryModel(){

    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(String categoryImage) {
        this.categoryImage = categoryImage;
    }


    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }

    public static final Creator<CategoryModel> CREATOR = new Creator<CategoryModel>() {
        @Override
        public CategoryModel createFromParcel(Parcel in) {
            return new CategoryModel(in);
        }

        @Override
        public CategoryModel[] newArray(int size) {
            return new CategoryModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{this.categoryName, this.categoryImage, this.categoryType});
    }


}
