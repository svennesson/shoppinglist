package se.masv.shoppinglist.dao;

import org.hibernate.SessionFactory;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;
import se.masv.shoppinglist.dao.mapping.ShoppinglistMapper;
import se.masv.shoppinglist.model.Shoppinglist;

import java.util.List;

public interface ShoppingListDAO{

    @SqlQuery("SELECT * FROM shoppinglists")
    List<Shoppinglist> getAllShoppinglists();

    @SqlQuery("SELECT * FROM shoppinglists WHERE id = :id")
    Shoppinglist getShoppinglistById(@Bind("id") Long id);

    @SqlQuery("INSERT INTO shoppinglists (description, title) VALUES (:desc, :title) RETURNING *")
    Shoppinglist createShoppinglist(@Bind("desc") String description, @Bind("title") String title);

    @SqlQuery("SELECT * " +
            "FROM shoppinglists " +
            "INNER JOIN users_has_shoppinglists " +
            "ON shoppinglists.id = users_has_shoppinglists.shoppinglist_id " +
            "WHERE users_has_shoppinglists.user_id = :userId")
    List<Shoppinglist> getShoppinglistsForUser(@Bind("userId") Long userId);

    @SqlQuery("SELECT shoppinglist_id FROM users_has_shoppinglists WHERE user_id = :userId")
    List<Long> getListIdsForUser(@Bind("userId") Long userId);

    @SqlQuery("UPDATE shoppinglists SET title = :title, description = :desc WHERE id = :id RETURNING *")
    Shoppinglist updateShoppinglist(@Bind("title") String title, @Bind("desc") String description, @Bind("id") Long listId);

    @SqlUpdate("DELETE FROM shoppinglists WHERE id = :id")
    int deleteShoppinglist(@Bind("id") Long listId);
}
