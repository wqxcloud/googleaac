package com.example.aac;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.example.bean.User;

/**
 * https://blog.csdn.net/jaikydota163/article/details/78645012
 * https://blog.csdn.net/DaltSoftware/article/details/80362249
 */
public class ShareViewModel extends ViewModel {
    private MutableLiveData<User> mutableLiveData = new MutableLiveData<>();
    //采用非Lambda表达式的定义方式
//    private LiveData<String> mMapData = Transformations.map(mutableLiveData, new Function<User, String>() {
//        @Override
//        public String apply(User value) {
//            return value.getNum() + value.getUnit1() + "---" + value.getUnit2();
//        }
//    });

    //采用Lambda表达式做Transformations.map
    private LiveData<String> mMapData = Transformations.map(mutableLiveData, value -> {
        return value.getName() + value.getAge()+"";
    });

    //采用Lambda表达式做Transformations.switchMap
    private LiveData<String> mSitchMapData = Transformations.switchMap(mutableLiveData, value -> {
        MutableLiveData<String> dataLiveData = new MutableLiveData<>();
        dataLiveData.setValue(value.getName() + value.getAge());
        return dataLiveData;
    });

    public LiveData<String> getSitchMapData() {
        return mSitchMapData;
    }

    public LiveData<String> getMapData() {
        return mMapData;
    }

    public ShareViewModel(){
        setData(new User());
    }

    public void setData(User data) {
        mutableLiveData.setValue(data);
    }

    public void setValue(int value){
        mutableLiveData.getValue().setAge(value);
    }

    public MutableLiveData<User> getData() {
        return mutableLiveData;
    }
}