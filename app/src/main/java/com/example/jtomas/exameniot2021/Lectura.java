package com.example.jtomas.exameniot2021;

// 1. JAVA
public class Lectura {
    private String sensor;
    private long tiempo;
    private double valor;
    private String urlFoto;

    public Lectura() {};

    public Lectura(String sensor, long tiempo, double valor, String urlFoto) {
        this.sensor = sensor;
        this.tiempo = tiempo;
        this.valor = valor;
        this.urlFoto = urlFoto;
    }

    public String getSensor() {
        return sensor;
    }

    public void setSensor(String sensor) {
        this.sensor = sensor;
    }

    public long getTiempo() {
        return tiempo;
    }

    public void setTiempo(long tiempo) {
        this.tiempo = tiempo;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    @Override
    public String toString() {
        return "Lectura{" +
                "sensor='" + sensor + '\'' +
                ", tiempo=" + tiempo +
                ", valor=" + valor +
                ", urlFoto='" + urlFoto + '\'' +
                '}';
    }
}
