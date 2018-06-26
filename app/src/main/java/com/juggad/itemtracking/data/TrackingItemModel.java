package com.juggad.itemtracking.data;

/**
 * Created by Aman Jain on 26/06/18.
 */
public class TrackingItemModel extends ProfileModel {

   private int position;

    public TrackingItemModel(ProfileModel model, int position) {
        super(model.getName(), model.getAddress());
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(final int position) {
        this.position = position;
    }
}
