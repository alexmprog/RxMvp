package com.renovavision.rxmvp.data.repository;

import android.support.annotation.NonNull;

import com.renovavision.rxmvp.data.model.Repository;
import com.renovavision.rxmvp.data.model.User;

import java.util.List;

import rx.Observable;

public interface GitHubRepository {

    Observable<List<Repository>> getPublicRepositories(@NonNull String username);

    Observable<User> getUserFromUrl(@NonNull String userUrl);
}
