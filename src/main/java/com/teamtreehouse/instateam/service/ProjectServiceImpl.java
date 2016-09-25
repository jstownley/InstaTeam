package com.teamtreehouse.instateam.service;

import com.teamtreehouse.instateam.dao.ProjectDao;
import com.teamtreehouse.instateam.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService{
    @Autowired
    private ProjectDao projectDao;

    @Override
    public List<Project> findAll() {
        return projectDao.findAll();
    }

    @Override
    public Project findRoleById(Long id) {
        return projectDao.findRoleById(id);
    }

    @Override
    public void update(Project project) {
        projectDao.update(project);
    }

    @Override
    public void delete(Project project) {
        projectDao.delete(project);
    }
}
