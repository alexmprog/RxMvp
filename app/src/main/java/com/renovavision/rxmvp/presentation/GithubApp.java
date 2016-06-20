package com.renovavision.rxmvp.presentation;

import android.app.Application;
import android.content.Context;

import com.renovavision.rxmvp.BuildConfig;
import com.renovavision.rxmvp.data.di.GitHubModule;
import com.renovavision.rxmvp.data.di.RepositoryModule;
import com.renovavision.rxmvp.domain.di.UseCaseModule;
import com.renovavision.rxmvp.presentation.common.di.component.ApplicationComponent;
import com.renovavision.rxmvp.presentation.common.di.component.DaggerApplicationComponent;
import com.renovavision.rxmvp.presentation.common.di.module.ApplicationModule;

import timber.log.Timber;

public class GitHubApp extends Application {

    ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        applicationComponent = DaggerApplicationComponent.builder()
                .repositoryModule(new RepositoryModule())
                .gitHubModule(new GitHubModule())
                .useCaseModule(new UseCaseModule())
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public static GitHubApp get(Context context) {
        return (GitHubApp) context.getApplicationContext();
    }

    public ApplicationComponent getComponent() {
        return applicationComponent;
    }
}
