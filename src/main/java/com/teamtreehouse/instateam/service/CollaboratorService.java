package com.teamtreehouse.instateam.service;

import com.teamtreehouse.instateam.model.Collaborator;

import java.util.List;

public interface CollaboratorService {
    List<Collaborator> findAll();
    Collaborator findRoleById(Long id);
    void update(Collaborator collaborator);
    void delete(Collaborator collaborator);
}
