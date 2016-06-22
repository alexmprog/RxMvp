package com.renovavision.rxmvp.data.di;

import com.renovavision.rxmvp.data.api.GitHubService;

import dagger.Module;
import dagger.Provides;

import static org.mockito.Mockito.mock;

@Module
public class GitHubTestModule {

    @Provides
    GitHubService providesGitHubService() {
        return mock(GitHubService.class);
    }
}
