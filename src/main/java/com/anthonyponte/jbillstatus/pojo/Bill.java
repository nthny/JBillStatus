package com.anthonyponte.jbillstatus.pojo;

import com.poiji.annotation.ExcelCellName;
import pe.gob.sunat.StatusResponse;

public class Bill {

  @ExcelCellName("RUC")
  private String ruc;

  @ExcelCellName("Tipo")
  private String type;

  @ExcelCellName("Serie")
  private String serie;

  @ExcelCellName("Numero")
  private int number;

  private StatusResponse billResponse;
  private StatusResponse cdrResponse;

  public Bill() {}

  public Bill(
      String ruc,
      String type,
      String serie,
      int number,
      StatusResponse billResponse,
      StatusResponse cdrResponse) {
    this.ruc = ruc;
    this.type = type;
    this.serie = serie;
    this.number = number;
    this.billResponse = billResponse;
    this.cdrResponse = cdrResponse;
  }

  public String getRuc() {
    return ruc;
  }

  public void setRuc(String ruc) {
    this.ruc = ruc;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getSerie() {
    return serie;
  }

  public void setSerie(String serie) {
    this.serie = serie;
  }

  public int getNumber() {
    return number;
  }

  public void setNumber(int number) {
    this.number = number;
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
        + ", type="
        + type
        + ", serie="
        + serie
        + ", number="
        + number
        + '}';
  }
}
