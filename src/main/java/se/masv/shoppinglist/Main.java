package se.masv.shoppinglist;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.oauth.OAuthCredentialAuthFilter;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.reflections.Reflections;
import org.skife.jdbi.v2.DBI;
import se.masv.shoppinglist.auth.ShoppinglistAuthenticator;
import se.masv.shoppinglist.auth.ShoppinglistAuthorizer;
import se.masv.shoppinglist.config.DBIFactory;
import se.masv.shoppinglist.config.ShoppinglistConfiguration;
import se.masv.shoppinglist.dao.ItemDAO;
import se.masv.shoppinglist.dao.ShoppingListDAO;
import se.masv.shoppinglist.dao.TokenDAO;
import se.masv.shoppinglist.dao.UserDAO;
import se.masv.shoppinglist.model.Item;
import se.masv.shoppinglist.model.Shoppinglist;
import se.masv.shoppinglist.model.User;
import se.masv.shoppinglist.resources.ItemResource;
import se.masv.shoppinglist.resources.LoginResource;
import se.masv.shoppinglist.resources.ShoppinglistResource;
import se.masv.shoppinglist.resources.UserResource;
import se.masv.shoppinglist.service.ItemService;
import se.masv.shoppinglist.service.ShoppinglistService;
import se.masv.shoppinglist.service.TokenService;
import se.masv.shoppinglist.service.UserService;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.util.EnumSet;

import static org.eclipse.jetty.servlets.CrossOriginFilter.*;

public class Main extends Application<ShoppinglistConfiguration>{

    private final HibernateBundle<ShoppinglistConfiguration> hibernate = new HibernateBundle<ShoppinglistConfiguration>(User.class, Item.class, Shoppinglist.class) {
        public DataSourceFactory getDataSourceFactory(ShoppinglistConfiguration shoppinglistConfiguration) {
            return shoppinglistConfiguration.getDataSourceFactory();
        }
    };

    @Override
    public void initialize(Bootstrap<ShoppinglistConfiguration> bootstrap) {
        bootstrap.addBundle(hibernate);
    }

    public static void main(String[] args) throws Exception{
        new Main().run(args);
    }

    @Override
    public void run(ShoppinglistConfiguration configuration, Environment environment) throws Exception {
        environment.getObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);

        //CORS
        final FilterRegistration.Dynamic corsFilter = environment.servlets().addFilter("CORSFilter", CrossOriginFilter.class);
        corsFilter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), false, "/*");
        corsFilter.setInitParameter(ALLOWED_METHODS_PARAM, "GET, PUT, POST, OPTIONS, DELETE, HEAD");
        corsFilter.setInitParameter(ALLOWED_ORIGINS_PARAM, "*");
        corsFilter.setInitParameter(ALLOWED_HEADERS_PARAM, "Origin, Content-Type, Accept, Authorization");
        corsFilter.setInitParameter(ALLOW_CREDENTIALS_PARAM, "true");

        //Reflections
        final Reflections reflections = new Reflections("se.masv.shoppinglist");

        //JDBI
        final DBIFactory factory = new DBIFactory(reflections);
        final DBI jdbi = factory.build(environment, configuration.getDataSourceFactory(), "postgresql");

        //DAO
        final UserDAO userDao = jdbi.onDemand(UserDAO.class);
        final ShoppingListDAO listDao = jdbi.onDemand(ShoppingListDAO.class);
        final ItemDAO itemDao = jdbi.onDemand(ItemDAO.class);
        final TokenDAO tokenDao = jdbi.onDemand(TokenDAO.class);

        //Service
        final UserService userService = new UserService(userDao, listDao, tokenDao);
        final ShoppinglistService shoppinglistService = new ShoppinglistService(listDao, userDao);
        final ItemService itemService = new ItemService(itemDao, listDao);
        final TokenService tokenService = new TokenService(tokenDao);

        //Auth
        environment.jersey().register(new AuthDynamicFeature(
                new OAuthCredentialAuthFilter.Builder<User>()
                .setAuthenticator(new ShoppinglistAuthenticator(userService, tokenService))
                .setAuthorizer(new ShoppinglistAuthorizer())
                .setPrefix("Bearer")
                .buildAuthFilter()));

        environment.jersey().register(RolesAllowedDynamicFeature.class);
        environment.jersey().register(new AuthValueFactoryProvider.Binder<>(User.class));

        //Jersey resources
        environment.jersey().register(new UserResource(userService));
        environment.jersey().register(new ShoppinglistResource(shoppinglistService, itemService));
        environment.jersey().register(new ItemResource(itemService));
        environment.jersey().register(new LoginResource(userService));
    }
}
