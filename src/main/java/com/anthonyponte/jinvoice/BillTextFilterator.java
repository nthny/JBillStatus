/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.anthonyponte.jinvoice;

import ca.odell.glazedlists.TextFilterator;
import java.util.List;

/** @author nthny */
public class BillTextFilterator implements TextFilterator<Bill> {

  @Override
  public void getFilterStrings(List<String> baseList, Bill element) {
    baseList.add(element.getRuc());
    baseList.add(element.getTipo());
    baseList.add(element.getSerie());
    baseList.add(String.valueOf(element.getCorrelativo()));
    baseList.add(String.valueOf(element.getBillResponse().getStatusCode()));
    baseList.add(String.valueOf(element.getBillResponse().getStatusMessage()));
    baseList.add(String.valueOf(element.getCdrResponse().getStatusCode()));
    baseList.add(String.valueOf(element.getCdrResponse().getStatusCode()));
  }
}
