package com.renovavision.rxmvp.presenters;

import android.support.annotation.NonNull;

import com.renovavision.rxmvp.api.GithubApi;
import com.renovavision.rxmvp.model.User;
import com.renovavision.rxmvp.ui.views.RepositoryView;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class RepositoryPresenter extends BasePresenter<RepositoryView> {

    private Subscription mSubscription;

    public RepositoryPresenter(@NonNull RepositoryView baseView) {
        super(baseView);
    }

    @Override
    public void onViewDestroyed() {
        super.onViewDestroyed();
        if (mSubscription != null) {
            mSubscription.unsubscribe();
        }
    }

    public void loadOwner(@NonNull String userUrl) {
        mSubscription = GithubApi.getInstance().getUserFromUrl(userUrl)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<User>() {
                    @Override
                    public void call(User user) {
                        RepositoryView  view = getView();
                        if(view == null){
                            return;
                        }

                        view.showOwner(user);
                    }
                });
    }
}
