package com.teamtreehouse.instateam.dao;

import com.teamtreehouse.instateam.model.Collaborator;

import java.util.List;

public interface CollaboratorDao {
    List<Collaborator> findAll();
    Collaborator findRoleById(Long id);
    void update(Collaborator collaborator);
    void delete(Collaborator collaborator);
}
