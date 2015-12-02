package se.masv.shoppinglist.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class AuthenticationException extends WebApplicationException{

    public AuthenticationException() {
        super(Response.status(Response.Status.UNAUTHORIZED).build());
    }

    public AuthenticationException(String message) {
        super(Response.status(Response.Status.UNAUTHORIZED).entity(message).type(MediaType.TEXT_PLAIN).build());
    }
}
