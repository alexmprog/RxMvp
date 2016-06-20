package com.renovavision.rxmvp.presentation.common.view;

import com.renovavision.rxmvp.presentation.common.view.error.ViewError;
import com.renovavision.rxmvp.presentation.common.view.model.ViewModel;

public interface MvpLceView<M extends ViewModel, E extends ViewError> extends MvpView {

    void showLoading();

    void hideLoading();

    void showContent();

    void hideContent();

    void showError(E error);

    void showData(M data);
}
