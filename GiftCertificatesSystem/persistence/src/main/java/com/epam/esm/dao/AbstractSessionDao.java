package com.epam.esm.dao;

import com.epam.esm.exception.OpenSessionException;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * The {@code Abstract session dao} has methods for working with hibernate
 * sessions.
 * Class provides methods for working with and without transactions.
 *
 * @author Yuriy Kopilets
 * @version 1.0
 */
@RequiredArgsConstructor
public abstract class AbstractSessionDao {
    private final LocalSessionFactoryBean localSessionFactory;

    /**
     * Provides possibilities for working with hibernate session.
     * In the session function need to define necessary operations.
     *
     * @param <T>             the type of value witch will returned after
     *           operation defined in the function
     * @param sessionFunction the session function
     * @return the t
     */
    protected <T> T doWithSession(Function<Session, T> sessionFunction) {
        final Session session = openSession();
        T result = sessionFunction.apply(session);
        session.close();
        return result;
    }

    /**
     * Provides possibilities for working with <i>transactional</i> hibernate
     * session.
     * In the session function need to define necessary operations.
     *
     * @param sessionConsumer the session consumer
     */
    protected void doWithSessionTransaction(Consumer<Session> sessionConsumer) {
        final Session session = openSession();
        session.beginTransaction();
        sessionConsumer.accept(session);
        session.getTransaction().commit();
        session.close();
    }

    private Session openSession() {
        SessionFactory sessionFactory = localSessionFactory.getObject();
        if (sessionFactory != null) {
            return sessionFactory.openSession();
        } else {
            throw new OpenSessionException("LocalSessionFactoryBean hasn't been produced SessionFactory");
        }
    }
}
