package com.example.viewmodel;

import android.arch.lifecycle.LiveData;

import com.example.bean.User;
import com.example.repository.UserRepository;

public class UserProfileViewModel {
    private LiveData<User> user;
    private UserRepository userRepo;

    public UserProfileViewModel(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public void init(String userId) {
        if (this.user != null) {
            // ViewModel is created per Fragment so
            // we know the userId won't change
            return;
        }
        user = userRepo.getUser(userId);
    }

    public LiveData<User> getUser() {
        return this.user;
    }
}
