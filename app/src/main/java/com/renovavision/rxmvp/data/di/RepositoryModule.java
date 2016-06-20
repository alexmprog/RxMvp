package com.renovavision.rxmvp.data.di;

import com.renovavision.rxmvp.data.api.GitHubService;
import com.renovavision.rxmvp.data.repository.GitHubRepository;
import com.renovavision.rxmvp.data.repository.GitHubRepositoryImpl;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {

    @Provides
    GitHubRepository providesGitHubRepository(GitHubService gitHubService) {
        return new GitHubRepositoryImpl(gitHubService);
    }
}
