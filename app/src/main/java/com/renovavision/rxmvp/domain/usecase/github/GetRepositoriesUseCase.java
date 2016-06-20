package com.renovavision.rxmvp.domain.usecase.github;

import android.support.annotation.NonNull;

import com.renovavision.rxmvp.data.model.Repository;
import com.renovavision.rxmvp.data.repository.GitHubRepository;

import java.util.List;

import rx.Observable;

public class GetRepositoriesUseCase {

    private GitHubRepository gitHubRepository;

    public GetRepositoriesUseCase(GitHubRepository gitHubRepository) {
        this.gitHubRepository = gitHubRepository;
    }

    public Observable<List<Repository>> getPublicRepositories(@NonNull String userName) {
        return gitHubRepository.getPublicRepositories(userName);
    }
}
