package com.renovavision.rxmvp.data.di;

import com.renovavision.rxmvp.BuildConfig;
import com.renovavision.rxmvp.data.api.GitHubService;
import com.renovavision.rxmvp.data.api.GitHubServiceFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class GitHubModule {

    @Provides
    @Singleton
    public GitHubService providesGitHubService() {
        return GitHubServiceFactory.createGithubService(BuildConfig.GITHUB_BASE_URL);
    }
}
