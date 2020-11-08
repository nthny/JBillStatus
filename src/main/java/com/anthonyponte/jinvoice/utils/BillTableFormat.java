/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.anthonyponte.jinvoice.utils;

import com.anthonyponte.jinvoice.pojo.Bill;
import ca.odell.glazedlists.gui.TableFormat;

/** @author nthny */
public class BillTableFormat implements TableFormat<Bill> {

  @Override
  public int getColumnCount() {
    return 6;
  }

  @Override
  public String getColumnName(int column) {
    switch (column) {
      case 0:
        return "RUC";
      case 1:
        return "Tipo";
      case 2:
        return "Serie";
      case 3:
        return "Correlativo";
      case 4:
        return "Estado del comprobante";
      case 5:
        return "Estado del Cdr";
      default:
        break;
    }
    throw new IllegalStateException("Unexpected column: " + column);
  }

  @Override
  public Object getColumnValue(Bill baseObject, int column) {
    switch (column) {
      case 0:
        return baseObject.getRuc();
      case 1:
        return baseObject.getTipo();
      case 2:
        return baseObject.getSerie();
      case 3:
        return baseObject.getCorrelativo();
      case 4:
        return baseObject.getBillResponse().getStatusMessage();
      case 5:
        return baseObject.getCdrResponse().getStatusMessage();
      default:
        break;
    }
    throw new IllegalStateException("Unexpected column: " + column);
  }
}
