/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.anthonyponte.jinvoice;

import java.util.Comparator;

/** @author nthny */
public class BillComparator implements Comparator<Bill> {

  @Override
  public int compare(Bill o1, Bill o2) {
    return o1.getCorrelativo() - o2.getCorrelativo();
  }
}
