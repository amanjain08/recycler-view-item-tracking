package com.juggad.itemtracking.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import com.juggad.itemstracking.R;
import com.juggad.itemtracking.data.ProfileModel;
import com.juggad.itemtracking.data.TrackingItemModel;
import com.juggad.itemtracking.viewmodel.ProfileViewModel;
import io.reactivex.observers.DefaultObserver;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    RecyclerView mRecyclerView;

    ProfileAdapter mProfileAdapter;

    ProfileViewModel mProfileViewModel;

    List<ProfileModel> mProfileModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        mProfileViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        setObserver();
        mProfileViewModel.fetchProfileData(this);
    }

    private void setObserver() {
        mProfileViewModel.getProfileLiveData().observe(this, new Observer<List<ProfileModel>>() {
            @Override
            public void onChanged(@Nullable final List<ProfileModel> profileModels) {
                mProfileModels.clear();
                if (profileModels != null) {
                    mProfileModels.addAll(profileModels);
                }
                mProfileAdapter.notifyDataSetChanged();
            }
        });
    }

    private void initView() {
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mProfileAdapter = new ProfileAdapter(mProfileModels);
        mRecyclerView.setAdapter(mProfileAdapter);
        initItemTracking();
    }

    private void initItemTracking() {
        mProfileAdapter.viewItem().subscribe(new DefaultObserver<TrackingItemModel>() {
            @Override
            public void onNext(final TrackingItemModel trackingItemModel) {
                Log.d(TAG, "Tracked Item: " + trackingItemModel.getPosition() + ", Name: " + trackingItemModel
                        .getName());
            }

            @Override
            public void onError(final Throwable e) {
                Log.e(TAG, "onError: ", e);
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: ");
            }
        });
    }

    /*
    Dispose all observable
     */
    @Override
    protected void onPause() {
        super.onPause();
        if (mProfileAdapter != null) {
            mProfileAdapter.disposeAll();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mProfileAdapter != null) {
            mProfileAdapter.createCompositeDisposable();
        }
    }
}
