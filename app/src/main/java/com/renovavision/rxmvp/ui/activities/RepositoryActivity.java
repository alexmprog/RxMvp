package com.renovavision.rxmvp.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.renovavision.rxmvp.R;
import com.renovavision.rxmvp.model.Repository;
import com.renovavision.rxmvp.model.User;
import com.renovavision.rxmvp.presenters.RepositoryPresenter;
import com.renovavision.rxmvp.ui.views.RepositoryView;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RepositoryActivity extends AppCompatActivity implements RepositoryView {

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

    private RepositoryPresenter presenter;

    public static Intent newIntent(Context context, Repository repository) {
        Intent intent = new Intent(context, RepositoryActivity.class);
        intent.putExtra(EXTRA_REPOSITORY, repository);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_repository);

        ButterKnife.bind(this);

        setupToolbar();

        presenter = new RepositoryPresenter(this);

        Repository repository = getIntent().getParcelableExtra(EXTRA_REPOSITORY);
        bindRepositoryData(repository);
        presenter.loadOwner(repository.owner.url);
    }

    private void setupToolbar(){
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        presenter.onViewDestroyed();
        super.onDestroy();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showOwner(final User owner) {
        ownerNameText.setText(owner.name);
        ownerEmailText.setText(owner.email);
        ownerEmailText.setVisibility(owner.hasEmail() ? View.VISIBLE : View.GONE);
        ownerLocationText.setText(owner.location);
        ownerLocationText.setVisibility(owner.hasLocation() ? View.VISIBLE : View.GONE);
        ownerLayout.setVisibility(View.VISIBLE);
    }

    private void bindRepositoryData(final Repository repository) {
        setTitle(repository.name);
        descriptionText.setText(repository.description);
        homepageText.setText(repository.homepage);
        homepageText.setVisibility(repository.hasHomepage() ? View.VISIBLE : View.GONE);
        languageText.setText(getString(R.string.text_language, repository.language));
        languageText.setVisibility(repository.hasLanguage() ? View.VISIBLE : View.GONE);
        forkText.setVisibility(repository.isFork() ? View.VISIBLE : View.GONE);

        Picasso.with(this)
                .load(repository.owner.avatarUrl)
                .placeholder(R.drawable.placeholder)
                .into(ownerImage);
    }
}
