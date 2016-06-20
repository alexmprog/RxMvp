package com.renovavision.rxmvp.presentation.common.di.module;

import android.app.Application;
import android.content.Context;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    protected final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    public Application providesApplication() {
        return mApplication;
    }

    @Provides
    public Context providesContext() {
        return mApplication;
    }

}