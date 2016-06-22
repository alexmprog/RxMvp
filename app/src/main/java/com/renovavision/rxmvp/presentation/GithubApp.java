package com.renovavision.rxmvp.presentation;

import android.app.Application;

import com.renovavision.rxmvp.BuildConfig;
import com.renovavision.rxmvp.data.di.GitHubModule;
import com.renovavision.rxmvp.data.di.RepositoryModule;
import com.renovavision.rxmvp.domain.di.UseCaseModule;
import com.renovavision.rxmvp.presentation.common.di.component.ApplicationComponent;
import com.renovavision.rxmvp.presentation.common.di.component.DaggerApplicationComponent;
import com.renovavision.rxmvp.presentation.common.di.module.ApplicationModule;

import timber.log.Timber;

public class GitHubApp extends Application {

    static ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        applicationComponent = provideAppComponent();
    }

    protected ApplicationComponent provideAppComponent() {
        return DaggerApplicationComponent.builder()
                .repositoryModule(new RepositoryModule())
                .gitHubModule(new GitHubModule())
                .useCaseModule(new UseCaseModule())
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public static ApplicationComponent getComponent() {
        return applicationComponent;
    }
}
