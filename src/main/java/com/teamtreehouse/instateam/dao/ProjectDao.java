package com.teamtreehouse.instateam.dao;

import com.teamtreehouse.instateam.model.Collaborator;
import com.teamtreehouse.instateam.model.Project;
import com.teamtreehouse.instateam.model.Role;
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
public class ProjectDao extends DAO {
    @Autowired
    private SessionFactory sessionFactory;
    @PersistenceContext
    private EntityManager entityManager;

    public List<Project> findAll() {
        // Open session
        Session session = sessionFactory.openSession();

        // Do stuff (session.createCriteria is deprecated and this is what Hibernate suggests)
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Project> criteria = cb.createQuery(Project.class);
        Root<Project> projectRoot = criteria.from(Project.class);
        criteria.select(projectRoot);
        List<Project> projects = entityManager.createQuery(criteria).getResultList();

        // Close session
        session.close();
        return projects;
    }

    public Project findById(Long id) {
        // Open session
        Session session = sessionFactory.openSession();

        // Do stuff
        Project project = session.get(Project.class, id);

        // Close session
        Hibernate.initialize(project.getCollaborators());
        Hibernate.initialize(project.getRolesNeeded());
        session.close();
        return project;
    }
}
