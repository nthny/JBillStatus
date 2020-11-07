/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.anthonyponte.jinvoice;

import pe.gob.sunat.BillService;
import pe.gob.sunat.StatusResponse;

/** @author nthny */
public class BillServiceImpl implements BillService {

  @Override
  public StatusResponse getStatusCdr(
      String rucComprobante,
      String tipoComprobante,
      String serieComprobante,
      Integer numeroComprobante) {
    throw new UnsupportedOperationException(
        "Not supported yet."); // To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public StatusResponse getStatus(
      String rucComprobante,
      String tipoComprobante,
      String serieComprobante,
      Integer numeroComprobante) {
    throw new UnsupportedOperationException(
        "Not supported yet."); // To change body of generated methods, choose Tools | Templates.
  }
}
