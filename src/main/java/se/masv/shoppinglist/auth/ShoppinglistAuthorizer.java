package se.masv.shoppinglist.auth;

import io.dropwizard.auth.Authorizer;
import se.masv.shoppinglist.model.Role;
import se.masv.shoppinglist.model.User;

public class ShoppinglistAuthorizer implements Authorizer<User> {

    @Override
    public boolean authorize(User user, String role) {
        return role.equalsIgnoreCase(user.getRole());
    }
}
