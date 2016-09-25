package com.teamtreehouse.instateam.dao;

import com.teamtreehouse.instateam.model.Project;

import java.util.List;

public interface ProjectDao {
    List<Project> findAll();
    Project findRoleById(Long id);
    void update(Project project);
    void delete(Project project);
}
