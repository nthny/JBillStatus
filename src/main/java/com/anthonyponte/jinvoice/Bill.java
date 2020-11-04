/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anthonyponte.jinvoice;

/** @author nthny */
public class Bill {

    private long id;
    private String ruc;
    private String tipo;
    private String serie;
    private int correlativo;

    public Bill() {}

    public Bill(long id, String ruc, String tipo, String serie, int correlativo) {
        this.id = id;
        this.ruc = ruc;
        this.tipo = tipo;
        this.serie = serie;
        this.correlativo = correlativo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public int getCorrelativo() {
        return correlativo;
    }

    public void setCorrelativo(int correlativo) {
        this.correlativo = correlativo;
    }

    @Override
    public String toString() {
        return "Bill{"
                + "id="
                + id
                + ", ruc="
                + ruc
                + ", tipo="
                + tipo
                + ", serie="
                + serie
                + ", correlativo="
                + correlativo
                + '}';
    }
}
