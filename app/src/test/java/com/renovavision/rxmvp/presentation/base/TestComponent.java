package com.renovavision.rxmvp.presentation.base;

import com.renovavision.rxmvp.data.di.GitHubTestModule;
import com.renovavision.rxmvp.data.di.RepositoryTestModule;
import com.renovavision.rxmvp.data.repository.GitHubRepositoryTest;
import com.renovavision.rxmvp.domain.di.UseCaseTestModule;
import com.renovavision.rxmvp.domain.usecase.GetRepositoriesUseCaseTest;
import com.renovavision.rxmvp.domain.usecase.GetUserUseCaseTest;
import com.renovavision.rxmvp.presentation.common.di.component.ApplicationComponent;
import com.renovavision.rxmvp.presentation.common.di.module.ApplicationModule;
import com.renovavision.rxmvp.presentation.ui.RepositoryPresenterTest;
import com.renovavision.rxmvp.presentation.ui.UserPresenterTest;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, GitHubTestModule.class, RepositoryTestModule.class, UseCaseTestModule.class})
public interface TestComponent extends ApplicationComponent {

    void inject(GitHubRepositoryTest dataRepositoryImplTest);

    void inject(GetRepositoriesUseCaseTest getRepositoriesUseCaseTest);

    void inject(GetUserUseCaseTest getUserUseCaseTest);

    void inject(RepositoryPresenterTest repositoryPresenterTest);

    void inject(UserPresenterTest userPresenterTest);
}
