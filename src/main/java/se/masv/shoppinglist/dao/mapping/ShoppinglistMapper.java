package se.masv.shoppinglist.dao.mapping;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;
import se.masv.shoppinglist.model.Shoppinglist;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ShoppinglistMapper implements ResultSetMapper<Shoppinglist> {

    @Override
    public Shoppinglist map(int i, ResultSet r, StatementContext statementContext) throws SQLException {
        return new Shoppinglist(r.getLong("id"), r.getString("title"), r.getString("description"));
    }
}
