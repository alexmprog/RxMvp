package com.renovavision.rxmvp.presentation.common.di.component;

import android.app.Application;
import android.content.Context;

import com.renovavision.rxmvp.data.api.GitHubService;
import com.renovavision.rxmvp.data.di.GitHubModule;
import com.renovavision.rxmvp.data.di.RepositoryModule;
import com.renovavision.rxmvp.data.repository.GitHubRepository;
import com.renovavision.rxmvp.domain.UseCaseExecutor;
import com.renovavision.rxmvp.domain.di.UseCaseModule;
import com.renovavision.rxmvp.domain.usecase.github.GetRepositoriesUseCase;
import com.renovavision.rxmvp.domain.usecase.github.GetUserUserCase;
import com.renovavision.rxmvp.presentation.common.di.module.ApplicationModule;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Component;
import rx.Scheduler;

@Singleton
@Component(modules = {ApplicationModule.class, RepositoryModule.class,
        GitHubModule.class, UseCaseModule.class})
public interface ApplicationComponent {

    Context provideContext();

    Application provideApplication();

    GitHubService provideGitHubService();

    GitHubRepository provideGitHubRepository();

    GetRepositoriesUseCase provideGetRepositoriesUseCase();

    GetUserUserCase provideGetUserUserCase();

    @Named(UseCaseExecutor.SUBSCRIBE_SCHEDULER)
    Scheduler provideSubscribeScheduler();

    @Named(UseCaseExecutor.OBSERVE_SCHEDULER)
    Scheduler provideObserveScheduler();

    UseCaseExecutor provideUseCaseExecutor();
}
