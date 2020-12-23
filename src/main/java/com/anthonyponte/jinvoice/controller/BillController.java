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
import ca.odell.glazedlists.TextFilterator;
import ca.odell.glazedlists.gui.TableFormat;
import ca.odell.glazedlists.matchers.MatcherEditor;
import ca.odell.glazedlists.swing.AdvancedListSelectionModel;
import ca.odell.glazedlists.swing.AdvancedTableModel;
import ca.odell.glazedlists.swing.DefaultEventSelectionModel;
import static ca.odell.glazedlists.swing.GlazedListsSwing.eventTableModelWithThreadProxyList;
import ca.odell.glazedlists.swing.TableComparatorChooser;
import ca.odell.glazedlists.swing.TextComponentMatcherEditor;
import com.anthonyponte.jinvoice.pojo.Bill;
import com.anthonyponte.jinvoice.impl.BillServiceImpl;
import com.anthonyponte.jinvoice.view.LoadingDialog;
import com.anthonyponte.jinvoice.view.UserFrame;
import com.poiji.bind.Poiji;
import java.awt.Color;
import java.awt.Component;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingWorker;
import javax.swing.table.TableCellRenderer;
import org.jdesktop.swingx.renderer.DefaultTableRenderer;
import pe.gob.sunat.BillService;
import pe.gob.sunat.StatusResponse;

/** @author nthny */
public class BillController {

  private final BillFrame billFrame;
  private final LoadingDialog loadingDialog;
  private EventList<Bill> eventList;
  private AdvancedListSelectionModel<Bill> selectionModel;
  private AdvancedTableModel<Bill> model;
  private BillService service;

  public BillController(BillFrame billFrame) {
    this.billFrame = billFrame;
    this.loadingDialog = new LoadingDialog(billFrame, false);
    this.service = new BillServiceImpl();
    init();
  }

  public void start() {
    billFrame.setVisible(true);
    billFrame.scroll.setDropTarget(dt);
    billFrame.table.addMouseListener(ml);
  }

  private void init() {
    eventList = new BasicEventList<>();

    Comparator comparator =
        (Comparator<Bill>) (Bill o1, Bill o2) -> o1.getNumber() - o2.getNumber();

    SortedList<Bill> sortedList = new SortedList<>(eventList, comparator);

    TextFilterator<Bill> textFilterator =
        (List<String> baseList, Bill element) -> {
          baseList.add(element.getBillResponse().getStatusCode());
          baseList.add(element.getBillResponse().getStatusMessage());
        };

    MatcherEditor<Bill> matcherEditor =
        new TextComponentMatcherEditor<>(this.billFrame.tfFilter, textFilterator);

    FilterList<Bill> filterList = new FilterList<>(sortedList, matcherEditor);

    TableFormat<Bill> tableFormat =
        new TableFormat<Bill>() {
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
                return "Numero";
              case 4:
                return "Estado";
              case 5:
                return "Estado del cdr";
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
                return baseObject.getType();
              case 2:
                return baseObject.getSerie();
              case 3:
                return baseObject.getNumber();
              case 4:
                return baseObject.getBillResponse().getStatusMessage();
              case 5:
                return baseObject.getCdrResponse().getStatusMessage();
              default:
                break;
            }
            throw new IllegalStateException("Unexpected column: " + column);
          }
        };

    model = eventTableModelWithThreadProxyList(filterList, tableFormat);

    selectionModel = new DefaultEventSelectionModel<>(eventList);

    billFrame.table.setModel(model);

    billFrame.table.setSelectionModel(selectionModel);

    TableComparatorChooser.install(
        billFrame.table, sortedList, TableComparatorChooser.MULTIPLE_COLUMN_MOUSE_WITH_UNDO);
  }

  private final DropTarget dt =
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

                    SwingWorker worker =
                        new SwingWorker<List<Bill>, Integer>() {
                          @Override
                          protected List<Bill> doInBackground() throws Exception {
                            loadingDialog.setVisible(true);

                            List<Bill> list = Poiji.fromExcel(file, Bill.class);

                            loadingDialog.progressBar.setMinimum(0);
                            loadingDialog.progressBar.setMaximum(list.size());

                            for (int i = 0; i < list.size(); i++) {
                              Bill bill = (Bill) list.get(i);

                              StatusResponse billResponse =
                                  service.getStatus(
                                      bill.getRuc(),
                                      bill.getType(),
                                      bill.getSerie(),
                                      bill.getNumber());

                              StatusResponse cdrResponse =
                                  service.getStatusCdr(
                                      bill.getRuc(),
                                      bill.getType(),
                                      bill.getSerie(),
                                      bill.getNumber());

                              list.get(i).setBillResponse(billResponse);
                              list.get(i).setCdrResponse(cdrResponse);

                              publish(i);
                            }

                            return list;
                          }

                          @Override
                          protected void process(List<Integer> chunks) {
                            loadingDialog.progressBar.setValue(chunks.get(0));
                          }

                          @Override
                          protected void done() {
                            try {
                              List<Bill> bills = get();

                              eventList.clear();
                              eventList.addAll(bills);

                              TableCellRenderer renderer =
                                  new DefaultTableRenderer() {
                                    @Override
                                    public Component getTableCellRendererComponent(
                                        JTable table,
                                        Object value,
                                        boolean isSelected,
                                        boolean hasFocus,
                                        int row,
                                        int column) {

                                      JLabel label =
                                          (JLabel)
                                              super.getTableCellRendererComponent(
                                                  table, value, isSelected, hasFocus, row, column);

                                      Bill bill = model.getElementAt(row);

                                      switch (bill.getBillResponse().getStatusCode()) {
                                        case "0001":
                                          label.setForeground(Color.decode("#AED581"));
                                          break;
                                        case "0002":
                                          label.setForeground(Color.decode("#FFF176"));
                                          break;
                                        case "0003":
                                          label.setForeground(Color.decode("#E57373"));
                                          break;
                                        case "0004":
                                        case "0005":
                                        case "0006":
                                        case "0007":
                                        case "0008":
                                        case "0009":
                                        case "0010":
                                        case "0011":
                                        case "0012":
                                          label.setForeground(Color.decode("#BBBBBB"));
                                          break;
                                      }

                                      return label;
                                    }
                                  };

                              billFrame
                                  .table
                                  .getColumnModel()
                                  .getColumn(4)
                                  .setCellRenderer(renderer);

                              billFrame
                                  .table
                                  .getColumnModel()
                                  .getColumn(5)
                                  .setCellRenderer(renderer);

                              loadingDialog.dispose();
                            } catch (InterruptedException | ExecutionException ex) {
                              loadingDialog.dispose();

                              int input =
                                  JOptionPane.showOptionDialog(
                                      billFrame,
                                      ex.getMessage(),
                                      "Error",
                                      JOptionPane.DEFAULT_OPTION,
                                      JOptionPane.ERROR_MESSAGE,
                                      null,
                                      null,
                                      null);

                              if (input == JOptionPane.OK_OPTION) {
                                billFrame.dispose();
                                UserFrame userFrame = new UserFrame();
                                new UserController(userFrame).start();
                              }
                            }
                          }
                        };

                    worker.execute();
                  }
                }
              }
            } catch (UnsupportedFlavorException | IOException ex) {

              JOptionPane.showMessageDialog(
                  billFrame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
          } else {
            dtde.rejectDrop();
          }
        }
      };

  private final MouseListener ml =
      new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
          if (e.getClickCount() == 2) {
            JTable target = (JTable) e.getSource();
            int row = target.getSelectedRow();
            int column = target.getSelectedColumn();
            Bill bill = model.getElementAt(row);

            if (column == 5 && bill.getBillResponse().getStatusCode().equals("0001")
                || bill.getBillResponse().getStatusCode().equals("0002")
                || bill.getBillResponse().getStatusCode().equals("0003")) {

              JFileChooser chooser = new JFileChooser();
              chooser.setCurrentDirectory(new File("."));
              chooser.setSelectedFile(
                  new File(
                      "R-"
                          + bill.getRuc()
                          + "-"
                          + bill.getType()
                          + "-"
                          + bill.getSerie()
                          + "-"
                          + bill.getNumber()
                          + ".zip"));

              int result = chooser.showSaveDialog(billFrame);
              if (result == JFileChooser.APPROVE_OPTION) {

                File file = chooser.getSelectedFile().getAbsoluteFile();
                try (FileOutputStream fout =
                    new FileOutputStream(file.getParent() + "//" + file.getName())) {
                  fout.write(bill.getCdrResponse().getContent());
                  fout.flush();
                  fout.close();
                } catch (FileNotFoundException ex) {
                  Logger.getLogger(BillController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                  Logger.getLogger(BillController.class.getName()).log(Level.SEVERE, null, ex);
                }
              }
            }
          }
        }
      };
}
