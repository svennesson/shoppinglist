package se.masv.shoppinglist.auth;

import java.util.UUID;

public class TokenFactory {

    public static UUID generateToken() {
        return UUID.randomUUID();
    }
}
