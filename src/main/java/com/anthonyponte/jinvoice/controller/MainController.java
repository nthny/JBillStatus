/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anthonyponte.jinvoice.controller;

import com.anthonyponte.jinvoice.view.MainFrame;
import ca.odell.glazedlists.BasicEventList;
import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.FilterList;
import ca.odell.glazedlists.SortedList;
import ca.odell.glazedlists.matchers.MatcherEditor;
import ca.odell.glazedlists.swing.AdvancedListSelectionModel;
import ca.odell.glazedlists.swing.AdvancedTableModel;
import ca.odell.glazedlists.swing.DefaultEventSelectionModel;
import static ca.odell.glazedlists.swing.GlazedListsSwing.eventTableModelWithThreadProxyList;
import ca.odell.glazedlists.swing.TableComparatorChooser;
import ca.odell.glazedlists.swing.TextComponentMatcherEditor;
import com.anthonyponte.jinvoice.pojo.Bill;
import com.anthonyponte.jinvoice.utils.BillComparator;
import com.anthonyponte.jinvoice.utils.BillTableFormat;
import com.anthonyponte.jinvoice.utils.BillTextFilterator;
import com.anthonyponte.jinvoice.utils.BillWorker;
import io.github.millij.poi.SpreadsheetReadException;
import io.github.millij.poi.ss.reader.XlsReader;
import io.github.millij.poi.ss.reader.XlsxReader;
import io.github.millij.poi.ss.writer.SpreadsheetWriter;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

/** @author nthny */
public class MainController {

  private final MainFrame mainFrame;
  private EventList<Bill> eventList;
  private AdvancedListSelectionModel<Bill> selectionModel;
  private AdvancedTableModel<Bill> model;

  public MainController(MainFrame mainFrame) {
    this.mainFrame = mainFrame;
    init();
  }

  public void start() {
    mainFrame.setVisible(true);

    mainFrame.menuImportar.addActionListener(
        (ActionEvent e) -> {
          FileFilter filter = new FileNameExtensionFilter("Excel file", "xls", "xlsx");
          JFileChooser chooser = new JFileChooser();
          chooser.setCurrentDirectory(new java.io.File("."));
          chooser.addChoosableFileFilter(filter);
          chooser.setFileFilter(filter);

          int result = chooser.showOpenDialog(mainFrame);
          if (result == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile().getAbsoluteFile();
            BillWorker worker = new BillWorker(mainFrame, file, eventList);
            worker.execute();
          }
        });

    mainFrame.menuExportar.addActionListener(
        (ActionEvent e) -> {
          try {
            SpreadsheetWriter writer =
                new SpreadsheetWriter(
                    "C:\\"
                        + DateFormat.getDateInstance(DateFormat.FULL).format(new Date())
                        + ".xlsx");
            writer.addSheet(Bill.class, eventList);
            writer.write();
          } catch (FileNotFoundException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
          } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
          }
        });

    mainFrame.scroll.setDropTarget(
        new DropTarget() {
          @Override
          public synchronized void drop(DropTargetDropEvent dtde) {
            if (dtde.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
              try {
                dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
                Transferable t = dtde.getTransferable();
                List fileList = (List) t.getTransferData(DataFlavor.javaFileListFlavor);
                if (fileList != null && fileList.size() > 0) {
                  for (Object value : fileList) {
                    if (value instanceof File) {
                      File file = (File) value;
                      BillWorker worker = new BillWorker(mainFrame, file, eventList);
                      worker.execute();
                    }
                  }
                }
              } catch (UnsupportedFlavorException | IOException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
              }
            } else {
              dtde.rejectDrop();
            }
          }
        });
  }

  private void init() {
    eventList = new BasicEventList<>();

    SortedList<Bill> sortedList = new SortedList<>(eventList, new BillComparator());

    MatcherEditor<Bill> matcherEditor =
        new TextComponentMatcherEditor<>(this.mainFrame.filter, new BillTextFilterator());

    FilterList<Bill> filterList = new FilterList<>(sortedList, matcherEditor);

    model = eventTableModelWithThreadProxyList(filterList, new BillTableFormat());

    selectionModel = new DefaultEventSelectionModel<>(eventList);

    mainFrame.table.setModel(model);

    mainFrame.table.setSelectionModel(selectionModel);

    TableComparatorChooser.install(
        mainFrame.table, sortedList, TableComparatorChooser.MULTIPLE_COLUMN_MOUSE_WITH_UNDO);
  }
}