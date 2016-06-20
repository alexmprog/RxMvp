package com.renovavision.rxmvp.presentation.ui.repository.view;


import com.renovavision.rxmvp.data.model.Repository;
import com.renovavision.rxmvp.presentation.common.view.model.ViewModel;

import java.io.Serializable;
import java.util.List;

public class RepositoryListViewModel implements ViewModel, Serializable {

    private List<Repository> mRepositoryList;

    public RepositoryListViewModel(List<Repository> repositoryList) {
        this.mRepositoryList = repositoryList;
    }

    public List<Repository> getRepositoryList() {
        return mRepositoryList;
    }
}
