package com.renovavision.rxmvp.ui.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.renovavision.rxmvp.R;
import com.renovavision.rxmvp.model.Repository;
import com.renovavision.rxmvp.presenters.HomePresenter;
import com.renovavision.rxmvp.ui.adapters.RepositoryAdapter;
import com.renovavision.rxmvp.ui.views.HomeView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends AppCompatActivity implements HomeView {

    @Bind(R.id.repos_recycler_view)
    RecyclerView reposRecycleView;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.edit_text_username)
    EditText editTextUsername;

    @Bind(R.id.progress)
    ProgressBar progressBar;

    @Bind(R.id.text_info)
    TextView infoTextView;

    @Bind(R.id.button_search)
    ImageButton searchButton;

    private HomePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        setupRecyclerView(reposRecycleView);
        setupEditText();

        presenter = new HomePresenter(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onViewResumed();
    }

    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        presenter.onViewDestroyed();
        super.onDestroy();
    }

    @OnClick(R.id.button_search)
    public void onClick(){
        presenter.loadRepositories(editTextUsername.getText().toString());
    }

    @Override
    public Context getContext() {
        return this;
    }

    private void setupEditText(){
        editTextUsername.addTextChangedListener(mHideShowButtonTextWatcher);
        editTextUsername.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    presenter.loadRepositories(editTextUsername.getText().toString());
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void showRepositories(List<Repository> repositories) {
        RepositoryAdapter adapter = (RepositoryAdapter) reposRecycleView.getAdapter();
        adapter.setRepositories(repositories);
        adapter.notifyDataSetChanged();
        reposRecycleView.requestFocus();
        hideSoftKeyboard();
        progressBar.setVisibility(View.INVISIBLE);
        infoTextView.setVisibility(View.INVISIBLE);
        reposRecycleView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showMessage(int stringId) {
        progressBar.setVisibility(View.INVISIBLE);
        infoTextView.setVisibility(View.VISIBLE);
        reposRecycleView.setVisibility(View.INVISIBLE);
        infoTextView.setText(getString(stringId));
    }

    @Override
    public void showProgressIndicator() {
        progressBar.setVisibility(View.VISIBLE);
        infoTextView.setVisibility(View.INVISIBLE);
        reposRecycleView.setVisibility(View.INVISIBLE);
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        RepositoryAdapter adapter = new RepositoryAdapter();
        adapter.setCallback(new RepositoryAdapter.Callback() {
            @Override
            public void onItemClick(Repository repository) {
                startActivity(RepositoryActivity.newIntent(HomeActivity.this, repository));
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void hideSoftKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editTextUsername.getWindowToken(), 0);
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

}

