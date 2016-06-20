package com.renovavision.rxmvp.presentation.ui.repository.di;

import com.renovavision.rxmvp.presentation.common.di.component.ActivityComponent;
import com.renovavision.rxmvp.presentation.common.di.component.ApplicationComponent;
import com.renovavision.rxmvp.presentation.common.di.module.ActivityModule;
import com.renovavision.rxmvp.presentation.common.di.scope.ActivityScope;
import com.renovavision.rxmvp.presentation.ui.repository.RepositoryActivity;

import dagger.Component;

@ActivityScope
@Component(dependencies = {ApplicationComponent.class}, modules = {ActivityModule.class, RepositoryListModule.class})
public interface RepositoryListComponent extends ActivityComponent {

    void inject(RepositoryActivity repositoryActivity);
}
