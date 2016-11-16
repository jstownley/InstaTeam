package com.teamtreehouse.instateam.dao;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public abstract class DAO {

    @Autowired
    private SessionFactory sessionFactory;
    @PersistenceContext
    private EntityManager entityManager;

    abstract public List findAll();

    abstract public Object findById(Long id);

    public void update(Object object) {
        // Open session
        Session session = sessionFactory.openSession();

        // Do stuff
        session.beginTransaction();
        session.saveOrUpdate(object);
        session.getTransaction().commit();

        // Close session
        session.close();
    }

    public void delete(Object object) {
        // Open session
        Session session = sessionFactory.openSession();

        // Do stuff
        session.beginTransaction();
        session.delete(object);
        session.getTransaction().commit();

        // Close session
        session.close();
    }


}
