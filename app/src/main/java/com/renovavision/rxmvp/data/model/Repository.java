package com.renovavision.rxmvp.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Repository implements Serializable {
    public long id;
    public String name;
    public String description;
    public int forks;
    public int watchers;

    @SerializedName("stargazers_count")
    public int stars;
    public String language;
    public String homepage;
    public User owner;
    public boolean fork;

    public Repository() {
    }

    public boolean hasHomepage() {
        return homepage != null && !homepage.isEmpty();
    }

    public boolean hasLanguage() {
        return language != null && !language.isEmpty();
    }

    public boolean isFork() {
        return fork;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Repository that = (Repository) o;

        if (id != that.id) return false;
        if (forks != that.forks) return false;
        if (watchers != that.watchers) return false;
        if (stars != that.stars) return false;
        if (fork != that.fork) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null)
            return false;
        if (language != null ? !language.equals(that.language) : that.language != null)
            return false;
        if (homepage != null ? !homepage.equals(that.homepage) : that.homepage != null)
            return false;
        return !(owner != null ? !owner.equals(that.owner) : that.owner != null);

    }
}
