package com.renovavision.rxmvp.espresso.base.di;

import com.renovavision.rxmvp.espresso.ui.RepositoryActivityTest;
import com.renovavision.rxmvp.presentation.common.di.component.ApplicationComponent;
import com.renovavision.rxmvp.presentation.common.di.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, GitHubTestModule.class,
        RepositoryTestModule.class, UseCaseTestModule.class, TestDataModule.class})
public interface TestComponent extends ApplicationComponent {

    void inject(RepositoryActivityTest repositoryActivityTest);
}

