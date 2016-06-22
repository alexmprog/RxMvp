package com.renovavision.rxmvp.data.di;

import com.renovavision.rxmvp.data.api.GitHubService;
import com.renovavision.rxmvp.data.repository.GitHubRepository;

import dagger.Module;
import dagger.Provides;

import static org.mockito.Mockito.mock;

@Module
public class RepositoryTestModule {

    @Provides
    GitHubRepository providesGitHubRepository(GitHubService gitHubService) {
        return mock(GitHubRepository.class);
    }
}
