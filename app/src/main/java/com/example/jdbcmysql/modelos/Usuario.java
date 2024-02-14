package com.example.jdbcmysql.modelos;

public class Usuario {
    private int id;
    private String nombre;
    private String email;
    private String telefono;
    private String pass;

    public Usuario(int id, String nombre, String email, String telefono, String pass) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.pass = pass;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getPass() {
        return pass;
    }
}
