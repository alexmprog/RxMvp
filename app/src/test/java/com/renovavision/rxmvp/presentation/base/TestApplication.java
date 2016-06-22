package com.renovavision.rxmvp.presentation.base;

import com.renovavision.rxmvp.data.di.GitHubTestModule;
import com.renovavision.rxmvp.data.di.RepositoryTestModule;
import com.renovavision.rxmvp.domain.di.UseCaseTestModule;
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
                .useCaseTestModule(new UseCaseTestModule())
                .build();
    }
}
