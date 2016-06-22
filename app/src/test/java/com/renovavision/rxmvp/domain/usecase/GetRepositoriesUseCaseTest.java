package com.renovavision.rxmvp.domain.usecase;

import com.renovavision.rxmvp.data.model.Repository;
import com.renovavision.rxmvp.data.repository.GitHubRepository;
import com.renovavision.rxmvp.domain.usecase.github.GetRepositoriesUseCase;
import com.renovavision.rxmvp.presentation.base.BaseTest;
import com.renovavision.rxmvp.presentation.base.TestConstants;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.observers.TestSubscriber;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class GetRepositoriesUseCaseTest extends BaseTest {

    @Inject
    GitHubRepository gitHubRepository;

    GetRepositoriesUseCase getRepositoriesUseCase;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        component.inject(this);
        getRepositoriesUseCase = new GetRepositoriesUseCase(gitHubRepository);
    }

    @Test
    public void testGetRepositories() throws Exception {

        List<Repository> repositoryList = Arrays.asList(testUtils.readJson(TestConstants.GET_REPOS, Repository[].class));
        when(gitHubRepository.getPublicRepositories(TestConstants.GET_REPOS)).thenReturn(Observable.just(repositoryList));

        TestSubscriber<List<Repository>> testSubscriber = new TestSubscriber<>();
        getRepositoriesUseCase.getPublicRepositories(TestConstants.GET_REPOS).subscribe(testSubscriber);

        testSubscriber.assertNoErrors();
        testSubscriber.assertValueCount(1);

        List<Repository> actual = testSubscriber.getOnNextEvents().get(0);

        assertEquals("Android-CleanArchitecture", actual.get(0).name);
        assertEquals(0, actual.get(0).forks);
        assertEquals(61188555, actual.get(0).id);
    }
}
