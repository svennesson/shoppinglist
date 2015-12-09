package se.masv.shoppinglist.service;

import se.masv.shoppinglist.auth.TokenFactory;
import se.masv.shoppinglist.dao.ShoppingListDAO;
import se.masv.shoppinglist.dao.TokenDAO;
import se.masv.shoppinglist.dao.UserDAO;
import se.masv.shoppinglist.dto.UpdateUserCommand;
import se.masv.shoppinglist.dto.WebTokenResponse;
import se.masv.shoppinglist.exception.AuthenticationException;
import se.masv.shoppinglist.model.*;
import se.masv.shoppinglist.util.PBKDF2Hash;

import java.util.List;
import java.util.UUID;

import static se.masv.shoppinglist.util.EntityUtil.*;

public class UserService {

    private final UserDAO userDao;
    private final ShoppingListDAO listDao;
    private final TokenDAO tokenDao;

    public UserService(UserDAO userDao, ShoppingListDAO listDao, TokenDAO tokenDao) {
        this.userDao = userDao;
        this.listDao = listDao;
        this.tokenDao = tokenDao;
    }

    public User getUserProfileById(final Long id) {
        final User user = userDao.getUserById(id);
        isValidEntity(user, id);

        final List<Long> listIds = listDao.getListIdsForUser(id);
        user.setListIds(listIds);

        return user;
    }

    public User getUserProfileByEmail(String email) {
        final User user = userDao.getUserByEmail(email);
        isValidEntity(user, email);

        return user;
    }

    public BasicCredentials getUserCredentialsByEmail(final String email) {
        return userDao.getUserCredentials(email);
    }

    public User getUserById(final Long id) {
        final User user = getUserProfileById(id);
        final List<Shoppinglist> lists = getShoppinglistsForUser(id);
        user.setShoppinglists(lists);

        return user;
    }

    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    public Long createUser(final User user) {
        user.setPassword(PBKDF2Hash.createHash(user.getPassword()));

        return userDao.createUser(user.getAge(), user.getEmail(), user.getName(), user.getPassword(), user.getRole());
    }

    private List<Shoppinglist> getShoppinglistsForUser(Long userId) {
        return listDao.getShoppinglistsForUser(userId);
    }

    public void addListToUser(final Long userId, final Long listId) {
        final User user = userDao.getUserById(userId);
        isValidEntity(user, userId);

        final Shoppinglist list = listDao.getShoppinglistById(listId);
        isValidEntity(list, listId);

        userDao.addListToUser(userId, listId);
    }

    public int updateUser(final UpdateUserCommand user, final Long userId) {
        final User userInDb = userDao.getUserById(userId);
        isValidEntity(userInDb, userId);

        return userDao.updateUser(userId, user.age, user.name, user.email);
    }

    public WebTokenResponse authorize(BasicCredentials basicCredentials) {
        final BasicCredentials userCredentialsInDb = userDao.getUserCredentials(basicCredentials.getEmail());

        if (null == userCredentialsInDb) {
            throw new AuthenticationException("Wrong email and/or password");
        }

        final boolean valid = validatePassword(basicCredentials.getPassword(), userCredentialsInDb.getPassword());

        if (valid) {
            final User user = userDao.getUserByEmail(userCredentialsInDb.getEmail());
            final UUID uuid = TokenFactory.generateToken();

            if (tokenDao.checkIfAlreadyLoggedIn(user.getId()) != null) {
                tokenDao.deleteToken(user.getId());
            }

            final AccessToken accessToken = tokenDao.insertUserToken(user.getId(), uuid);

            return new WebTokenResponse(accessToken, user.getRole());
        }

        throw new AuthenticationException("Wrong email and/or password");
    }

    public User makeUserAdmin(Long userId) {
        final User user = userDao.getUserById(userId);
        isValidEntity(user, userId);

        return userDao.changeRole(Role.ADMIN, userId);
    }

    private boolean validatePassword(String password, String correctHash) {
        return PBKDF2Hash.validatePassword(password, correctHash);
    }
}
