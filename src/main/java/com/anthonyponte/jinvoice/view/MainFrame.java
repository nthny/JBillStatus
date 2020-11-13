/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anthonyponte.jinvoice.view;

import com.github.weisj.darklaf.ui.table.DarkTableUI;
import com.github.weisj.darklaf.ui.text.DarkTextUI;
import epinsa.com.pe.utils.TableColumnAdjuster;

/**
 *
 * @author nthny
 */
public class MainFrame extends javax.swing.JFrame {

    /**
     * Creates new form MainFrame
     */
    public MainFrame() {
        initComponents();
        init();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scroll = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        filter = new com.github.weisj.darklaf.components.text.SearchTextField();
        menuBar = new javax.swing.JMenuBar();
        menu = new javax.swing.JMenu();
        menuImportar = new javax.swing.JMenuItem();
        menuExportar = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("JInvoice");
        setIconImage(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_bill_64.png")).getImage());
        setMinimumSize(new java.awt.Dimension(800, 600));

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        table.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        table.setShowGrid(true);
        table.getTableHeader().setReorderingAllowed(false);
        scroll.setViewportView(table);
        table.putClientProperty(DarkTableUI.KEY_ALTERNATE_ROW_COLOR, true);

        menu.setText("Menu");

        menuImportar.setText("Importar");
        menu.add(menuImportar);

        menuExportar.setText("Exportar");
        menu.add(menuExportar);

        menuBar.add(menu);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scroll, javax.swing.GroupLayout.DEFAULT_SIZE, 780, Short.MAX_VALUE)
                    .addComponent(filter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(filter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scroll, javax.swing.GroupLayout.DEFAULT_SIZE, 530, Short.MAX_VALUE)
                .addContainerGap())
        );

        filter.putClientProperty(DarkTextUI.KEY_DEFAULT_TEXT, "Filtrar comprobantes");

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void init() {
        columnAdjuster = new TableColumnAdjuster(table);
        columnAdjuster.setDynamicAdjustment(true);
        columnAdjuster.adjustColumns();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public com.github.weisj.darklaf.components.text.SearchTextField filter;
    private javax.swing.JMenu menu;
    private javax.swing.JMenuBar menuBar;
    public javax.swing.JMenuItem menuExportar;
    public javax.swing.JMenuItem menuImportar;
    public javax.swing.JScrollPane scroll;
    public javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables
    private TableColumnAdjuster columnAdjuster;

}
