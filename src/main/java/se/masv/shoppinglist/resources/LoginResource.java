package se.masv.shoppinglist.resources;

import se.masv.shoppinglist.dto.WebTokenResponse;
import se.masv.shoppinglist.exception.AuthenticationException;
import se.masv.shoppinglist.model.AccessToken;
import se.masv.shoppinglist.model.BasicCredentials;
import se.masv.shoppinglist.service.UserService;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("login")
public class LoginResource {

    private final UserService userService;

    public LoginResource(UserService userService) {
        this.userService = userService;
    }

    @POST
    public Response loginUser(@Valid BasicCredentials basicCredentials) {
        final WebTokenResponse token = userService.authorize(basicCredentials);

        return Response.ok(token).build();
    }
}
