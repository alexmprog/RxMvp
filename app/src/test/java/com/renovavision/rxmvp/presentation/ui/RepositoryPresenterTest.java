package com.renovavision.rxmvp.presentation.ui;

import com.renovavision.rxmvp.data.model.Repository;
import com.renovavision.rxmvp.domain.UseCaseExecutor;
import com.renovavision.rxmvp.domain.usecase.github.GetRepositoriesUseCase;
import com.renovavision.rxmvp.presentation.base.BaseTest;
import com.renovavision.rxmvp.presentation.base.TestConstants;
import com.renovavision.rxmvp.presentation.ui.repository.presenter.RepositoryModelTransformer;
import com.renovavision.rxmvp.presentation.ui.repository.presenter.RepositoryPresenter;
import com.renovavision.rxmvp.presentation.ui.repository.view.RepositoryListView;
import com.renovavision.rxmvp.presentation.ui.repository.view.RepositoryListViewError;
import com.renovavision.rxmvp.presentation.ui.repository.view.RepositoryListViewModel;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RepositoryPresenterTest extends BaseTest {

    @Inject
    GetRepositoriesUseCase getRepositoriesUseCase;

    @Inject
    UseCaseExecutor useCaseExecutor;

    RepositoryModelTransformer repositoryModelTransformer;

    RepositoryPresenter repositoryPresenter;

    RepositoryListView repositoryListView;

    List<Repository> repositoryList;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        component.inject(this);

        repositoryList = Arrays.asList(testUtils.readJson(TestConstants.GET_REPOS, Repository[].class));

        repositoryModelTransformer = mock(RepositoryModelTransformer.class);
        repositoryListView = mock(RepositoryListView.class);
        repositoryPresenter = new RepositoryPresenter(getRepositoriesUseCase, useCaseExecutor, repositoryModelTransformer);

        repositoryPresenter.attachView(repositoryListView);
    }


    @Test
    public void testLoadData() {
        RepositoryListViewModel model = new RepositoryListViewModel(repositoryList);
        when(repositoryModelTransformer.call(repositoryList)).thenReturn(model);

        doReturn(Observable.just(repositoryList)).when(getRepositoriesUseCase).getPublicRepositories(TestConstants.GET_REPOS);
        repositoryPresenter.loadRepositories(TestConstants.GET_REPOS);

        verify(repositoryListView).hideLoading();
        verify(repositoryListView).showContent();
        verify(repositoryListView).showData(model);
    }

    @Test
    public void testLoadDataFailed() {
        doReturn(Observable.error(new Throwable(TestConstants.ERROR))).when(getRepositoriesUseCase).getPublicRepositories(TestConstants.GET_REPOS);
        repositoryPresenter.loadRepositories(TestConstants.GET_REPOS);

        verify(repositoryListView).hideLoading();
        verify(repositoryListView).showError(any(RepositoryListViewError.class));
    }
}
