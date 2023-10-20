package com.example.pm1e17321.Models;

import androidx.appcompat.app.AppCompatActivity;

public class Contactos{

    private Integer id;

    private String Pais;
    private String nombres;

    private int telefono;

    private String nota;

    public Contactos() {
    }

    public Contactos(Integer id, String Pais, String nombres, int telefono, String nota) {
        this.id = id;
        this.Pais = Pais;
        this.nombres = nombres;
        this.telefono = telefono;
        this.nota = nota;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPais() {
        return Pais;
    }

    public void setPais(String pais) {
        Pais = pais;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }
}

