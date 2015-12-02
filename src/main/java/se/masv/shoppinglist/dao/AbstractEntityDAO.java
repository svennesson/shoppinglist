package se.masv.shoppinglist.dao;

import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import se.masv.shoppinglist.model.AbstractEntity;

import java.util.List;

public class AbstractEntityDAO<T extends AbstractEntity> extends AbstractDAO<T>{

    public AbstractEntityDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public T findById(Long id) {
        return get(id);
    }

    public Long createEntity(T entity) {
        return persist(entity).getId();
    }
}
