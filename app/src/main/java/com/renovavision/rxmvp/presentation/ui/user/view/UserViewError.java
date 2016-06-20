package com.renovavision.rxmvp.presentation.ui.user.view;

import android.support.annotation.StringRes;

import com.renovavision.rxmvp.presentation.common.view.error.ViewError;

public class UserViewError implements ViewError {

    @StringRes
    private int messageId;

    public UserViewError(@StringRes int messageId) {
        this.messageId = messageId;
    }

    @StringRes
    public int getMessageId() {
        return messageId;
    }
}
