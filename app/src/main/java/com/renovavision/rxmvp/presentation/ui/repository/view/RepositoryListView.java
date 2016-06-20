package com.renovavision.rxmvp.presentation.ui.repository.view;

import android.support.annotation.NonNull;

import com.renovavision.rxmvp.data.model.Repository;
import com.renovavision.rxmvp.presentation.common.view.MvpLceView;

public interface RepositoryListView extends MvpLceView<RepositoryListViewModel, RepositoryListViewError> {

    void goToUser(@NonNull Repository repository);
}
