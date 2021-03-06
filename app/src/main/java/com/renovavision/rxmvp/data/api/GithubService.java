package com.renovavision.rxmvp.data.api;

import com.renovavision.rxmvp.data.model.Repository;
import com.renovavision.rxmvp.data.model.User;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Url;
import rx.Observable;

public interface GitHubService {

    @GET("users/{username}/repos")
    Observable<List<Repository>> getPublicRepositories(@Path("username") String username);

    @GET
    Observable<User> getUserFromUrl(@Url String userUrl);
}
