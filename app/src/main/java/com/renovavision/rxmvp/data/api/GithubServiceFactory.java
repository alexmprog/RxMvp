package com.renovavision.rxmvp.data.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.renovavision.rxmvp.BuildConfig;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class GitHubServiceFactory {

    public static GitHubService createGithubService() {
        OkHttpClient okHttpClient = createOkHttpClient(createLoggingInterceptor());
        return createGiphyService(okHttpClient);
    }

    public static GitHubService createGiphyService(OkHttpClient okHttpClient) {
        final Gson gson = new GsonBuilder()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.GITHUB_BASE_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        return retrofit.create(GitHubService.class);
    }

    public static OkHttpClient createOkHttpClient(HttpLoggingInterceptor httpLoggingInterceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build();
    }

    public static HttpLoggingInterceptor createLoggingInterceptor() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY
                : HttpLoggingInterceptor.Level.NONE);
        return logging;
    }
}
