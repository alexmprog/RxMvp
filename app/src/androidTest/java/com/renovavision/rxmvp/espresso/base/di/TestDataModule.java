package com.renovavision.rxmvp.espresso.base.di;

import com.renovavision.rxmvp.data.api.GitHubService;
import com.renovavision.rxmvp.espresso.base.GitHubTestConfig;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class TestDataModule {

    @Provides
    @Singleton
    public GitHubTestConfig providesGitHubTestConfig(GitHubService gitHubService) {
        return new GitHubTestConfig(gitHubService);
    }
}
