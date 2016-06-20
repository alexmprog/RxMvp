package com.renovavision.rxmvp.presentation.ui.repository.presenter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.renovavision.rxmvp.R;
import com.renovavision.rxmvp.data.model.Repository;
import com.renovavision.rxmvp.domain.UseCaseExecutor;
import com.renovavision.rxmvp.domain.usecase.github.GetRepositoriesUseCase;
import com.renovavision.rxmvp.presentation.common.presenter.BasePresenter;
import com.renovavision.rxmvp.presentation.ui.repository.view.RepositoryListViewError;
import com.renovavision.rxmvp.presentation.ui.repository.view.RepositoryListView;
import com.renovavision.rxmvp.presentation.ui.repository.view.RepositoryListViewModel;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscription;

public class RepositoryPresenter extends BasePresenter<RepositoryListView> {

    private static final String MODEL_KEY = "model_key";

    private UseCaseExecutor mUseCaseExecutor;

    private GetRepositoriesUseCase mGetRepositoriesUseCase;

    private RepositoryModelTransformer mRepositoryModelTransformer;

    private Subscription mSubscription;

    private RepositoryListViewModel mRepositoryViewModel;

    public RepositoryPresenter(@NonNull GetRepositoriesUseCase getRepositoriesUseCase,
                               @NonNull UseCaseExecutor useCaseExecutor,
                               @NonNull RepositoryModelTransformer repositoryModelTransformer) {
        this.mGetRepositoriesUseCase = getRepositoriesUseCase;
        this.mUseCaseExecutor = useCaseExecutor;
        this.mRepositoryModelTransformer = repositoryModelTransformer;
    }

    public void loadRepositories(@NonNull String userName) {
        String username = userName.trim();
        if (TextUtils.isEmpty(username)) {
            return;
        }

        getMvpView().hideContent();
        getMvpView().showLoading();

        if (mSubscription != null) {
            mSubscription.unsubscribe();
        }

        mSubscription = mGetRepositoriesUseCase
                .getPublicRepositories(username)
                .map(mRepositoryModelTransformer)
                .compose(mUseCaseExecutor.applySchedulers())
                .subscribe(repositoryViewModel -> {
                    mRepositoryViewModel = repositoryViewModel;
                    if (isViewAttached()) {
                        getMvpView().hideLoading();
                        getMvpView().showContent();
                        getMvpView().showData(repositoryViewModel);
                    }
                }, throwable -> {
                    if (isViewAttached()) {
                        getMvpView().hideLoading();
                        getMvpView().hideContent();
                        if (isHttp404(throwable)) {
                            getMvpView().showError(new RepositoryListViewError(R.string.error_username_not_found));
                        } else {
                            getMvpView().showError(new RepositoryListViewError(R.string.error_loading_repos));
                        }
                    }
                });
    }

    @Override
    public void saveState(Bundle outBundle) {
        if (mRepositoryViewModel != null) {
            outBundle.putSerializable(MODEL_KEY, mRepositoryViewModel);
        }
    }

    @Override
    public void restoreState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mRepositoryViewModel = (RepositoryListViewModel) savedInstanceState.getSerializable(MODEL_KEY);
        }
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) {
            mSubscription.unsubscribe();
        }
    }

    @Override
    public void attachView(RepositoryListView mvpView) {
        super.attachView(mvpView);
        if (mRepositoryViewModel != null) {
            getMvpView().hideLoading();
            getMvpView().showContent();
            getMvpView().showData(mRepositoryViewModel);
        }
    }

    public void onRepositoryClicked(@NonNull Repository repository) {
        getMvpView().goToUser(repository);
    }

    private boolean isHttp404(Throwable error) {
        return error instanceof HttpException && ((HttpException) error).code() == 404;
    }

}
