package com.renovavision.rxmvp.presentation.ui.repository.presenter;

import com.renovavision.rxmvp.data.model.Repository;
import com.renovavision.rxmvp.presentation.ui.repository.view.RepositoryListViewModel;

import java.util.List;

import rx.functions.Func1;

public class RepositoryModelTransformer implements Func1<List<Repository>, RepositoryListViewModel> {

    @Override
    public RepositoryListViewModel call(List<Repository> repositories) {
        return new RepositoryListViewModel(repositories);
    }
}
