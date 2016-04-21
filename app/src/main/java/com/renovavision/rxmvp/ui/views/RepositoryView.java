package com.renovavision.rxmvp.ui.views;

import com.renovavision.rxmvp.model.User;

public interface RepositoryView extends BaseView {

    void showOwner(final User owner);
}
