package com.renovavision.rxmvp.domain.usecase;

import com.renovavision.rxmvp.data.model.User;
import com.renovavision.rxmvp.data.repository.GitHubRepository;
import com.renovavision.rxmvp.domain.usecase.github.GetUserUseCase;
import com.renovavision.rxmvp.presentation.base.BaseTest;
import com.renovavision.rxmvp.presentation.base.TestConstants;

import org.junit.Before;
import org.junit.Test;

import javax.inject.Inject;

import rx.Observable;
import rx.observers.TestSubscriber;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class GetUserUseCaseTest extends BaseTest {

    @Inject
    GitHubRepository gitHubRepository;

    GetUserUseCase getUserUseCase;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        component.inject(this);
        getUserUseCase = new GetUserUseCase(gitHubRepository);
    }

    @Test
    public void testGetUser() throws Exception {

        User user = testUtils.readJson(TestConstants.GET_USER, User.class);
        when(gitHubRepository.getUserFromUrl(TestConstants.GET_USER)).thenReturn(Observable.just(user));

        TestSubscriber<User> testSubscriber = new TestSubscriber<>();
        getUserUseCase.getUser(TestConstants.GET_USER).subscribe(testSubscriber);

        testSubscriber.assertNoErrors();
        testSubscriber.assertValueCount(1);

        User actual = testSubscriber.getOnNextEvents().get(0);

        assertEquals("alexmprog", actual.login);
        assertEquals("alexmprog@email.com", actual.email);
        assertEquals(11029882, actual.id);
    }
}

