package com.teamtreehouse.instateam.service;

import com.teamtreehouse.instateam.model.Project;

import java.util.List;

public interface ProjectService {
    List<Project> findAll();
    Project findRoleById(Long id);
    void update(Project project);
    void delete(Project project);
}
