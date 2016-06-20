package com.renovavision.rxmvp.presentation.ui.user.view;

import com.renovavision.rxmvp.data.model.Repository;
import com.renovavision.rxmvp.data.model.User;
import com.renovavision.rxmvp.presentation.common.view.model.ViewModel;

import java.io.Serializable;

public class UserViewModel implements ViewModel, Serializable {

    private Repository mRepository;

    private User mUser;

    public UserViewModel(Repository repository, User user) {
        this.mRepository = repository;
        this.mUser = user;
    }

    public User getUser() {
        return mUser;
    }

    public Repository getRepository() {
        return mRepository;
    }
}

