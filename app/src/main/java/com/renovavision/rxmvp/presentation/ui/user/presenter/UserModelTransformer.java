package com.renovavision.rxmvp.presentation.ui.user.presenter;

import com.renovavision.rxmvp.data.model.Repository;
import com.renovavision.rxmvp.data.model.User;
import com.renovavision.rxmvp.presentation.ui.user.view.UserViewModel;

import rx.functions.Func1;

public class UserModelTransformer implements Func1<User, UserViewModel> {

    private Repository repository;

    public void setRepository(Repository repository) {
        this.repository = repository;
    }

    @Override
    public UserViewModel call(User user) {
        return new UserViewModel(repository, user);
    }
}
