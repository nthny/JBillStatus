/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.anthonyponte.jinvoice.utils;

import com.anthonyponte.jinvoice.pojo.Bill;
import com.anthonyponte.jinvoice.controller.MainController;
import com.anthonyponte.jinvoice.view.MainFrame;
import ca.odell.glazedlists.EventList;
import com.anthonyponte.jinvoice.view.LoadingWindow;
import io.github.millij.poi.SpreadsheetReadException;
import io.github.millij.poi.ss.reader.XlsReader;
import io.github.millij.poi.ss.reader.XlsxReader;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import pe.gob.sunat.BillService;

/** @author nthny */
public class BillWorker extends SwingWorker<List<Bill>, Object> {

  private final MainFrame mainFrame;
  private final LoadingWindow window;
  private final File file;
  private final EventList<Bill> eventList;

  public BillWorker(MainFrame mainFrame, File file, EventList<Bill> eventList) {
    this.mainFrame = mainFrame;
    this.file = file;
    this.eventList = eventList;
    this.window = new LoadingWindow();
  }

  @Override
  protected List<Bill> doInBackground() throws Exception {
    window.setVisible(true);
    try {
      if (file.getName().endsWith(".xls")) {
        List<Bill> bills = new XlsReader().read(Bill.class, file);
        return status(bills);
      } else if (file.getName().endsWith(".xlsx")) {
        List<Bill> bills = new XlsxReader().read(Bill.class, file);
        return status(bills);
      } else {
        JOptionPane.showMessageDialog(
            mainFrame, "El archivo debe ser .xls o .xlsx", "Error", JOptionPane.ERROR_MESSAGE);
      }
    } catch (SpreadsheetReadException ex) {
      Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
    }
    return null;
  }

  @Override
  protected void done() {
    try {
      eventList.addAll(get());
      window.setVisible(false);
      window.dispose();
    } catch (InterruptedException | ExecutionException ex) {
      Logger.getLogger(BillWorker.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  private List<Bill> status(List<Bill> bills) {
    BillService service = new BillServiceImpl();
    List<Bill> list = new ArrayList<>();
    for (int i = 0; i < bills.size(); i++) {
      Bill bill = (Bill) bills.get(i);
      bill.setBillResponse(
          service.getStatus(bill.getRuc(), bill.getTipo(), bill.getSerie(), bill.getCorrelativo()));
      bill.setCdrResponse(
          service.getStatusCdr(
              bill.getRuc(), bill.getTipo(), bill.getSerie(), bill.getCorrelativo()));
      list.add(bill);
    }
    return list;
  }
}
