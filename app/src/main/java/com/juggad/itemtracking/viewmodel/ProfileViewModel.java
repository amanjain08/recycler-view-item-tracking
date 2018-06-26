package com.juggad.itemtracking.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.util.Log;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.juggad.itemtracking.data.ProfileModel;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by Aman Jain on 26/06/18.
 */
public class ProfileViewModel extends ViewModel {

    private final static String TAG = ProfileViewModel.class.getSimpleName();

    private MutableLiveData<List<ProfileModel>> mListMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<List<ProfileModel>> getProfileLiveData() {
        return mListMutableLiveData;
    }

    public void fetchProfileData(Context mContext) {
        getObservable("sampledata", mContext)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver());
    }

    private Observer<List<ProfileModel>> getObserver() {
        return new Observer<List<ProfileModel>>() {

            @Override
            public void onSubscribe(final Disposable d) {
            }

            @Override
            public void onNext(final List<ProfileModel> profileModels) {
                Log.d(TAG, "onNext: ");
                mListMutableLiveData.postValue(profileModels);
            }

            @Override
            public void onError(final Throwable e) {
                Log.e(TAG, "onError: ",e );
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: ");
            }
        };
    }

    private Observable<List<ProfileModel>> getObservable(final String fileName, final Context context) {
        return Observable.fromCallable(new Callable<List<ProfileModel>>() {
            @Override
            public List<ProfileModel> call() throws Exception {
                Type listPlaceType = new TypeToken<List<ProfileModel>>() {
                }.getType();
                String json = loadJSONFromAsset(fileName, context);
                List<ProfileModel> profileModelList = new GsonBuilder().create().fromJson(json, listPlaceType);
                return profileModelList;
            }
        });
    }

    private String loadJSONFromAsset(String fileName, Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(fileName + ".json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
