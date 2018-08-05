package com.example.tommy.demoapp10;

import android.os.Parcel;
import android.os.Parcelable;

// TODO: Add Product's Bitmap (상품 이미지)

public class Product implements Parcelable {
    private String link;
    private String name;
    private String price;
    private String description;

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
        this.link = parcel.readString();
        this.name = parcel.readString();
        this.price = parcel.readString();
        this.description = parcel.readString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i){
        parcel.writeString(this.link);
        parcel.writeString(this.name);
        parcel.writeString(this.price);
        parcel.writeString(this.description);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getLink() {
        return link;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }
}
