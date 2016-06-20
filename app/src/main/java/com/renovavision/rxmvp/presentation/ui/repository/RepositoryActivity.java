package com.renovavision.rxmvp.presentation.ui.repository;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.renovavision.rxmvp.R;
import com.renovavision.rxmvp.data.model.Repository;
import com.renovavision.rxmvp.presentation.GitHubApp;
import com.renovavision.rxmvp.presentation.common.activity.BaseMvpActivity;
import com.renovavision.rxmvp.presentation.common.di.module.ActivityModule;
import com.renovavision.rxmvp.presentation.ui.repository.di.DaggerRepositoryListComponent;
import com.renovavision.rxmvp.presentation.ui.repository.di.RepositoryListComponent;
import com.renovavision.rxmvp.presentation.ui.repository.di.RepositoryListModule;
import com.renovavision.rxmvp.presentation.ui.repository.presenter.RepositoryPresenter;
import com.renovavision.rxmvp.presentation.ui.repository.view.RepositoryListView;
import com.renovavision.rxmvp.presentation.ui.repository.view.RepositoryListViewError;
import com.renovavision.rxmvp.presentation.ui.repository.view.RepositoryListViewModel;
import com.renovavision.rxmvp.presentation.ui.user.UserActivity;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RepositoryActivity extends BaseMvpActivity<RepositoryListView, RepositoryPresenter, RepositoryListComponent> implements RepositoryListView, RepositoryAdapter.Callback {

    @Bind(R.id.repos_recycler_view)
    RecyclerView reposRecycleView;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.edit_text_username)
    EditText editTextUsername;

    @Bind(R.id.progress)
    ProgressBar progressBar;

    @Bind(R.id.main_container)
    RelativeLayout mainContainer;

    @Bind(R.id.button_search)
    ImageButton searchButton;

    @Inject
    RepositoryAdapter repositoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        getMvpPresenter().attachView(this);

        setSupportActionBar(toolbar);
        setupRecyclerView(reposRecycleView);
        setupEditText();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.button_search)
    public void onClick() {
        presenter.loadRepositories(editTextUsername.getText().toString());
    }

    private void setupEditText() {
        editTextUsername.addTextChangedListener(mHideShowButtonTextWatcher);
        editTextUsername.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                presenter.loadRepositories(editTextUsername.getText().toString());
                return true;
            }
            return false;
        });
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        repositoryAdapter.setCallback(this);
        recyclerView.setAdapter(repositoryAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private TextWatcher mHideShowButtonTextWatcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
            searchButton.setVisibility(charSequence.length() > 0 ? View.VISIBLE : View.GONE);
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    @Override
    public RepositoryListComponent createActivityComponent() {
        return DaggerRepositoryListComponent.builder()
                .applicationComponent(GitHubApp.get(this).getComponent())
                .activityModule(new ActivityModule(this))
                .repositoryListModule(new RepositoryListModule())
                .build();
    }

    @Override
    public void inject() {
        getActivityComponent().inject(this);
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showContent() {
        reposRecycleView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideContent() {
        reposRecycleView.setVisibility(View.GONE);
    }

    @Override
    public void showError(RepositoryListViewError error) {
        Snackbar.make(mainContainer, error.getMessageId(), Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showData(RepositoryListViewModel data) {
        repositoryAdapter.setRepositories(data.getRepositoryList());
    }

    @Override
    public void onItemClick(Repository repository) {
        getMvpPresenter().onRepositoryClicked(repository);
    }

    @Override
    public void goToUser(@NonNull Repository repository) {
        startActivity(UserActivity.newIntent(this, repository));
    }
}

