package com.renovavision.rxmvp.presentation.ui.repository.di;

import android.support.annotation.NonNull;

import com.renovavision.rxmvp.domain.UseCaseExecutor;
import com.renovavision.rxmvp.domain.usecase.github.GetRepositoriesUseCase;
import com.renovavision.rxmvp.presentation.ui.repository.RepositoryAdapter;
import com.renovavision.rxmvp.presentation.ui.repository.presenter.RepositoryModelTransformer;
import com.renovavision.rxmvp.presentation.ui.repository.presenter.RepositoryPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryListModule {

    @Provides
    public RepositoryModelTransformer providesRepositoryModelTransformer() {
        return new RepositoryModelTransformer();
    }

    @Provides
    public RepositoryPresenter providesRepositoryPresenter(@NonNull GetRepositoriesUseCase getRepositoriesUseCase, @NonNull UseCaseExecutor useCaseExecutor,
                                                           @NonNull RepositoryModelTransformer repositoryModelTransformer) {
        return new RepositoryPresenter(getRepositoriesUseCase, useCaseExecutor, repositoryModelTransformer);
    }

    @Provides
    public RepositoryAdapter providesRepositoryAdapter() {
        return new RepositoryAdapter();
    }
}
