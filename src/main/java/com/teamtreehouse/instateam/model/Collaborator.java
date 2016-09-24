package com.teamtreehouse.instateam.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
public class Collaborator {
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Pattern(regexp = "[A-Z]{1}[a-z]*\\s[A-Z]{1}[a-z]*")
    private String name;

    @NotNull
    @ManyToOne
    private Role role;

    public Collaborator(Long id, String name, Role role) {
        this.id = id;
        this.name = name;
        this.role = role;
    }

    public Collaborator() {
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
