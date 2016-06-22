package com.renovavision.rxmvp.espresso.base;

import com.renovavision.rxmvp.data.api.GitHubService;
import com.renovavision.rxmvp.data.model.Repository;
import com.renovavision.rxmvp.data.model.User;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;

import static org.mockito.Mockito.when;

public class GitHubTestConfig {

    protected List<Repository> repositoryList;

    protected User user;

    protected GitHubService gitHubService;

    protected TestUtils testUtils;

    public GitHubTestConfig(GitHubService gitHubService) {
        this.gitHubService = gitHubService;
        this.testUtils = new TestUtils();
    }

    public void setCorrectAnswer() {
        repositoryList = Arrays.asList(testUtils.readJson(TestConstants.GET_REPOS, Repository[].class));
        user = testUtils.readJson(TestConstants.GET_USER, User.class);
        when(gitHubService.getPublicRepositories(TestConstants.USER_NAME))
                .thenReturn(getObservableWithDelay((repositoryList)));
        when(gitHubService.getUserFromUrl(TestConstants.USER_NAME))
                .thenReturn(getObservableWithDelay((user)));
    }

    public void setErrorAnswer() {
        when(gitHubService.getPublicRepositories(TestConstants.USER_NAME))
                .thenReturn(getErrorObservableWithDelay());
        when(gitHubService.getUserFromUrl(TestConstants.USER_NAME))
                .thenReturn(getErrorObservableWithDelay());
    }

    private <T> Observable<T> getObservableWithDelay(final T value) {
        return Observable.timer(TestConstants.API_DELAY, TimeUnit.MILLISECONDS)
                .concatMap(aLong -> Observable.just(value));
    }

    private <T> Observable<T> getErrorObservableWithDelay() {
        return Observable.timer(TestConstants.API_DELAY, TimeUnit.MILLISECONDS)
                .concatMap(aLong -> Observable.error(new Throwable(TestConstants.ERROR)));
    }

}
