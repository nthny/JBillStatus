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
import com.anthonyponte.jinvoice.utils.BillServiceImpl;
import com.anthonyponte.jinvoice.view.LoadingDialog;
import com.poiji.bind.Poiji;
import java.awt.Color;
import java.awt.Component;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
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
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import pe.gob.sunat.BillService;
import pe.gob.sunat.StatusResponse;

/** @author nthny */
public class BillController implements MouseListener {

  private final BillFrame mainFrame;
  private final LoadingDialog loadingDialog;
  private EventList<Bill> eventList;
  private AdvancedListSelectionModel<Bill> selectionModel;
  private AdvancedTableModel<Bill> model;

  public BillController(BillFrame mainFrame) {
    this.mainFrame = mainFrame;
    this.loadingDialog = new LoadingDialog(mainFrame, false);
    init();
  }

  public void start() {
    mainFrame.setVisible(true);
    mainFrame.table.addMouseListener(this);
  }

  private void init() {
    eventList = new BasicEventList<>();

    Comparator comparator =
        (Comparator<Bill>) (Bill o1, Bill o2) -> o1.getCorrelativo() - o2.getCorrelativo();
    SortedList<Bill> sortedList = new SortedList<>(eventList, comparator);

    TextFilterator<Bill> textFilterator =
        (List<String> baseList, Bill element) -> {
          baseList.add(element.getBillResponse().getStatusCode());
          baseList.add(element.getBillResponse().getStatusMessage());
        };
    MatcherEditor<Bill> matcherEditor =
        new TextComponentMatcherEditor<>(this.mainFrame.tfFilter, textFilterator);

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
        };
    model = eventTableModelWithThreadProxyList(filterList, tableFormat);

    selectionModel = new DefaultEventSelectionModel<>(eventList);

    mainFrame.table.setModel(model);

    mainFrame.table.setSelectionModel(selectionModel);

    TableComparatorChooser.install(
        mainFrame.table, sortedList, TableComparatorChooser.MULTIPLE_COLUMN_MOUSE_WITH_UNDO);

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
                      if (file.getName().endsWith(".xls") || file.getName().endsWith(".xlsx")) {

                        SwingWorker worker =
                            new SwingWorker<List<Bill>, Void>() {
                              @Override
                              protected List<Bill> doInBackground() throws Exception {
                                loadingDialog.setVisible(true);

                                List<Bill> list = Poiji.fromExcel(file, Bill.class);
                                BillService service = new BillServiceImpl();

                                for (int i = 0; i < list.size(); i++) {
                                  Bill bill = (Bill) list.get(i);

                                  StatusResponse billResponse =
                                      service.getStatus(
                                          bill.getRuc(),
                                          bill.getTipo(),
                                          bill.getSerie(),
                                          bill.getCorrelativo());

                                  StatusResponse cdrResponse =
                                      service.getStatusCdr(
                                          bill.getRuc(),
                                          bill.getTipo(),
                                          bill.getSerie(),
                                          bill.getCorrelativo());

                                  list.get(i).setBillResponse(billResponse);
                                  list.get(i).setCdrResponse(cdrResponse);
                                }

                                return list;
                              }

                              @Override
                              protected void done() {
                                try {
                                  eventList.clear();
                                  eventList.addAll(get());

                                  TableCellRenderer renderer =
                                      new DefaultTableCellRenderer() {
                                        @Override
                                        public Component getTableCellRendererComponent(
                                            JTable table,
                                            Object value,
                                            boolean isSelected,
                                            boolean hasFocus,
                                            int row,
                                            int column) {

                                          Component component =
                                              super.getTableCellRendererComponent(
                                                  table, value, isSelected, hasFocus, row, column);

                                          Bill bill = model.getElementAt(row);

                                          switch (bill.getBillResponse().getStatusCode()) {
                                            case "0001":
                                              component.setForeground(Color.WHITE);
                                              // Objects.GreenAndroid
                                              component.setBackground(Color.decode("#A4C639"));
                                              break;
                                            case "0002":
                                              component.setForeground(Color.WHITE);
                                              // Objects.Yellow
                                              component.setBackground(Color.decode("#F4AF3D"));
                                              break;
                                            case "0003":
                                              component.setForeground(Color.WHITE);
                                              // Objects.Grey
                                              component.setBackground(Color.decode("#9AA7B0"));
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
                                              component.setForeground(Color.WHITE);
                                              // Objects.RedStatus
                                              component.setBackground(Color.decode("#E05555"));
                                              break;
                                          }

                                          return component;
                                        }
                                      };

                                  mainFrame
                                      .table
                                      .getColumnModel()
                                      .getColumn(4)
                                      .setCellRenderer(renderer);

                                  mainFrame
                                      .table
                                      .getColumnModel()
                                      .getColumn(5)
                                      .setCellRenderer(renderer);

                                  loadingDialog.dispose();
                                } catch (InterruptedException | ExecutionException ex) {
                                  Logger.getLogger(BillController.class.getName())
                                      .log(Level.SEVERE, null, ex);
                                }
                              }
                            };

                        worker.execute();

                      } else {
                        JOptionPane.showMessageDialog(
                            mainFrame,
                            "El archivo debe ser .xls o .xlsx",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                      }
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
                    + bill.getTipo()
                    + "-"
                    + bill.getSerie()
                    + "-"
                    + bill.getCorrelativo()
                    + ".zip"));

        int result = chooser.showSaveDialog(mainFrame);
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

  @Override
  public void mousePressed(MouseEvent e) {}

  @Override
  public void mouseReleased(MouseEvent e) {}

  @Override
  public void mouseEntered(MouseEvent e) {}

  @Override
  public void mouseExited(MouseEvent e) {}
}
