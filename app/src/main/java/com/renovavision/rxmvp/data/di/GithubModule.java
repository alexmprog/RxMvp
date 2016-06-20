package com.renovavision.rxmvp.data.di;

import com.renovavision.rxmvp.data.api.GitHubService;
import com.renovavision.rxmvp.data.api.GitHubServiceFactory;

import dagger.Module;
import dagger.Provides;

@Module
public class GitHubModule {

    @Provides
    GitHubService providesGitHubService() {
        return GitHubServiceFactory.createGithubService();
    }
}
