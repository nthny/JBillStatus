/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anthonyponte.jinvoice;

import io.github.millij.poi.ss.model.annotations.SheetColumn;

/** @author nthny */
public class Bill {

  @SheetColumn("RUC")
  private String ruc;

  @SheetColumn("Tipo")
  private String tipo;

  @SheetColumn("Serie")
  private String serie;

  @SheetColumn("Correlativo")
  private int correlativo;

  public Bill() {}

  public Bill(String ruc, String tipo, String serie, int correlativo) {
    this.ruc = ruc;
    this.tipo = tipo;
    this.serie = serie;
    this.correlativo = correlativo;
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
        + "ruc="
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
