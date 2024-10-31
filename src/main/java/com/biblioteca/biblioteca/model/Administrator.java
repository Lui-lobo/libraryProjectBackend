package com.biblioteca.biblioteca.model;

import jakarta.persistence.Entity;

@Entity
public class Administrator extends User {
    private String accessLevel;

    public Administrator() {
        super();
    }

    public Administrator(String name, String email, String password, String accessLevel) {
        super(name, email, password, "Administrador", true);
        this.accessLevel = accessLevel;
    }

    public String getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(String accessLevel) {
        this.accessLevel = accessLevel;
    }

    @Override
    public String getUserDetails() {
        return "Administrador: " + getName() + ", Nível de Acesso: " + accessLevel;
    }
}
