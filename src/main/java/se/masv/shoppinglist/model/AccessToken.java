package se.masv.shoppinglist.model;

import com.google.common.base.MoreObjects;

import java.util.Date;
import java.util.Objects;

public class AccessToken {

    private final Long userId;
    private final String token;
    private final Date expires;

    public AccessToken(Long userId, String token, Date expires) {
        this.userId = userId;
        this.token = token;
        this.expires = expires;
    }

    public String getToken() {
        return token;
    }

    public Date getExpires() {
        return expires;
    }

    public Long getUserId() {
        return userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccessToken that = (AccessToken) o;
        return Objects.equals(token, that.token) &&
                Objects.equals(expires, that.expires);
    }

    @Override
    public int hashCode() {
        return Objects.hash(token, expires);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("token", token)
                .add("expires", expires)
                .toString();
    }
}
