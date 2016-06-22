package com.renovavision.rxmvp.espresso.base;

import com.renovavision.rxmvp.espresso.base.di.DaggerTestComponent;
import com.renovavision.rxmvp.espresso.base.di.GitHubTestModule;
import com.renovavision.rxmvp.espresso.base.di.RepositoryTestModule;
import com.renovavision.rxmvp.espresso.base.di.TestDataModule;
import com.renovavision.rxmvp.espresso.base.di.UseCaseTestModule;
import com.renovavision.rxmvp.presentation.GitHubApp;
import com.renovavision.rxmvp.presentation.common.di.component.ApplicationComponent;
import com.renovavision.rxmvp.presentation.common.di.module.ApplicationModule;

public class TestApplication extends GitHubApp {

    @Override
    protected ApplicationComponent provideAppComponent() {
        return DaggerTestComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .gitHubTestModule(new GitHubTestModule())
                .repositoryTestModule(new RepositoryTestModule())
                .testDataModule(new TestDataModule())
                .useCaseTestModule(new UseCaseTestModule())
                .build();
    }
}