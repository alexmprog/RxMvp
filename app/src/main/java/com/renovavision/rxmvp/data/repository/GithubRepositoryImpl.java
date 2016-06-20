package com.renovavision.rxmvp.data.repository;

import android.support.annotation.NonNull;

import com.renovavision.rxmvp.data.api.GitHubService;
import com.renovavision.rxmvp.data.model.Repository;
import com.renovavision.rxmvp.data.model.User;

import java.util.List;

import rx.Observable;

public class GitHubRepositoryImpl implements GitHubRepository {

    private GitHubService mGitHubService;

    public GitHubRepositoryImpl(GitHubService gitHubService) {
        this.mGitHubService = gitHubService;
    }

    @Override
    public Observable<List<Repository>> getPublicRepositories(@NonNull String username) {
        return mGitHubService.getPublicRepositories(username);
    }

    @Override
    public Observable<User> getUserFromUrl(@NonNull String userUrl) {
        return mGitHubService.getUserFromUrl(userUrl);
    }
}
