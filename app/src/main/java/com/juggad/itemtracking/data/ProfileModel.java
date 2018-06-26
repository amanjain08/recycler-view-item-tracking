package com.juggad.itemtracking.data;

/**
 * Created by Aman Jain on 26/06/18.
 */

public class ProfileModel {

    private String name;

    private String address;

    public ProfileModel(final String name, final String address) {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(final String address) {
        this.address = address;
    }
}
