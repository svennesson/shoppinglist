package se.masv.shoppinglist.auth;

import se.masv.shoppinglist.exception.EntityForbiddenException;
import se.masv.shoppinglist.model.Shoppinglist;
import se.masv.shoppinglist.model.User;

import java.util.List;

public class PathAccessAllowed {

    public static void shoppingListAccessForUser(final User user, final Long shoppinglistId) {
        final List<Long> listIds = user.getListIds();

        for (Long id : listIds) {
            if (id.longValue() == shoppinglistId) {
                return;
            }
        }

        throw new EntityForbiddenException("Access not allowed");
    }

    public static void userAccessAllowed(final User authUser, final Long userId) {
        if (authUser.getId().longValue() == userId.longValue()) {
            return;
        }

        throw new EntityForbiddenException("Access not allowed");
    }
}
