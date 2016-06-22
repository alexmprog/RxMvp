package com.renovavision.rxmvp.presentation.base;

import com.renovavision.rxmvp.BuildConfig;
import com.renovavision.rxmvp.presentation.GitHubApp;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricGradleTestRunner.class)
@Config(application = TestApplication.class,
        constants = BuildConfig.class,
        sdk = 21)
@Ignore
public class BaseTest {

    public TestComponent component;
    public TestUtils testUtils;

    @Before
    public void setUp() throws Exception {
        component = (TestComponent) GitHubApp.getComponent();
        testUtils = new TestUtils();
    }

}
