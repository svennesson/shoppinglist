package se.masv.shoppinglist.auth;

import com.google.common.base.Optional;
import io.dropwizard.auth.Authenticator;
import se.masv.shoppinglist.exception.AuthenticationException;
import se.masv.shoppinglist.model.AccessToken;
import se.masv.shoppinglist.model.Role;
import se.masv.shoppinglist.model.User;
import se.masv.shoppinglist.service.TokenService;
import se.masv.shoppinglist.service.UserService;

public class ShoppinglistAuthenticator implements Authenticator<String, User> {

    private final UserService userService;
    private final TokenService tokenService;

    public ShoppinglistAuthenticator(UserService userService, TokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
    }

//    @Override
//    public Optional<User> authenticate(BasicCredentials basicCredentials) throws AuthenticationException {
//        final BasicCredentials credentialsInDb = userService.getUserCredentialsByEmail(basicCredentials.getUsername());
//
//        if (null == credentialsInDb || !credentialsInDb.getPassword().equals(basicCredentials.getPassword())) {
//            throw new AuthenticationException("email and/or password was incorrect, please try again");
//        }
//
//        final User user = userService.getUserProfileByEmail(basicCredentials.getUsername());
//        return Optional.fromNullable(user);
//    }

    @Override
    public Optional<User> authenticate(String token) throws AuthenticationException {
        final AccessToken accessToken = tokenService.getUserIdByToken(token);
        final User userProfile = userService.getUserProfileById(accessToken.getUserId());

        return Optional.fromNullable(userProfile);
    }
}
