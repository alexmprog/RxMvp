package com.renovavision.rxmvp.espresso.ui;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.MediumTest;

import com.renovavision.rxmvp.R;
import com.renovavision.rxmvp.espresso.base.EspressoTools;
import com.renovavision.rxmvp.espresso.base.GitHubTestConfig;
import com.renovavision.rxmvp.espresso.base.TestConstants;
import com.renovavision.rxmvp.espresso.base.TestUtils;
import com.renovavision.rxmvp.espresso.base.di.TestComponent;
import com.renovavision.rxmvp.presentation.GitHubApp;
import com.renovavision.rxmvp.presentation.ui.repository.RepositoryActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@MediumTest
public class RepositoryActivityTest {

    @Rule
    public ActivityTestRule<RepositoryActivity> mActivityRule = new ActivityTestRule<>(RepositoryActivity.class);

    @Inject
    GitHubTestConfig gitHubTestService;

    TestUtils testUtils;

    @Before
    public void setUp() {
        ((TestComponent) GitHubApp.getComponent()).inject(this);
        testUtils = new TestUtils();
    }

    @Test
    public void testElementsDisplayed() {
        onView(withId(R.id.button_search)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        onView(withId(R.id.edit_text_username)).check(matches(isDisplayed()));
    }


    @Test
    public void testGetUserRepo() {
        gitHubTestService.setCorrectAnswer();
        enterOwner();

        onView(withId(R.id.repos_recycler_view)).check(EspressoTools.hasItemsCount(TestConstants.REPOS_COUNT));
    }

    @Test
    public void testGetUserRepoError() {
        gitHubTestService.setErrorAnswer();
        enterOwner();

        onView(withId(R.id.repos_recycler_view)).check(EspressoTools.hasItemsCount(0));
    }

    @Test
    public void testHideProgressBar() {
        gitHubTestService.setCorrectAnswer();
        enterOwner();
        onView(withId(R.id.progress)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
    }


    @Test
    public void testHideProgressBarOnError() {
        gitHubTestService.setErrorAnswer();
        enterOwner();
        onView(withId(R.id.progress)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
    }

    private void enterOwner() {
        onView(withId(R.id.edit_text_username)).perform(clearText());
        onView(withId(R.id.edit_text_username)).perform(typeText(TestConstants.USER_NAME));
        onView(withId(R.id.button_search)).perform(click());
    }


}
