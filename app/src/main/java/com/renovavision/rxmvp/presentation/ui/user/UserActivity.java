package com.renovavision.rxmvp.presentation.ui.user;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.renovavision.rxmvp.R;
import com.renovavision.rxmvp.data.model.Repository;
import com.renovavision.rxmvp.data.model.User;
import com.renovavision.rxmvp.presentation.GitHubApp;
import com.renovavision.rxmvp.presentation.common.activity.BaseMvpActivity;
import com.renovavision.rxmvp.presentation.common.di.module.ActivityModule;
import com.renovavision.rxmvp.presentation.ui.user.di.DaggerUserComponent;
import com.renovavision.rxmvp.presentation.ui.user.di.UserComponent;
import com.renovavision.rxmvp.presentation.ui.user.di.UserModule;
import com.renovavision.rxmvp.presentation.ui.user.presenter.UserPresenter;
import com.renovavision.rxmvp.presentation.ui.user.view.UserView;
import com.renovavision.rxmvp.presentation.ui.user.view.UserViewError;
import com.renovavision.rxmvp.presentation.ui.user.view.UserViewModel;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

public class UserActivity extends BaseMvpActivity<UserView, UserPresenter, UserComponent> implements UserView {

    private static final String EXTRA_REPOSITORY = "EXTRA_REPOSITORY";

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.text_repo_description)
    TextView descriptionText;

    @Bind(R.id.text_homepage)
    TextView homepageText;

    @Bind(R.id.text_language)
    TextView languageText;

    @Bind(R.id.text_fork)
    TextView forkText;

    @Bind(R.id.text_owner_name)
    TextView ownerNameText;

    @Bind(R.id.text_owner_email)
    TextView ownerEmailText;

    @Bind(R.id.text_owner_location)
    TextView ownerLocationText;

    @Bind(R.id.image_owner)
    ImageView ownerImage;

    @Bind(R.id.layout_owner)
    View ownerLayout;

    public static Intent newIntent(Context context, Repository repository) {
        Intent intent = new Intent(context, UserActivity.class);
        intent.putExtra(EXTRA_REPOSITORY, repository);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repository);
        ButterKnife.bind(this);

        getMvpPresenter().attachView(this);

        setupToolbar();

        Repository repository = (Repository) getIntent().getSerializableExtra(EXTRA_REPOSITORY);

        getMvpPresenter().loadUser(repository);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public UserComponent createActivityComponent() {
        return DaggerUserComponent.builder()
                .applicationComponent(GitHubApp.getComponent())
                .activityModule(new ActivityModule(this))
                .userModule(new UserModule())
                .build();
    }

    @Override
    public void inject() {
        getActivityComponent().inject(this);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showContent() {

    }

    @Override
    public void hideContent() {

    }

    @Override
    public void showError(UserViewError error) {

    }

    @Override
    public void showData(UserViewModel data) {
        User user = data.getUser();
        Repository repository = data.getRepository();

        bindUserData(user);
        bindRepositoryData(repository);
    }

    private void bindRepositoryData(final Repository repository) {
        setTitle(repository.name);
        descriptionText.setText(repository.description);
        homepageText.setText(repository.homepage);
        homepageText.setVisibility(repository.hasHomepage() ? View.VISIBLE : View.GONE);
        languageText.setText(getString(R.string.text_language, repository.language));
        languageText.setVisibility(repository.hasLanguage() ? View.VISIBLE : View.GONE);
        forkText.setVisibility(repository.isFork() ? View.VISIBLE : View.GONE);
    }

    private void bindUserData(final User user) {
        ownerNameText.setText(user.name);
        ownerEmailText.setText(user.email);
        ownerEmailText.setVisibility(user.hasEmail() ? View.VISIBLE : View.GONE);
        ownerLocationText.setText(user.location);
        ownerLocationText.setVisibility(user.hasLocation() ? View.VISIBLE : View.GONE);
        ownerLayout.setVisibility(View.VISIBLE);
        Picasso.with(this)
                .load(user.avatarUrl)
                .placeholder(R.drawable.placeholder)
                .into(ownerImage);
    }
}
