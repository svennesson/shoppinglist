package se.masv.shoppinglist.dao.mapping;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;
import se.masv.shoppinglist.model.AccessToken;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AccessTokenMapper implements ResultSetMapper<AccessToken>{

    @Override
    public AccessToken map(int i, ResultSet r, StatementContext statementContext) throws SQLException {
        return new AccessToken(r.getLong("user_id"), r.getString("token"), r.getDate("expires"));
    }
}
