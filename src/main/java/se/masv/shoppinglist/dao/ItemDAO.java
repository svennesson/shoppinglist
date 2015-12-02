package se.masv.shoppinglist.dao;

import org.hibernate.SessionFactory;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;
import se.masv.shoppinglist.dao.mapping.ItemMapper;
import se.masv.shoppinglist.model.Item;

import java.util.List;

public interface ItemDAO{

    @SqlQuery("SELECT * FROM items")
    List<Item> getAllItems();

    @SqlQuery("SELECT * FROM items WHERE id = :id")
    Item getItemById(@Bind("id") Long id);

    @SqlQuery("INSERT INTO " +
            "items (checked, price, quantity, store, text, shoppinglist_id) " +
            "VALUES (:checked, :price, :quantity, :store, :text, :sl_id) RETURNING id")
    Long createItem(@Bind("checked") boolean checked,
                    @Bind("price") int price,
                    @Bind("quantity") int quantity,
                    @Bind("store") String store,
                    @Bind("text") String text,
                    @Bind("sl_id") Long list_id);

    @SqlQuery("UPDATE items SET checked = :checked WHERE id = :itemId AND shoppinglist_id = :listId RETURNING *")
    Item setChecked(@Bind("listId") Long listId, @Bind("itemId") Long itemId, @Bind("checked") boolean checked);

    @SqlQuery("SELECT * FROM items WHERE id = :itemId AND shoppinglist_id = :listId")
    Item getItemByIdAndListId(@Bind("itemId") Long itemId, @Bind("listId") Long listId);

    @SqlUpdate("DELETE FROM items WHERE id = :itemId AND shoppinglist_id = :listId")
    int deleteItemByIdAndListId(@Bind("itemId") Long itemId, @Bind("listId") Long listId);
}
