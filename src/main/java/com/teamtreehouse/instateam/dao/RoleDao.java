package com.teamtreehouse.instateam.dao;

import com.teamtreehouse.instateam.model.Role;
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

@Repository
public class RoleDao extends DAO {
    @Autowired
    private SessionFactory sessionFactory;
    @PersistenceContext
    private EntityManager entityManager;

    public List<Role> findAll() {
        // Open a session
        Session session = sessionFactory.openSession();

        // Do stuff (session.createCriteria is deprecated and this is what Hibernate suggests)
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Role> criteria = cb.createQuery(Role.class);
        Root<Role> roleRoot = criteria.from(Role.class);
        criteria.select(roleRoot);
        List<Role> roles = entityManager.createQuery(criteria).getResultList();

        // Close session
        session.close();
        return roles;
    }

    public Role findById(Long id) {
        // Open a session
        Session session = sessionFactory.openSession();

        // Do stuff
        Role role = session.get(Role.class, id);

        // Close session
        session.close();
        return role;
    }
}
