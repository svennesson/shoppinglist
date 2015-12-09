package se.masv.shoppinglist.dto;

import com.google.common.base.MoreObjects;
import se.masv.shoppinglist.model.AccessToken;
import se.masv.shoppinglist.model.Role;

import java.util.Objects;

public class WebTokenResponse {

    public final AccessToken accessToken;
    public final String role;

    public WebTokenResponse(AccessToken accessToken, String role) {
        this.accessToken = accessToken;
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WebTokenResponse that = (WebTokenResponse) o;
        return Objects.equals(accessToken, that.accessToken) &&
                Objects.equals(role, that.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accessToken, role);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("accessToken", accessToken)
                .add("role", role)
                .toString();
    }
}
