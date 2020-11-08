package com.epam.esm.dao;

import com.epam.esm.exception.OpenSessionException;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import java.util.function.Function;

@RequiredArgsConstructor
public abstract class AbstractSessionDao {
    private final LocalSessionFactoryBean localSessionFactory;

    protected <T> T doWithSession(Function<Session, T> sessionFunction) {
        return sessionFunction.apply(openSession());
    }

    protected int doWithSessionTransaction(Function<Session, Integer> sessionFunction) {
        final Session session = openSession();
        session.beginTransaction();
        int updatedRows = sessionFunction.apply(session);
        session.getTransaction().commit();
        session.close();
        return updatedRows;
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
