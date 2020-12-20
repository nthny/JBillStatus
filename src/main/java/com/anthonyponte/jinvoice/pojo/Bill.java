/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anthonyponte.jinvoice.pojo;

import com.poiji.annotation.ExcelCellName;
import pe.gob.sunat.StatusResponse;

/** @author nthny */
public class Bill {

  @ExcelCellName("RUC")
  private String ruc;

  @ExcelCellName("TIPO")
  private String tipo;

  @ExcelCellName("SERIE")
  private String serie;

  @ExcelCellName("CORRELATIVO")
  private int correlativo;

  private StatusResponse billResponse;
  private StatusResponse cdrResponse;

  public Bill() {}

  public Bill(String ruc, String tipo, String serie, int correlativo) {
    this.ruc = ruc;
    this.tipo = tipo;
    this.serie = serie;
    this.correlativo = correlativo;
  }

  public Bill(
      String ruc,
      String tipo,
      String serie,
      int correlativo,
      StatusResponse billResponse,
      StatusResponse cdrResponse) {
    this.ruc = ruc;
    this.tipo = tipo;
    this.serie = serie;
    this.correlativo = correlativo;
    this.billResponse = billResponse;
    this.cdrResponse = cdrResponse;
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

  public StatusResponse getBillResponse() {
    return billResponse;
  }

  public void setBillResponse(StatusResponse billResponse) {
    this.billResponse = billResponse;
  }

  public StatusResponse getCdrResponse() {
    return cdrResponse;
  }

  public void setCdrResponse(StatusResponse cdrResponse) {
    this.cdrResponse = cdrResponse;
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
        + " BillResponse{"
        + "statusCode="
        + billResponse.getStatusCode()
        + ", statusMessage="
        + billResponse.getStatusMessage()
        + '}'
        + " CdrResponse{"
        + "statusCode="
        + cdrResponse.getStatusCode()
        + ", statusMessage="
        + cdrResponse.getStatusMessage()
        + "}"
        + "}";
  }
}
