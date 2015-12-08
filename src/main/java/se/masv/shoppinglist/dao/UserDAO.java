package se.masv.shoppinglist.dao;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import se.masv.shoppinglist.model.BasicCredentials;
import se.masv.shoppinglist.model.User;

import java.util.List;

public interface UserDAO{

    @SqlQuery("SELECT * FROM users WHERE id = :id")
    User getUserById(@Bind("id") Long userId);

    @SqlQuery("SELECT * FROM users Where email = :email")
    User getUserByEmail(@Bind("email") String email);

    @SqlQuery("SELECT email, password FROM users WHERE email = :email")
    BasicCredentials getUserCredentials(@Bind("email") String email);

    @SqlQuery("SELECT * FROM users")
    List<User> getAllUsers();

    @SqlQuery("INSERT INTO users (age, email, name, password, role) VALUES (:age, :email, :name, :pass, :role) RETURNING id")
    Long createUser(@Bind("age") int age, @Bind("email") String email, @Bind("name") String name, @Bind("pass") String password, @Bind("role") String role);

    @SqlUpdate("INSERT INTO users_has_shoppinglists (shoppinglist_id, user_id) VALUES (:list_id, :user_id)")
    int addListToUser(@Bind("user_id") Long userId, @Bind("list_id") Long listId);

    @SqlUpdate("UPDATE users SET age = :age, name = :name, email = :email WHERE id = :id")
    int updateUser(@Bind("id") Long userId, @Bind("age") int age, @Bind("name") String name, @Bind("email") String email);

    @SqlQuery("UPDATE users SET role = :role WHERE id = :id RETURNING *")
    User changeRole(@Bind("role") String role, @Bind("id") Long userId);
}
