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
        int len = 10;
        for (int i = 0; i < len; i++) {
            NewsDataVo dataVo = new NewsDataVo();
            dataVo.setId("1223" + i);
            dataVo.setNewsTitle("Android AccArchitecture框架简介" + i);
            dataVo.setNewsContent("Android Architecture Components,简称 AAC,一个处理UI的生命周期与数据的持久化的架构" + i);
            dataVo.setReadNum(i);
            mData.setValue(dataVo);
        }
    }
}
