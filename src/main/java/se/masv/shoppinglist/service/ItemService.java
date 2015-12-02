package se.masv.shoppinglist.service;

import se.masv.shoppinglist.dao.ItemDAO;
import se.masv.shoppinglist.dao.ShoppingListDAO;
import se.masv.shoppinglist.model.Item;
import se.masv.shoppinglist.model.Shoppinglist;
import se.masv.shoppinglist.util.SqlUtil;

import java.util.List;

import static se.masv.shoppinglist.util.EntityUtil.*;
import static se.masv.shoppinglist.util.SqlUtil.isNotZero;

public class ItemService {
    private final ItemDAO itemDao;
    private final ShoppingListDAO shoppingListDAO;

    public ItemService(ItemDAO itemDao, ShoppingListDAO shoppingListDAO) {
        this.itemDao = itemDao;
        this.shoppingListDAO = shoppingListDAO;
    }

    public Item getItemById(final Long listId, Long itemId) {
        final Shoppinglist shoppinglist = shoppingListDAO.getShoppinglistById(listId);
        isValidEntity(shoppinglist, listId);

        final Item item = itemDao.getItemById(itemId);
        isValidEntity(item, itemId);

        return item;
    }

    public List<Item> getAllItems() {
        return itemDao.getAllItems();
    }

    public Long createItem(final Item item, final Long listId) {
        final Shoppinglist shoppinglist = shoppingListDAO.getShoppinglistById(listId);
        isValidEntity(shoppinglist, listId);

        return itemDao.createItem(item.isChecked(), item.getPrice(), item.getQuantity(), item.getStore(), item.getText(), listId);
    }

    public Item toggleCheckedItem(final Long itemId, final Long listId) {
        final Item item = itemDao.getItemByIdAndListId(itemId, listId);
        isValidEntity(item, itemId);

        return itemDao.setChecked(listId, itemId, !item.isChecked());
    }

    public void deleteItem(final Long itemId, final Long listId) {
        final int rowsDeleted = itemDao.deleteItemByIdAndListId(itemId, listId);
        isNotZero(rowsDeleted);
    }
}
