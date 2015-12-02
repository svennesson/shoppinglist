package se.masv.shoppinglist.dao.mapping;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;
import se.masv.shoppinglist.model.BasicCredentials;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BasicCredentialsMapper implements ResultSetMapper<BasicCredentials> {

    @Override
    public BasicCredentials map(int i, ResultSet r, StatementContext statementContext) throws SQLException {
        return new BasicCredentials(r.getString("email"), r.getString("password"));
    }
}
