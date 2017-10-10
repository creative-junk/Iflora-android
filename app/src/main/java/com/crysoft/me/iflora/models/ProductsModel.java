package com.crysoft.me.iflora.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Maxx on 10/9/2017.
 */

public class ProductsModel implements Parcelable{
    private String productTitle,imageURL,pricePerStem,color,season,stemLength,quality,growerName,growerId;
    private int numberOfStems,stemsPerBox;

    public ProductsModel(){

    }
    protected ProductsModel(Parcel in) {
        productTitle = in.readString();
        imageURL = in.readString();
        pricePerStem = in.readString();
        color = in.readString();
        season = in.readString();
        stemLength = in.readString();
        quality = in.readString();
        growerName = in.readString();
        growerId = in.readString();
        numberOfStems = in.readInt();
        stemsPerBox = in.readInt();
    }

    public static final Creator<ProductsModel> CREATOR = new Creator<ProductsModel>() {
        @Override
        public ProductsModel createFromParcel(Parcel in) {
            return new ProductsModel(in);
        }

        @Override
        public ProductsModel[] newArray(int size) {
            return new ProductsModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getPricePerStem() {
        return pricePerStem;
    }

    public void setPricePerStem(String pricePerStem) {
        this.pricePerStem = pricePerStem;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getStemLength() {
        return stemLength;
    }

    public void setStemLength(String stemLength) {
        this.stemLength = stemLength;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getGrowerName() {
        return growerName;
    }

    public void setGrowerName(String growerName) {
        this.growerName = growerName;
    }

    public String getGrowerId() {
        return growerId;
    }

    public void setGrowerId(String growerId) {
        this.growerId = growerId;
    }

    public int getNumberOfStems() {
        return numberOfStems;
    }

    public void setNumberOfStems(int numberOfStems) {
        this.numberOfStems = numberOfStems;
    }

    public int getStemsPerBox() {
        return stemsPerBox;
    }

    public void setStemsPerBox(int stemsPerBox) {
        this.stemsPerBox = stemsPerBox;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(productTitle);
        parcel.writeString(imageURL);
        parcel.writeString(pricePerStem);
        parcel.writeString(color);
        parcel.writeString(season);
        parcel.writeString(stemLength);
        parcel.writeString(quality);
        parcel.writeString(growerName);
        parcel.writeString(growerId);
        parcel.writeInt(numberOfStems);
        parcel.writeInt(stemsPerBox);
    }
}
