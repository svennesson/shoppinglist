package se.masv.shoppinglist.resources;

import io.dropwizard.auth.Auth;
import se.masv.shoppinglist.auth.PathAccessAllowed;
import se.masv.shoppinglist.model.Role;
import se.masv.shoppinglist.model.User;
import se.masv.shoppinglist.service.ShoppinglistService;
import se.masv.shoppinglist.model.Item;
import se.masv.shoppinglist.model.Shoppinglist;
import se.masv.shoppinglist.service.ItemService;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import static se.masv.shoppinglist.auth.PathAccessAllowed.shoppingListAccessForUser;

@Path("lists")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ShoppinglistResource {

    private final ShoppinglistService listService;
    private final ItemService itemService;

    public ShoppinglistResource(ShoppinglistService shoppinglistService, ItemService itemService) {
        this.listService = shoppinglistService;
        this.itemService = itemService;
    }

    @RolesAllowed(Role.ADMIN)
    @GET
    public Response getAllShoppinglists() {
        final List<Shoppinglist> lists = listService.getAllShoppinglists();
        return Response.ok(lists).build();
    }

    @GET
    @Path("/{id}")
    public Response getShoppinglist(@Auth User user, @PathParam("id") final Long id) {
        shoppingListAccessForUser(user, id);
        final Shoppinglist list = listService.getShoppinglistById(id);

        return Response.ok(list).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateShoppingList(@Auth User user, @PathParam("id") final Long id, @Valid Shoppinglist shoppinglist) {
        shoppingListAccessForUser(user, id);
        final Shoppinglist list = listService.updateShoppingList(id, shoppinglist);

        return Response.ok(list).build();
    }

    @POST
    public Response createShoppinglist(@Auth User user, @Valid Shoppinglist list) {
        Shoppinglist shoppinglist = listService.createShoppinglist(list, user);
        return Response.ok(shoppinglist).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteShoppinglist(@Auth User user, @PathParam("id") final Long listId) {
        shoppingListAccessForUser(user, listId);
        listService.deleteShoppinglist(listId);

        return Response.ok().build();
    }

    @POST
    @Path("/{listId}/items")
    public Response addItem(@Auth User user, @PathParam("listId") final Long listId, @Valid Item item) {
        shoppingListAccessForUser(user, listId);
        final Long id = itemService.createItem(item, listId);

        return Response.ok(id).build();
    }

    @GET
    @Path("/{listId}/items/{itemId}")
    public Response getItem(@Auth User user, @PathParam("listId") final Long listId, @PathParam("itemId") final Long itemId) {
        shoppingListAccessForUser(user, listId);
        final Item item = itemService.getItemById(listId, itemId);

        return Response.ok(item).build();
    }

    @PUT
    @Path("/{listId}/items/{itemId}/checked")
    public Response toggleCheckItem(@Auth User user, @PathParam("listId") final Long listId, @PathParam("itemId") final Long itemId) {
        shoppingListAccessForUser(user, listId);
        final Item item = itemService.toggleCheckedItem(itemId, listId);

        return Response.ok(item).build();
    }

    @DELETE
    @Path("/{listId}/items/{itemId}")
    public Response deleteItem(@Auth User user, @PathParam("listId") final Long listId, @PathParam("itemId") final Long itemId) {
        shoppingListAccessForUser(user, listId);
        itemService.deleteItem(itemId, listId);

        return Response.ok().build();
    }

}
