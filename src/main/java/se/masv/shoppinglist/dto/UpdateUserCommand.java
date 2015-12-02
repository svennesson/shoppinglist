package se.masv.shoppinglist.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import io.dropwizard.validation.ValidationMethod;

import javax.validation.constraints.NotNull;
import java.util.Objects;

public class UpdateUserCommand {

    @NotNull
    public final int age;

    @NotNull
    public final String name;

    @NotNull
    public final String email;

    @JsonCreator
    public UpdateUserCommand(@JsonProperty("age") int age, @JsonProperty("name") String name, @JsonProperty("email") String email) {
        this.age = age;
        this.name = name;
        this.email = email;
    }

    @ValidationMethod(message = "All fields must be entered")
    public boolean isValid() {
        return null != name || null != email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UpdateUserCommand that = (UpdateUserCommand) o;
        return age == that.age &&
                Objects.equals(name, that.name) &&
                Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(age, name, email);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("age", age)
                .add("name", name)
                .add("email", email)
                .toString();
    }
}
