package se.masv.shoppinglist.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class EntityNotFoundException extends WebApplicationException{

    public EntityNotFoundException() {
        super(Response.status(Status.NOT_FOUND).build());
    }

    public EntityNotFoundException(String message) {
        super(Response.status(Status.NOT_FOUND)
                .entity(message)
                .type(MediaType.TEXT_PLAIN)
                .build());
    }
}
