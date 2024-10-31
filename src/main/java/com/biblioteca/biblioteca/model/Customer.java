package com.biblioteca.biblioteca.model;

import jakarta.persistence.Entity;

@Entity
public class Customer extends User {
    private String address;

    public Customer() {
        super();
    }

    public Customer(String name, String email, String password, String address) {
        super(name, email, password, "Cliente", true);
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String getUserDetails() {
        return "Cliente: " + getName() + ", Endereço: " + address;
    }
}
