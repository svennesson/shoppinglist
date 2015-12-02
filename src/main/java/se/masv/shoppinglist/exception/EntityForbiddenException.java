package se.masv.shoppinglist.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 * Created by svennesson on 2015-12-01.
 */
public class EntityForbiddenException extends WebApplicationException {

    public EntityForbiddenException() {
        super(Response.status(Response.Status.FORBIDDEN).build());
    }

    public EntityForbiddenException(String message) {
        super(Response.status(Response.Status.FORBIDDEN).entity(message).build());
    }
}
