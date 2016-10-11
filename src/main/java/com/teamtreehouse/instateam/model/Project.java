package com.teamtreehouse.instateam.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Entity
public class Project {
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Pattern(regexp = "[a-zA-Z0-9\\s]*")
    private String name;

    @NotNull
    @Pattern(regexp = "[a-zA-Z0-9\\s]*")
    private String description;

    @Pattern(regexp = "[a-zA-Z0-9_]*")
    private String status;

    @ManyToMany
    private List<Role> rolesNeeded;

    @ManyToMany
    private List<Collaborator> collaborators;

    public Project(Long id, String name, String description, String status, List<Role> rolesNeeded, List<Collaborator> collaborators) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.rolesNeeded = rolesNeeded;
        this.collaborators = collaborators;
    }

    public Project() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Role> getRolesNeeded() {
        return rolesNeeded;
    }

    public void setRolesNeeded(List<Role> rolesNeeded) {
        this.rolesNeeded = rolesNeeded;
    }

    public List<Collaborator> getCollaborators() {
        return collaborators;
    }

    public void setCollaborators(List<Collaborator> collaborators) {
        this.collaborators = collaborators;
    }
}
