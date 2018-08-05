package com.example.tommy.demoapp10;

import android.os.Parcel;
import android.os.Parcelable;

// TODO: Add Product's Bitmap (상품 이미지)

public class Product implements Parcelable {
    private String prod_link;
    private String name;
    private String price;
    private String desc;
    private String quote;
    private String thumbnail;

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    private Product(Parcel parcel){
        this.prod_link = parcel.readString();
        this.name = parcel.readString();
        this.price = parcel.readString();
        this.desc = parcel.readString();
        this.quote = parcel.readString();
        this.thumbnail = parcel.readString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i){
        parcel.writeString(this.prod_link);
        parcel.writeString(this.name);
        parcel.writeString(this.price);
        parcel.writeString(this.desc);
        parcel.writeString(this.quote);
        parcel.writeString(this.thumbnail);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getProdLink() {
        return prod_link;
    }
    public String getName() {
        return name;
    }
    public String getPrice() {
        return price;
    }
    public String getDesc() {
        return desc;
    }
    public String getQuote() { return quote; }
    public String getThumbnail() { return thumbnail; }
}
