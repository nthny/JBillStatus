/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anthonyponte.jinvoice.controller;

import com.anthonyponte.jinvoice.view.BillFrame;
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
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/** @author nthny */
public class BillController {

  private final BillFrame mainFrame;
  private EventList<Bill> eventList;
  private AdvancedListSelectionModel<Bill> selectionModel;
  private AdvancedTableModel<Bill> model;

  public BillController(BillFrame mainFrame) {
    this.mainFrame = mainFrame;
    init();
  }

  public void start() {
    mainFrame.setVisible(true);

		mainFrame.scroll.setDropTarget(new DropTarget() {
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
                Logger.getLogger(BillController.class.getName()).log(Level.SEVERE, null, ex);
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
        new TextComponentMatcherEditor<>(this.mainFrame.tfFilter, new BillTextFilterator());

    FilterList<Bill> filterList = new FilterList<>(sortedList, matcherEditor);

    model = eventTableModelWithThreadProxyList(filterList, new BillTableFormat());

    selectionModel = new DefaultEventSelectionModel<>(eventList);

    mainFrame.table.setModel(model);

    mainFrame.table.setSelectionModel(selectionModel);

    TableComparatorChooser.install(
        mainFrame.table, sortedList, TableComparatorChooser.MULTIPLE_COLUMN_MOUSE_WITH_UNDO);
  }
}
