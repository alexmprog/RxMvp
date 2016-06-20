package com.renovavision.rxmvp.presentation.ui.user.di;

import com.renovavision.rxmvp.presentation.common.di.component.ActivityComponent;
import com.renovavision.rxmvp.presentation.common.di.component.ApplicationComponent;
import com.renovavision.rxmvp.presentation.common.di.module.ActivityModule;
import com.renovavision.rxmvp.presentation.common.di.scope.ActivityScope;
import com.renovavision.rxmvp.presentation.ui.user.UserActivity;

import dagger.Component;

@ActivityScope
@Component(dependencies = {ApplicationComponent.class}, modules = {ActivityModule.class, UserModule.class})
public interface UserComponent extends ActivityComponent {

    void inject(UserActivity userActivity);
}
