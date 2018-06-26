package com.juggad.itemtracking.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.juggad.itemstracking.R;
import com.juggad.itemtracking.ui.ProfileAdapter.TrackingViewHolder;
import com.juggad.itemtracking.data.ProfileModel;
import com.juggad.itemtracking.data.TrackingItemModel;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subjects.PublishSubject;
import java.util.List;

/**
 * Created by Aman Jain on 26/06/18.
 */
public class ProfileAdapter extends RecyclerView.Adapter<TrackingViewHolder> {

    private List<ProfileModel> mProfileModels;

    private PublishSubject<TrackingItemModel> mPublishSubject = PublishSubject.create();

    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    ProfileAdapter(final List<ProfileModel> profileModels) {
        mProfileModels = profileModels;
    }

    @NonNull
    @Override
    public TrackingViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item, parent, false);
        return new TrackingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TrackingViewHolder holder, final int position) {
        holder.mTrackingItem.setTrackingItemModel(new TrackingItemModel(mProfileModels.get(position), position));
        holder.mName.setText(mProfileModels.get(position).getName());
        holder.mAddress.setText(mProfileModels.get(position).getAddress());
    }

    @Override
    public int getItemCount() {
        return mProfileModels.size();
    }

    public io.reactivex.Observable<TrackingItemModel> viewItem() {
        return mPublishSubject;
    }

    class TrackingViewHolder extends RecyclerView.ViewHolder {

        TextView mName;

        TextView mAddress;

        TrackingItem mTrackingItem;

        TrackingViewHolder(final View itemView) {
            super(itemView);
            mName = itemView.findViewById(R.id.name);
            mAddress = itemView.findViewById(R.id.address);
            mTrackingItem = new TrackingItem(itemView, mPublishSubject, mCompositeDisposable);
        }
    }

    public void disposeAll() {
        mCompositeDisposable.clear();
    }

    public void createCompositeDisposable() {
        if (mCompositeDisposable == null || mCompositeDisposable.isDisposed()) {
            mCompositeDisposable = new CompositeDisposable();
        }
    }


}
