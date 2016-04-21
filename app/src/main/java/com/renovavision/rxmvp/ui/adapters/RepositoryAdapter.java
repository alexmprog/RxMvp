package com.renovavision.rxmvp.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.renovavision.rxmvp.R;
import com.renovavision.rxmvp.model.Repository;

import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RepositoryAdapter extends RecyclerView.Adapter<RepositoryAdapter.RepositoryViewHolder> {

    private List<Repository> mRepositories;
    private Callback mCallback;

    public RepositoryAdapter() {
        this.mRepositories = Collections.emptyList();
    }

    public RepositoryAdapter(List<Repository> repositories) {
        this.mRepositories = repositories;
    }

    public void setRepositories(List<Repository> repositories) {
        this.mRepositories = repositories;
    }

    public void setCallback(Callback mCallback) {
        this.mCallback = mCallback;
    }

    @Override
    public RepositoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.repository_item, parent, false);
        final RepositoryViewHolder viewHolder = new RepositoryViewHolder(itemView);
        viewHolder.contentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCallback != null) {
                    mCallback.onItemClick(viewHolder.repository);
                }
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RepositoryViewHolder holder, int position) {
        Repository repository = mRepositories.get(position);
        Context context = holder.titleTextView.getContext();
        holder.repository = repository;
        holder.titleTextView.setText(repository.name);
        holder.descriptionTextView.setText(repository.description);
        holder.watchersTextView.setText(
                context.getResources().getString(R.string.text_watchers, repository.watchers));
        holder.starsTextView.setText(
                context.getResources().getString(R.string.text_stars, repository.stars));
        holder.forksTextView.setText(
                context.getResources().getString(R.string.text_forks, repository.forks));
    }

    @Override
    public int getItemCount() {
        return mRepositories.size();
    }

    public static class RepositoryViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.layout_content)
        View contentLayout;

        @Bind(R.id.text_repo_title)
        TextView titleTextView;

        @Bind(R.id.text_repo_description)
        TextView descriptionTextView;

        @Bind(R.id.text_watchers)
        TextView watchersTextView;

        @Bind(R.id.text_stars)
        TextView starsTextView;

        @Bind(R.id.text_forks)
        TextView forksTextView;

        public Repository repository;

        public RepositoryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public interface Callback {
        void onItemClick(Repository repository);
    }
}