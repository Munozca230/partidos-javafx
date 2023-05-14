package com.example.sena;

import java.time.LocalDate;
import java.util.Date;

public class Partido {
    private Long id;
    private String equipoContrincante;
    private int golesRecibidos;
    private int golesMarcados;
    private double avgGoles;
    private String lugar;
    private LocalDate fecha;
    private int amarillasColombia;
    private int amarillasContrincante;
    private int rojasColombia;
    private int rojasContrincante;
    private String tipoPartido;
    private String colorCamisa;

    public Partido(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEquipoContrincante() {
        return equipoContrincante;
    }

    public void setEquipoContrincante(String equipoContrincante) {
        this.equipoContrincante = equipoContrincante;
    }

    public int getGolesRecibidos() {
        return golesRecibidos;
    }

    public void setGolesRecibidos(int golesRecibidos) {
        this.golesRecibidos = golesRecibidos;
    }

    public int getGolesMarcados() {
        return golesMarcados;
    }

    public void setGolesMarcados(int golesMarcados) {
        this.golesMarcados = golesMarcados;
    }

    public double getAvgGoles() {
        return avgGoles;
    }

    public void setAvgGoles(double avgGoles) {
        this.avgGoles = avgGoles;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public int getAmarillasColombia() {
        return amarillasColombia;
    }

    public void setAmarillasColombia(int amarillasColombia) {
        this.amarillasColombia = amarillasColombia;
    }

    public int getAmarillasContrincante() {
        return amarillasContrincante;
    }

    public void setAmarillasContrincante(int amarillasContrincante) {
        this.amarillasContrincante = amarillasContrincante;
    }

    public int getRojasColombia() {
        return rojasColombia;
    }

    public void setRojasColombia(int rojasColombia) {
        this.rojasColombia = rojasColombia;
    }

    public int getRojasContrincante() {
        return rojasContrincante;
    }

    public void setRojasContrincante(int rojasContrincante) {
        this.rojasContrincante = rojasContrincante;
    }

    public String getTipoPartido() {
        return tipoPartido;
    }

    public void setTipoPartido(String tipoPartido) {
        this.tipoPartido = tipoPartido;
    }

    public String getColorCamisa() {
        return colorCamisa;
    }

    public void setColorCamisa(String colorCamisa) {
        this.colorCamisa = colorCamisa;
    }
}
