package com.renovavision.rxmvp.presenters;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.renovavision.rxmvp.R;
import com.renovavision.rxmvp.api.GithubApi;
import com.renovavision.rxmvp.model.Repository;
import com.renovavision.rxmvp.ui.views.HomeView;

import java.util.List;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class HomePresenter extends  BasePresenter<HomeView> {

    private Subscription mSubscription;
    private List<Repository> mRepositories;

    public HomePresenter(@NonNull HomeView baseView) {
        super(baseView);
    }

    @Override
    public void onViewDestroyed() {
        super.onViewDestroyed();
        if(mSubscription !=null){
            mSubscription.unsubscribe();
        }
    }

    public void loadRepositories(@NonNull String userName) {
        String username = userName.trim();
        if (TextUtils.isEmpty(username)) {
            return;
        }

        getView().showProgressIndicator();
        if (mSubscription != null){
            mSubscription.unsubscribe();
        }

        mSubscription = GithubApi.getInstance().getPublicRepositories(username)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<List<Repository>>() {
                    @Override
                    public void call(List<Repository> repositories) {
                        mRepositories = repositories;

                        HomeView view = getView();
                        if(view == null){
                            return;
                        }

                        if (!repositories.isEmpty()) {
                            view.showRepositories(mRepositories);
                        } else {
                            view.showMessage(R.string.text_empty_repos);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        HomeView view = getView();
                        if(view == null){
                            return;
                        }

                        if (isHttp404(throwable)) {
                            view.showMessage(R.string.error_username_not_found);
                        } else {
                            view.showMessage(R.string.error_loading_repos);
                        }
                    }
                });
    }

    private boolean isHttp404(Throwable error) {
        return error instanceof HttpException && ((HttpException) error).code() == 404;
    }

}
