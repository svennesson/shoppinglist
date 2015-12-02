package se.masv.shoppinglist.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;

public class BadRequestException extends WebApplicationException {

    public BadRequestException(String message) {
        super(Response.status(BAD_REQUEST).entity(message).build());
    }

    public BadRequestException() {
        super(Response.status(BAD_REQUEST).build());
    }
}
