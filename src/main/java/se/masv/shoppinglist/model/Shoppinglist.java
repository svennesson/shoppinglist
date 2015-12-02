package se.masv.shoppinglist.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.MoreObjects;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "shoppinglists")
public class Shoppinglist extends AbstractEntity{

    @NotEmpty
    @Column(nullable = false)
    private String title;

    @Column(nullable = true)
    private String description;

    @OneToMany(mappedBy = "shoppinglist", fetch = FetchType.EAGER)
    List<Item> items = new ArrayList<Item>();

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "users_has_shoppinglists",
            joinColumns = @JoinColumn(name = "shoppinglist_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    public List<User> users = new ArrayList<User>();

    public Shoppinglist() {}

    public Shoppinglist(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Shoppinglist(Long id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shoppinglist that = (Shoppinglist) o;
        return Objects.equals(title, that.title) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("title", title)
                .add("description", description)
                .add("items", items)
                .toString();
    }
}
