package com.renovavision.rxmvp.presentation.ui;

import com.renovavision.rxmvp.data.model.Repository;
import com.renovavision.rxmvp.data.model.User;
import com.renovavision.rxmvp.domain.UseCaseExecutor;
import com.renovavision.rxmvp.domain.usecase.github.GetUserUseCase;
import com.renovavision.rxmvp.presentation.base.BaseTest;
import com.renovavision.rxmvp.presentation.base.TestConstants;
import com.renovavision.rxmvp.presentation.ui.user.presenter.UserModelTransformer;
import com.renovavision.rxmvp.presentation.ui.user.presenter.UserPresenter;
import com.renovavision.rxmvp.presentation.ui.user.view.UserView;
import com.renovavision.rxmvp.presentation.ui.user.view.UserViewError;
import com.renovavision.rxmvp.presentation.ui.user.view.UserViewModel;

import org.junit.Before;
import org.junit.Test;

import javax.inject.Inject;

import rx.Observable;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserPresenterTest extends BaseTest {

    @Inject
    GetUserUseCase getUserUseCase;

    @Inject
    UseCaseExecutor useCaseExecutor;

    UserModelTransformer userModelTransformer;

    UserPresenter userPresenter;

    UserView userView;

    User user;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        component.inject(this);

        user = testUtils.readJson(TestConstants.GET_USER, User.class);

        userModelTransformer = mock(UserModelTransformer.class);
        userView = mock(UserView.class);
        userPresenter = new UserPresenter(getUserUseCase, useCaseExecutor, userModelTransformer);

        userPresenter.attachView(userView);
    }


    @Test
    public void testLoadData() {
        Repository repository = mock(Repository.class);
        User owner = mock(User.class);
        owner.url = TestConstants.GET_USER;
        repository.owner = owner;

        UserViewModel model = new UserViewModel(repository, user);
        when(userModelTransformer.call(user)).thenReturn(model);

        doReturn(Observable.just(user)).when(getUserUseCase).getUser(TestConstants.GET_USER);
        userPresenter.loadUser(repository);

        verify(userView).hideLoading();
        verify(userView).showContent();
        verify(userView).showData(model);
    }

    @Test
    public void testLoadDataFailed() {
        Repository repository = mock(Repository.class);
        User owner = mock(User.class);
        owner.url = TestConstants.GET_USER;
        repository.owner = owner;

        doReturn(Observable.error(new Throwable(TestConstants.ERROR))).when(getUserUseCase).getUser(TestConstants.GET_USER);
        userPresenter.loadUser(repository);

        verify(userView).hideLoading();
        verify(userView).showError(any(UserViewError.class));
    }

    @Test
    public void testLoadDataNotStarted() {
        Repository repository = mock(Repository.class);
        User owner = mock(User.class);
        owner.url = "";
        repository.owner = owner;

        userPresenter.loadUser(repository);

        verify(userView, never()).showLoading();
    }
}

