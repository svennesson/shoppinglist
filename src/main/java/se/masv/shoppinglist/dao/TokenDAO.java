package se.masv.shoppinglist.dao;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import se.masv.shoppinglist.model.AccessToken;

import java.util.UUID;

public interface TokenDAO {

    @SqlQuery("SELECT * FROM tokens WHERE token = :token")
    public AccessToken getAccessTokenByToken(@Bind("token") String token);

    @SqlQuery("INSERT INTO tokens (user_id, token, expires) VALUES (:userId, :token, (SELECT current_date + interval '1 month')) RETURNING *")
    public AccessToken insertUserToken(@Bind("userId") Long userId, @Bind("token") UUID token);

    @SqlUpdate("DELETE FROM tokens WHERE user_id = :userId")
    public int deleteToken(@Bind("userId") Long userId);

    @SqlQuery("SELECT * FROM tokens WHERE user_id = :userId")
    public AccessToken checkIfAlreadyLoggedIn(@Bind("userId") Long userId);
}
