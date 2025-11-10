package com.f26.cascosf26_2.model;

public class Casco {
    private int idCasco;
    private String nombreCasco;
    private String modelo;
    private int anioFabricacion;
    private String provisiones;
    private String documentos;

    public Casco(int idCasco, String nombreCasco, String modelo, int anioFabricacion, String provisiones, String documentos) {
        this.idCasco = idCasco;
        this.nombreCasco = nombreCasco;
        this.modelo = modelo;
        this.anioFabricacion = anioFabricacion;
        this.provisiones = provisiones;
        this.documentos = documentos;
    }

    // Getters y Setters

    public int getIdCasco() {
        return idCasco;
    }

    public void setIdCasco(int idCasco) {
        this.idCasco = idCasco;
    }

    public String getNombreCasco() {
        return nombreCasco;
    }

    public void setNombreCasco(String nombreCasco) {
        this.nombreCasco = nombreCasco;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getAnioFabricacion() {
        return anioFabricacion;
    }

    public void setAnioFabricacion(int anioFabricacion) {
        this.anioFabricacion = anioFabricacion;
    }

    public String getProvisiones() {
        return provisiones;
    }

    public void setProvisiones(String provisiones) {
        this.provisiones = provisiones;
    }

    public String getDocumentos() {
        return documentos;
    }

    public void setDocumentos(String documentos) {
        this.documentos = documentos;
    }
}
