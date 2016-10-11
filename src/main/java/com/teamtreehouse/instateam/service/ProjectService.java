package com.teamtreehouse.instateam.service;

import com.teamtreehouse.instateam.model.Collaborator;
import com.teamtreehouse.instateam.model.Project;
import com.teamtreehouse.instateam.model.Role;

import java.util.List;

public interface ProjectService {
    List<Project> findAll();
    List<Collaborator> findCollaboratorsByProjectId(Long id);
    List<Role> findRolesByProjectId(Long id);
    Project findProjectById(Long id);
    void update(Project project);
    void delete(Project project);
}
