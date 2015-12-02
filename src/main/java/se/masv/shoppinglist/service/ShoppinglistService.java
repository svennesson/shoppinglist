package se.masv.shoppinglist.service;

import se.masv.shoppinglist.dao.ShoppingListDAO;
import se.masv.shoppinglist.dao.UserDAO;
import se.masv.shoppinglist.model.Shoppinglist;
import se.masv.shoppinglist.model.User;

import java.util.List;

import static se.masv.shoppinglist.util.EntityUtil.*;

public class ShoppinglistService {

    private final ShoppingListDAO listDao;
    private final UserDAO userDao;

    public ShoppinglistService(ShoppingListDAO listDao, UserDAO userDao) {
        this.listDao = listDao;
        this.userDao = userDao;
    }

    public Shoppinglist getShoppinglistById(final Long id) {
        Shoppinglist shoppinglist = listDao.getShoppinglistById(id);
        isValidEntity(shoppinglist, id);

        return shoppinglist;
    }

    public List<Shoppinglist> getAllShoppinglists() {
        return listDao.getAllShoppinglists();
    }

    public Shoppinglist createShoppinglist(final Shoppinglist list, final User user) {
        final Shoppinglist shoppinglist = listDao.createShoppinglist(list.getDescription(), list.getTitle());
        addListToUser(user.getId(), shoppinglist.getId());

        return shoppinglist;
    }

    public Shoppinglist updateShoppingList(final Long listId, final Shoppinglist shoppinglist) {
        final Shoppinglist listInDb = listDao.getShoppinglistById(listId);
        isValidEntity(listInDb, listId);

        return listDao.updateShoppinglist(shoppinglist.getTitle(), shoppinglist.getDescription(), listId);
    }

    public void deleteShoppinglist(final Long listId) {
        final Shoppinglist listInDb = listDao.getShoppinglistById(listId);
        isValidEntity(listInDb, listId);

        listDao.deleteShoppinglist(listId);
    }

    private void addListToUser(final Long userId, final Long listId) {
        final Shoppinglist list = listDao.getShoppinglistById(listId);
        isValidEntity(list, listId);

        userDao.addListToUser(userId, listId);
    }
}
