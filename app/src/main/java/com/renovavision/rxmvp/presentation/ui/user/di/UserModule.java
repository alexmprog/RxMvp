package com.renovavision.rxmvp.presentation.ui.user.di;

import android.support.annotation.NonNull;

import com.renovavision.rxmvp.domain.UseCaseExecutor;
import com.renovavision.rxmvp.domain.usecase.github.GetUserUserCase;
import com.renovavision.rxmvp.presentation.ui.user.presenter.UserModelTransformer;
import com.renovavision.rxmvp.presentation.ui.user.presenter.UserPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class UserModule {

    @Provides
    public UserPresenter providesUserPresenter(@NonNull GetUserUserCase getUserUserCase, @NonNull UseCaseExecutor useCaseExecutor,
                                               @NonNull UserModelTransformer userModelTransformer) {
        return new UserPresenter(getUserUserCase, useCaseExecutor, userModelTransformer);
    }

    @Provides
    public UserModelTransformer providesUserModelTransformer() {
        return new UserModelTransformer();
    }
}
