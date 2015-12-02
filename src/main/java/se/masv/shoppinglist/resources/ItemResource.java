package se.masv.shoppinglist.resources;

import io.dropwizard.auth.Auth;
import se.masv.shoppinglist.model.Item;
import se.masv.shoppinglist.model.Role;
import se.masv.shoppinglist.model.User;
import se.masv.shoppinglist.service.ItemService;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("items")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ItemResource {

    private final ItemService itemService;

    public ItemResource(ItemService itemService) {
        this.itemService = itemService;
    }

    @RolesAllowed(Role.ADMIN)
    @GET
    public Response getAllItems(@Auth User user) {
        final List<Item> items = itemService.getAllItems();
        return Response.ok(items).build();
    }
}
