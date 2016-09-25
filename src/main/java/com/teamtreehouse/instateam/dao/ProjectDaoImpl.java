package com.teamtreehouse.instateam.dao;

import com.teamtreehouse.instateam.model.Project;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class ProjectDaoImpl implements ProjectDao {
    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private EntityManager entityManager;

    @Override
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

    @Override
    public Project findRoleById(Long id) {
        // Open session
        Session session = sessionFactory.openSession();

        // Do stuff
        Project project = session.get(Project.class, id);

        // Close session
        session.close();
        return project;
    }

    @Override
    public void update(Project project) {
        // Open session
        Session session = sessionFactory.openSession();

        // Do stuff
        session.beginTransaction();
        session.saveOrUpdate(project);
        session.getTransaction().commit();

        // Close session
        session.close();
    }

    @Override
    public void delete(Project project) {
        // Open session
        Session session = sessionFactory.openSession();

        // Do stuff
        session.beginTransaction();
        session.delete(project);
        session.getTransaction().commit();

        // Close session
        session.close();
    }
}
