package com.renovavision.rxmvp.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class User implements Serializable {
    public long id;
    public String name;
    public String url;
    public String email;
    public String login;
    public String location;

    @SerializedName("avatar_url")
    public String avatarUrl;

    public User() {
    }

    public boolean hasEmail() {
        return email != null && !email.isEmpty();
    }

    public boolean hasLocation() {
        return location != null && !location.isEmpty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (name != null ? !name.equals(user.name) : user.name != null) return false;
        if (url != null ? !url.equals(user.url) : user.url != null) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (login != null ? !login.equals(user.login) : user.login != null) return false;
        if (location != null ? !location.equals(user.location) : user.location != null)
            return false;
        return !(avatarUrl != null ? !avatarUrl.equals(user.avatarUrl) : user.avatarUrl != null);

    }
}
