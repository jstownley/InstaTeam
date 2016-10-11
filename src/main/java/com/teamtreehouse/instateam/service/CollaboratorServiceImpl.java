package com.teamtreehouse.instateam.service;

import com.teamtreehouse.instateam.dao.CollaboratorDao;
import com.teamtreehouse.instateam.model.Collaborator;
import com.teamtreehouse.instateam.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CollaboratorServiceImpl implements CollaboratorService {
    @Autowired
    private CollaboratorDao collaboratorDao;

    @Override
    public List<Collaborator> findAll() {
        return collaboratorDao.findAll();
    }

    @Override
    public List<Collaborator> findCollaboratorsByRoleId(Long id) {
        List<Collaborator> collaborators = findAll();
        return collaborators.stream().filter(c->c.getRole().getId()==id).collect(Collectors.toList());
    }

    @Override
    public Collaborator findCollaboratorById(Long id) {
        return collaboratorDao.findCollaboratorById(id);
    }

    @Override
    public Role findRoleByCollaboratorId(Long id) {
        Collaborator collaborator = collaboratorDao.findCollaboratorById(id);
        return collaborator.getRole();
    }

    @Override
    public void update(Collaborator collaborator) {
        collaboratorDao.update(collaborator);
    }

    @Override
    public void delete(Collaborator collaborator) {
        collaboratorDao.delete(collaborator);
    }
}
