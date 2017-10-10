package com.crysoft.me.iflora.adapters;

/**
 * Created by Maxx on 10/6/2017.
 */

public class DirectProduct {
    private String title,pricePerStem,rating;
    private int mainImage;

    public DirectProduct(){

    }
    public DirectProduct(final String title, String pricePerStem, String rating, int mainImage){
        this.title =title;
        this.pricePerStem = pricePerStem;
        this.rating = rating;
        this.mainImage = mainImage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPricePerStem() {
        return pricePerStem;
    }

    public void setPricePerStem(String pricePerStem) {
        this.pricePerStem = pricePerStem;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public int getMainImage() {
        return mainImage;
    }

    public void setMainImage(int mainImage) {
        this.mainImage = mainImage;
    }
}
