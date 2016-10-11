package com.teamtreehouse.instateam.dao;

import com.teamtreehouse.instateam.model.Collaborator;
import com.teamtreehouse.instateam.model.Project;
import com.teamtreehouse.instateam.model.Role;

import java.util.List;

public interface ProjectDao {
    List<Project> findAll();
    Project findProjectById(Long id);
    void update(Project project);
    void delete(Project project);
}
