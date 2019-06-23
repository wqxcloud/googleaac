package com.example.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.example.api.WebService;
import com.example.bean.User;
import com.example.bean.UserCache;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {
    private WebService webService;
    // simple in memory cache, details omitted for brevity(简单的内存缓存)
    private UserCache userCache;

    public LiveData<User> getUser(String userId) {

//        LiveData<User> cached = userCache.get(userId);
//        if (cached != null) {
//            return cached;
//        }



        // This is not an optimal implementation, we'll fix it below（这不是最好的实现，以后会修复的）
        final MutableLiveData<User> data = new MutableLiveData<>();
        webService.getUser(userId).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
        return data;
    }

}
