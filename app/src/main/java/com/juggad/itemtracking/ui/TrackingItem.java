package com.juggad.itemtracking.ui;

import android.view.View;
import com.juggad.itemtracking.data.TrackingItemModel;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.subjects.PublishSubject;
import java.util.concurrent.TimeUnit;

/**
 * Created by Aman Jain on 26/06/18.
 */
public class TrackingItem implements View.OnAttachStateChangeListener {

    private Disposable mDisposable;

    private PublishSubject<TrackingItemModel> mPublishSubject;

    private CompositeDisposable mCompositeDisposable;

    private TrackingItemModel mTrackingItemModel;

    /*
    Threshold time to set item as trackable.
     */
    private static final int THRESHOLD_MS = 3000;

    public void setTrackingItemModel(final TrackingItemModel trackingItemModel) {
        mTrackingItemModel = trackingItemModel;
    }

    TrackingItem(View view, final PublishSubject<TrackingItemModel> publishSubject,
            final CompositeDisposable compositeDisposable) {
        mPublishSubject = publishSubject;
        mCompositeDisposable = compositeDisposable;

        view.addOnAttachStateChangeListener(this);
    }

    /*
    When recycler view item show then dispose old observable and set a new observable
     */
    @Override
    public void onViewAttachedToWindow(final View v) {
        if (mDisposable != null) {
            mDisposable.dispose();
        }
        mDisposable = getObservable().subscribeWith(getObserver());
        mCompositeDisposable.add(mDisposable);
    }

    /*
    When recycler view item gets hidden then dispose old observable.
     */
    @Override
    public void onViewDetachedFromWindow(final View v) {
        if (mDisposable != null) {
            mDisposable.dispose();
            mCompositeDisposable.delete(mDisposable);
        }
    }

    /*
    Methods returns observable with a timer operator.
     */
    private Observable<Long> getObservable() {
        return Observable.timer(THRESHOLD_MS, TimeUnit.MILLISECONDS);
    }

    /*
    Method returns observer that will publish data using PublishSubject.
     */
    private DisposableObserver<Long> getObserver() {
        return new DisposableObserver<Long>() {
            @Override
            public void onNext(final Long aLong) {
                mPublishSubject.onNext(mTrackingItemModel);
            }

            @Override
            public void onError(final Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
    }
}
