package se.masv.shoppinglist.dao.mapping;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;
import se.masv.shoppinglist.model.Role;
import se.masv.shoppinglist.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements ResultSetMapper<User>{

    @Override
    public User map(int i, ResultSet r, StatementContext statementContext) throws SQLException {
        return new User(r.getLong("id"), r.getString("name"), r.getInt("age"), r.getString("email"), null, r.getString("role"));
    }
}
