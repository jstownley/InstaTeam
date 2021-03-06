package com.teamtreehouse.instateam.dao;

import com.teamtreehouse.instateam.model.Collaborator;
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

@Repository
public class CollaboratorDao extends DAO {
    @Autowired
    private SessionFactory sessionFactory;
    @PersistenceContext
    private EntityManager entityManager;

    public List<Collaborator> findAll() {
        // Open session
        Session session = sessionFactory.openSession();

        // Do stuff (session.createCriteria is deprecated and this is what Hibernate suggests)
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Collaborator> criteria = cb.createQuery(Collaborator.class);
        Root<Collaborator> collaboratorRoot = criteria.from(Collaborator.class);
        criteria.select(collaboratorRoot);
        List<Collaborator> collaborators = entityManager.createQuery(criteria).getResultList();

        // Close session
        session.close();
        return collaborators;
    }

    public Collaborator findById(Long id) {
        // Open session
        Session session = sessionFactory.openSession();

        // Do stuff
        Collaborator collaborator = session.get(Collaborator.class, id);

        // Close session
        Hibernate.initialize(collaborator.getRole());
        session.close();
        return collaborator;
    }
}
