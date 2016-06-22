package com.renovavision.rxmvp.domain.di;

import com.renovavision.rxmvp.data.repository.GitHubRepository;
import com.renovavision.rxmvp.domain.UseCaseExecutor;
import com.renovavision.rxmvp.domain.usecase.github.GetRepositoriesUseCase;
import com.renovavision.rxmvp.domain.usecase.github.GetUserUseCase;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rx.Scheduler;
import rx.schedulers.Schedulers;

import static org.mockito.Mockito.mock;

@Module
public class UseCaseTestModule {

    @Provides
    @Singleton
    @Named(UseCaseExecutor.SUBSCRIBE_SCHEDULER)
    public Scheduler providesSubscribeScheduler() {
        return Schedulers.immediate();
    }

    @Provides
    @Singleton
    @Named(UseCaseExecutor.OBSERVE_SCHEDULER)
    public Scheduler providesObserveScheduler() {
        return Schedulers.immediate();
    }

    @Provides
    @Singleton
    public UseCaseExecutor providesUseCaseExecutor(@Named(UseCaseExecutor.SUBSCRIBE_SCHEDULER) Scheduler subscribeScheduler,
                                                   @Named(UseCaseExecutor.OBSERVE_SCHEDULER) Scheduler observerScheduler) {
        return new UseCaseExecutor(subscribeScheduler, observerScheduler);
    }

    @Provides
    public GetUserUseCase providesGetUseCase(GitHubRepository gitHubRepository) {
        return mock(GetUserUseCase.class);
    }

    @Provides
    public GetRepositoriesUseCase providesGetRepositoriesUseCase(GitHubRepository gitHubRepository) {
        return mock(GetRepositoriesUseCase.class);
    }
}
