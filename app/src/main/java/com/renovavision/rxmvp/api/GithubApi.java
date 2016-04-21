package com.renovavision.rxmvp.api;

import android.support.annotation.NonNull;

import com.renovavision.rxmvp.model.Repository;
import com.renovavision.rxmvp.model.User;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

public class GithubApi {

    private static volatile GithubApi sInstance;

    public static GithubApi getInstance(){
        if(sInstance == null){
            synchronized (GithubApi.class){
                if(sInstance == null){
                    sInstance = new GithubApi();
                }
            }
        }
        return sInstance;
    }

    private GithubService mGithubService;

    private GithubApi(){
       initServices();
    }

    private void initServices(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        mGithubService = retrofit.create(GithubService.class);
    }

    public Observable<List<Repository>> getPublicRepositories(@NonNull String username){
        return mGithubService.getPublicRepositories(username);
    }

    public Observable<User> getUserFromUrl(@NonNull String userUrl){
        return mGithubService.getUserFromUrl(userUrl);
    }
}
