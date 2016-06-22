package com.renovavision.rxmvp.data.api;

import com.renovavision.rxmvp.data.model.Repository;
import com.renovavision.rxmvp.presentation.base.BaseTest;
import com.renovavision.rxmvp.presentation.base.TestConstants;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.mockwebserver.Dispatcher;
import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;
import com.squareup.okhttp.mockwebserver.RecordedRequest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import rx.observers.TestSubscriber;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;

public class GitHubServiceTest extends BaseTest {

    private MockWebServer server;
    private GitHubService gitHubService;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        server = new MockWebServer();
        server.start();
        final Dispatcher dispatcher = new Dispatcher() {

            @Override
            public MockResponse dispatch(RecordedRequest request) throws InterruptedException {

                if (request.getPath().equals("/users/alexmprog/repos")) {
                    return new MockResponse().setResponseCode(200)
                            .setBody(testUtils.readString(TestConstants.GET_REPOS));
                }
                return new MockResponse().setResponseCode(404);
            }
        };

        server.setDispatcher(dispatcher);
        HttpUrl baseUrl = server.url("/");
        gitHubService = GitHubServiceFactory.createGithubService(baseUrl.toString());
    }

    @Test
    public void testGetRepositories() throws Exception {

        TestSubscriber<List<Repository>> testSubscriber = new TestSubscriber<>();
        gitHubService.getPublicRepositories("alexmprog").subscribe(testSubscriber);

        testSubscriber.assertNoErrors();
        testSubscriber.assertValueCount(1);

        List<Repository> actual = testSubscriber.getOnNextEvents().get(0);

        assertEquals("Android-CleanArchitecture", actual.get(0).name);
        assertEquals(0, actual.get(0).forks);
        assertEquals(61188555, actual.get(0).id);
    }

    @Test
    public void testGetRepositoriesIncorrect() throws Exception {
        try {
            gitHubService.getPublicRepositories("IncorrectRequest").subscribe();
            fail();
        } catch (Exception expected) {
            assertEquals("HTTP 404 OK", expected.getMessage());
        }
    }

    @After
    public void tearDown() throws Exception {
        server.shutdown();
    }
}

