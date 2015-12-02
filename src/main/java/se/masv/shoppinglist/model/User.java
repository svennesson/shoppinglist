package se.masv.shoppinglist.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.MoreObjects;
import io.dropwizard.validation.ValidationMethod;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User extends AbstractEntity implements Principal{

    @NotEmpty
    @Column(nullable = false)
    private String name;

    @NotEmpty
    @Column(nullable = false)
    private String email;

    @NotEmpty
    @Column(nullable = false)
    private String password;

    @Column(nullable = true)
    private int age;

    @Column(nullable = false)
    private String role;

    @ManyToMany(mappedBy = "users")
    private List<Shoppinglist> shoppinglists = new ArrayList<Shoppinglist>();

    @JsonIgnore
    @Transient
    private List<Long> listIds = new ArrayList<>();

    @ValidationMethod(message = "Role must be admin or user")
    public boolean isValid() {
        return role.equalsIgnoreCase(Role.ADMIN) || role.equalsIgnoreCase(Role.USER);
    }

    public User(String name, int age, String email, String password, String role) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public User(Long id, String name, int age, String email, String password, String role) {
        this.role = role;
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
        this.password = password;
    }

    public User() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Shoppinglist> getShoppinglists() {
        return shoppinglists;
    }

    public void setShoppinglists(List<Shoppinglist> shoppinglists) {
        this.shoppinglists = shoppinglists;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Long> getListIds() {
        return listIds;
    }

    public void setListIds(List<Long> listIds) {
        this.listIds = listIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return age == user.age &&
                Objects.equals(name, user.name) &&
                Objects.equals(email, user.email) &&
                Objects.equals(password, user.password) &&
                Objects.equals(role, user.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email, password, age, role);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name", name)
                .add("email", email)
                .add("age", age)
                .add("role", role)
                .add("shoppinglists", shoppinglists)
                .toString();
    }
}
