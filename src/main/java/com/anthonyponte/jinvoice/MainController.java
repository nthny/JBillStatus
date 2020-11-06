/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anthonyponte.jinvoice;

import ca.odell.glazedlists.BasicEventList;
import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.FilterList;
import ca.odell.glazedlists.GlazedLists;
import ca.odell.glazedlists.SortedList;
import ca.odell.glazedlists.TextFilterator;
import ca.odell.glazedlists.gui.TableFormat;
import ca.odell.glazedlists.matchers.MatcherEditor;
import ca.odell.glazedlists.swing.AdvancedListSelectionModel;
import ca.odell.glazedlists.swing.AdvancedTableModel;
import ca.odell.glazedlists.swing.DefaultEventSelectionModel;
import static ca.odell.glazedlists.swing.GlazedListsSwing.eventTableModelWithThreadProxyList;
import ca.odell.glazedlists.swing.TableComparatorChooser;
import ca.odell.glazedlists.swing.TextComponentMatcherEditor;
import com.poiji.bind.Poiji;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
                      File f = (File) value;
                      List<Bill> list = Poiji.fromExcel(f, Bill.class);
                      eventList.addAll(list);
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
    eventList = GlazedLists.threadSafeList(new BasicEventList<>());

    Comparator comparator =
        (Comparator<Bill>) (Bill o1, Bill o2) -> o1.getCorrelativo() - o2.getCorrelativo();

    SortedList<Bill> sortedList = new SortedList<>(eventList, comparator);

    TextFilterator<Bill> textFilterator =
        (List<String> list, Bill e) -> {
          list.add(e.getRuc());
          list.add(e.getTipo());
          list.add(e.getSerie());
          list.add(String.valueOf(e.getCorrelativo()));
        };

    MatcherEditor<Bill> matcherEditor =
        new TextComponentMatcherEditor<>(mainFrame.filter, textFilterator);

    FilterList<Bill> filterList = new FilterList<>(sortedList, matcherEditor);

    TableFormat<Bill> tableFormat =
        new TableFormat<Bill>() {
          @Override
          public int getColumnCount() {
            return 5;
          }

          @Override
          public String getColumnName(int i) {
            switch (i) {
              case 0:
                return "#";
              case 1:
                return "RUC";
              case 2:
                return "Tipo";
              case 3:
                return "Serie";
              case 4:
                return "Correlativo";
              default:
                break;
            }
            throw new IllegalStateException("Unexpected column: " + i);
          }

          @Override
          public Object getColumnValue(Bill e, int i) {
            switch (i) {
              case 0:
                return e.getId();
              case 1:
                return e.getRuc();
              case 2:
                return e.getTipo();
              case 3:
                return e.getSerie();
              case 4:
                return e.getCorrelativo();
              default:
                break;
            }
            throw new IllegalStateException("Unexpected column: " + i);
          }
        };

    model = eventTableModelWithThreadProxyList(filterList, tableFormat);

    selectionModel = new DefaultEventSelectionModel<>(eventList);

    mainFrame.table.setModel(model);

    mainFrame.table.setSelectionModel(selectionModel);

    TableComparatorChooser.install(
        mainFrame.table, sortedList, TableComparatorChooser.MULTIPLE_COLUMN_MOUSE_WITH_UNDO);
  }
}
