package se.masv.shoppinglist.dao.mapping;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;
import se.masv.shoppinglist.model.Item;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemMapper implements ResultSetMapper<Item>{

    @Override
    public Item map(int i, ResultSet r, StatementContext statementContext) throws SQLException {
        return new Item(r.getLong("id"),
                        r.getString("text"),
                        r.getBoolean("checked"),
                        r.getString("store"),
                        r.getInt("quantity"),
                        r.getInt("price"));
    }
}
