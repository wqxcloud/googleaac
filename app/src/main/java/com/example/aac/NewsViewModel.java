package com.example.aac;

import android.app.Application;
import android.arch.core.util.Function;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.support.annotation.NonNull;
import android.util.Log;
import com.example.bean.NewsDataVo;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;


import java.util.concurrent.TimeUnit;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.observers.ConsumerSingleObserver;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class NewsViewModel extends AndroidViewModel {
    private static String TAG = "NewsViewModel";
    private MutableLiveData<NewsDataVo> mData;

    private LiveData<NewsDataVo> switchDataMap;

    LiveData<NewsDataVo> getSwitchDataMap() {
        return switchDataMap;
    }

    public NewsViewModel(@NonNull Application application) {
        super(application);
        Log.i(TAG, "NewsViewModel: application:"+application);
        mData = new MutableLiveData<>();
        switchDataMap = Transformations.switchMap(mData, new Function<NewsDataVo, LiveData<NewsDataVo>>() {
            @Override
            public LiveData<NewsDataVo> apply(NewsDataVo input) {
                MutableLiveData<NewsDataVo> temp = new MutableLiveData<>();
                temp.setValue(input);
                return temp;
            }
        });

    }
    /**
     * 模拟获取网络数据
     */
    void httpGetData() {
        int len = 1000;
        for (int i = 0; i < len; i++) {
            NewsDataVo dataVo = new NewsDataVo();
            dataVo.setId("1223" + i);
            dataVo.setNewsTitle("Android AccArchitecture框架简介" + i);
            dataVo.setNewsContent("Android Architecture Components,简称 AAC,一个处理UI的生命周期与数据的持久化的架构" + i);
            dataVo.setReadNum(i);
            mData.setValue(dataVo);
            Log.i(TAG, "httpGetData: i="+i);
        }
        Observable.interval(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        //AutoDispose的使用就是这句
                //.as(AutoDispose.<Long>autoDisposable(AndroidLifecycleScopeProvider.from(this)))
                .subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Long aLong) {
                Log.i("接收数据,当前线程"+Thread.currentThread().getName(), String.valueOf(aLong));
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });










    }
}
