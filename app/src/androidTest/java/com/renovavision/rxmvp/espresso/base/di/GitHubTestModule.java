package com.renovavision.rxmvp.espresso.base.di;

import com.renovavision.rxmvp.data.api.GitHubService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static org.mockito.Mockito.mock;

@Module
public class GitHubTestModule {

    @Provides
    @Singleton
    public GitHubService providesGitHubService() {
        return mock(GitHubService.class);
    }
}
