package com.renovavision.rxmvp.domain.usecase.github;

import android.support.annotation.NonNull;

import com.renovavision.rxmvp.data.model.User;
import com.renovavision.rxmvp.data.repository.GitHubRepository;

import rx.Observable;

public class GetUserUserCase {

    private GitHubRepository gitHubRepository;

    public GetUserUserCase(GitHubRepository gitHubRepository) {
        this.gitHubRepository = gitHubRepository;
    }

    public Observable<User> getUser(@NonNull String url) {
        return gitHubRepository.getUserFromUrl(url);
    }
}
