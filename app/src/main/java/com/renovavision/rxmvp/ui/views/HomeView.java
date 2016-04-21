package com.renovavision.rxmvp.ui.views;

import com.renovavision.rxmvp.model.Repository;

import java.util.List;

public interface HomeView extends BaseView {

    void showRepositories(List<Repository> repositories);

    void showMessage(int stringId);

    void showProgressIndicator();
}
