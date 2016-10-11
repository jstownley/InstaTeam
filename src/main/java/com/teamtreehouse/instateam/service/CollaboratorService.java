package com.teamtreehouse.instateam.service;

import com.teamtreehouse.instateam.model.Collaborator;
import com.teamtreehouse.instateam.model.Role;

import java.util.List;

public interface CollaboratorService {
    List<Collaborator> findAll();
    List<Collaborator> findCollaboratorsByRoleId(Long id);
    Collaborator findCollaboratorById(Long id);
    Role findRoleByCollaboratorId(Long id);
    void update(Collaborator collaborator);
    void delete(Collaborator collaborator);
}
