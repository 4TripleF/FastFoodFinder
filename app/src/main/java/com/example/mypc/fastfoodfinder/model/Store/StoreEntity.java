package com.example.mypc.fastfoodfinder.model.Store;

import com.google.android.gms.maps.model.LatLng;

import io.realm.RealmObject;

/**
 * Created by nhoxb on 11/20/2016.
 */
public class StoreEntity extends RealmObject {

    public StoreEntity() {
    }


    public void map(Store store)
    {
        title = store.getTitle();
        address = store.getAddress();
        latitude = Double.parseDouble(store.getLat());
        longitude = Double.parseDouble(store.getLng());
        telephone = store.getTel();
        type = store.getType();
    }

    public String getTitle() {
        return title;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public LatLng getPosition()
    {
        return new LatLng(latitude, longitude);
    }

    public int getType() {
        return type;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    private String title;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    private String address;
    private double latitude;
    private double longitude;
    private String telephone;
    int type;
}
