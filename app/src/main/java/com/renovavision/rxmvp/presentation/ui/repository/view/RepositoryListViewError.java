package com.renovavision.rxmvp.presentation.ui.repository.view;

import android.support.annotation.StringRes;

import com.renovavision.rxmvp.presentation.common.view.error.ViewError;

public class RepositoryListViewError implements ViewError {

    @StringRes
    private int messageId;

    public RepositoryListViewError(@StringRes int messageId) {
        this.messageId = messageId;
    }

    @StringRes
    public int getMessageId() {
        return messageId;
    }
}
