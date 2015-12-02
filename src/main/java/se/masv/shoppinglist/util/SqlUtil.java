package se.masv.shoppinglist.util;

import se.masv.shoppinglist.exception.AuthenticationException;
import se.masv.shoppinglist.exception.BadRequestException;
import se.masv.shoppinglist.model.AccessToken;

public class SqlUtil {

    public static boolean isNotZero(final AccessToken token) {
        if (token != null && token.getUserId() > 0) {
            return true;
        }

        throw new AuthenticationException("Token invalid");
    }

    public static void isNotZero(final int rows) {
        if (rows > 0) {
            return;
        }

        throw new BadRequestException("Not deleted");
    }
}
