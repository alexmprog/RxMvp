package com.renovavision.rxmvp.espresso.base.di;

import com.renovavision.rxmvp.data.repository.GitHubRepository;
import com.renovavision.rxmvp.domain.UseCaseExecutor;
import com.renovavision.rxmvp.domain.usecase.github.GetRepositoriesUseCase;
import com.renovavision.rxmvp.domain.usecase.github.GetUserUseCase;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@Module
public class UseCaseTestModule {

    @Provides
    @Singleton
    @Named(UseCaseExecutor.SUBSCRIBE_SCHEDULER)
    public Scheduler providesSubscribeScheduler() {
        return Schedulers.io();
    }

    @Provides
    @Singleton
    @Named(UseCaseExecutor.OBSERVE_SCHEDULER)
    public Scheduler providesObserveScheduler() {
        return AndroidSchedulers.mainThread();
    }

    @Provides
    @Singleton
    public UseCaseExecutor providesUseCaseExecutor(@Named(UseCaseExecutor.SUBSCRIBE_SCHEDULER) Scheduler subscribeScheduler,
                                                   @Named(UseCaseExecutor.OBSERVE_SCHEDULER) Scheduler observerScheduler) {
        return new UseCaseExecutor(subscribeScheduler, observerScheduler);
    }

    @Provides
    public GetUserUseCase providesGetUseCase(GitHubRepository gitHubRepository) {
        return new GetUserUseCase(gitHubRepository);
    }

    @Provides
    public GetRepositoriesUseCase providesGetRepositoriesUseCase(GitHubRepository gitHubRepository) {
        return new GetRepositoriesUseCase(gitHubRepository);
    }
}
