package se.masv.shoppinglist.resources;

import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;
import se.masv.shoppinglist.auth.PathAccessAllowed;
import se.masv.shoppinglist.dto.UpdateUserCommand;
import se.masv.shoppinglist.model.Role;
import se.masv.shoppinglist.model.User;
import se.masv.shoppinglist.service.UserService;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import static se.masv.shoppinglist.auth.PathAccessAllowed.shoppingListAccessForUser;
import static se.masv.shoppinglist.auth.PathAccessAllowed.userAccessAllowed;

@Path("users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    private final UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @RolesAllowed(Role.ADMIN)
    @GET
    public Response getAllUsers() {
        List<User> users = userService.getAllUsers();
        return Response.ok(users).build();
    }

    @GET
    @Path("/{id}")
    public Response getUser(@Auth User user, @PathParam("id") final Long id) {
        userAccessAllowed(user, id);
        final User userInDb = userService.getUserById(id);
        return Response.ok(userInDb).build();
    }

    @GET
    @Path("/{id}/profile")
    public Response getUserProfile(@Auth User user, @PathParam("id") final Long userId) {
        userAccessAllowed(user, userId);
        final User userInDb = userService.getUserProfileById(userId);
        return Response.ok(userInDb).build();
    }

    @POST
    public Response createUser(@Valid User user) {
        final Long userId = userService.createUser(user);
        return Response.ok(userId).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateUser(@Auth User authUser, @PathParam("id") Long userId, @Valid final UpdateUserCommand user) {
        userAccessAllowed(authUser, userId);
        userService.updateUser(user, userId);
        return Response.ok(userId).build();
    }

    @PUT
    @Path("/{userId}/lists/{listId}")
    public Response addListToUser(@Auth User user, @PathParam("userId") final Long userId, @PathParam("listId") final Long listId) {
        shoppingListAccessForUser(user, listId);
        userService.addListToUser(userId, listId);
        return Response.ok().build();
    }
}
