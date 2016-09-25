package com.teamtreehouse.instateam.service;

import com.teamtreehouse.instateam.dao.CollaboratorDao;
import com.teamtreehouse.instateam.model.Collaborator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollaboratorServiceImpl implements CollaboratorService {
    @Autowired
    private CollaboratorDao collaboratorDao;

    @Override
    public List<Collaborator> findAll() {
        return collaboratorDao.findAll();
    }

    @Override
    public Collaborator findRoleById(Long id) {
        return collaboratorDao.findRoleById(id);
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
