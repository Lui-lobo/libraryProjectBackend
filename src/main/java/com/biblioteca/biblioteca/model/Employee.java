package com.biblioteca.biblioteca.model;

import jakarta.persistence.*;

@Entity
public class Employee extends User{
    private String professionalProsition;

    public Employee() {
        super();
    }

    public Employee(String name, String email, String password, String professionalProsition) {
        super(name, email, password, "Funcionario", true);
        this.professionalProsition = professionalProsition;
    }

    public String getProfessionalPosition() {
        return professionalProsition;
    }

    public void setProfessionalPosition(String professionalPosition) {
        this.professionalProsition = professionalPosition;
    }

    @Override
    public String getUserDetails() {
        return "Funcionário: " + getName() + ", Cargo: " + professionalProsition;
    }
}
