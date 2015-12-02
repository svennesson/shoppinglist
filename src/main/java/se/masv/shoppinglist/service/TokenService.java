package se.masv.shoppinglist.service;

import se.masv.shoppinglist.dao.TokenDAO;
import se.masv.shoppinglist.exception.AuthenticationException;
import se.masv.shoppinglist.model.AccessToken;

import java.util.Date;
import java.util.UUID;

import static se.masv.shoppinglist.util.SqlUtil.*;

public class TokenService {

    private final TokenDAO tokenDao;

    public TokenService(TokenDAO tokenDao) {
        this.tokenDao = tokenDao;
    }

    public AccessToken getUserIdByToken(final String token) {
        final AccessToken accessToken = tokenDao.getAccessTokenByToken(token);
        isNotZero(accessToken);
        checkValidToken(accessToken);

        return accessToken;
    }


    public AccessToken insertUserToken(final Long userId, final UUID token) {
        return tokenDao.insertUserToken(userId, token);
    }

    public int deleteUserToken(final Long userId) {
        return tokenDao.deleteToken(userId);
    }

    private void checkValidToken(AccessToken accessToken) {
        final Date date = new Date();

        if (date.after(accessToken.getExpires())) {
            tokenDao.deleteToken(accessToken.getUserId());
            throw new AuthenticationException("User not authenticated, please log in");
        }
    }
}
