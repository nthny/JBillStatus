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
import com.github.weisj.darklaf.components.text.SearchEvent;
import com.github.weisj.darklaf.components.text.SearchListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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
    }

    private void init() {
        eventList = GlazedLists.threadSafeList(new BasicEventList<>());

        List<Bill> bills = new ArrayList<>();
        for (int i = 1; i < 100; i++) {
            Bill bill = new Bill();
            bill.setId(i);
            bill.setRuc("10455297252");
            bill.setTipo("01");
            bill.setSerie("E001");
            bill.setCorrelativo(i);
			bills.add(bill);
        }
		
		eventList.addAll(bills);

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
                mainFrame.table,
                sortedList,
                TableComparatorChooser.MULTIPLE_COLUMN_MOUSE_WITH_UNDO);
    }
}
