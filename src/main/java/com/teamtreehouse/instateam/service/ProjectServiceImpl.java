package com.teamtreehouse.instateam.service;

import com.teamtreehouse.instateam.dao.ProjectDao;
import com.teamtreehouse.instateam.model.Collaborator;
import com.teamtreehouse.instateam.model.Project;
import com.teamtreehouse.instateam.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService{
    @Autowired
    private ProjectDao projectDao;
    @Autowired
    private RoleService roleService;
    @Autowired
    private CollaboratorService collaboratorService;

    @Override
    public List<Project> findAll() {
        return projectDao.findAll();
    }

    @Override
    public List<Collaborator> findCollaboratorsByProjectId(Long id) {
        Project project = projectDao.findProjectById(id);
        return project.getCollaborators();
    }

    @Override
    public List<Role> findRolesByProjectId(Long id) {
        Project project = projectDao.findProjectById(id);
        return project.getRolesNeeded();
    }

    @Override
    public Project findProjectById(Long id) {
        return projectDao.findProjectById(id);
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
