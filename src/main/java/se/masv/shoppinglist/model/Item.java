package se.masv.shoppinglist.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.MoreObjects;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
@Table(name = "items")
public class Item extends AbstractEntity{

    @NotEmpty
    @Column(nullable = false)
    private String text;

    @Column(nullable = false)
    private boolean checked;

    @Column(nullable = true)
    private String store;

    @Column(nullable = true)
    private int quantity;

    @Column(nullable = true)
    private int price;

    @JoinColumn(name = "shoppinglist_id")
    @ManyToOne
    private Shoppinglist shoppinglist;

    public Item() {}

    public Item(String text, boolean checked, String store, int quantity, int price) {
        this.text = text;
        this.checked = checked;
        this.store = store;
        this.quantity = quantity;
        this.price = price;
    }

    public Item(Long id, String text, boolean checked, String store, int quantity, int price) {
        this.id = id;
        this.text = text;
        this.checked = checked;
        this.store = store;
        this.quantity = quantity;
        this.price = price;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Shoppinglist getShoppinglist() {
        return shoppinglist;
    }

    public void setShoppinglist(Shoppinglist shoppinglist) {
        this.shoppinglist = shoppinglist;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return checked == item.checked &&
                Objects.equals(text, item.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, checked);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("text", text)
                .add("checked", checked)
                .add("store", store)
                .add("quantity", quantity)
                .add("price", price)
                .toString();
    }
}
