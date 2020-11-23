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
public class BillFrame extends javax.swing.JFrame {

    /**
     * Creates new form MainFrame
     */
    public BillFrame() {
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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("JInvoice");
        setIconImage(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_invoice_64.png")).getImage());
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
                .addComponent(scroll, javax.swing.GroupLayout.DEFAULT_SIZE, 555, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void init() {
        filter.putClientProperty(DarkTextUI.KEY_DEFAULT_TEXT, "Filtrar comprobantes");

        table.putClientProperty(DarkTableUI.KEY_ALTERNATE_ROW_COLOR, true);

        columnAdjuster = new TableColumnAdjuster(table);
        columnAdjuster.setDynamicAdjustment(true);
        columnAdjuster.adjustColumns();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public com.github.weisj.darklaf.components.text.SearchTextField filter;
    public javax.swing.JScrollPane scroll;
    public javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables
    private TableColumnAdjuster columnAdjuster;
}