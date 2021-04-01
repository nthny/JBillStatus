package com.anthonyponte.jbillstatus.view;

import com.github.weisj.darklaf.ui.table.DarkTableUI;
import com.github.weisj.darklaf.ui.text.DarkTextFieldUI;
import com.github.weisj.darklaf.ui.text.DarkTextUI;
import javax.swing.ImageIcon;
import javax.swing.table.TableColumnModel;

public class BillFrame extends javax.swing.JFrame {

    public BillFrame() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tfFilter = new com.github.weisj.darklaf.components.text.SearchTextField();
        scroll = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        menuFile = new javax.swing.JMenu();
        mitemImport = new javax.swing.JMenuItem();
        mitemExport = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("JBillStatus");
        setIconImage(new ImageIcon(getClass().getResource("/img/jbillstatus.png")).getImage());
        setMinimumSize(new java.awt.Dimension(800, 600));
        setPreferredSize(new java.awt.Dimension(800, 600));

        tfFilter.setToolTipText("Filtrar comprobante por estado");

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        table.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        table.getTableHeader().setReorderingAllowed(false);
        scroll.setViewportView(table);
        table.putClientProperty(DarkTableUI.KEY_ALTERNATE_ROW_COLOR, true);

        menuFile.setText("Archivo");

        mitemImport.setText("Importar");
        menuFile.add(mitemImport);

        mitemExport.setText("Exportar");
        menuFile.add(mitemExport);

        jMenuBar1.add(menuFile);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tfFilter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(scroll, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 788, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tfFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scroll, javax.swing.GroupLayout.DEFAULT_SIZE, 536, Short.MAX_VALUE)
                .addContainerGap())
        );

        tfFilter.putClientProperty(DarkTextUI.KEY_DEFAULT_TEXT, "Filtrar comprobante por estado");
        tfFilter.putClientProperty(DarkTextFieldUI.KEY_SHOW_CLEAR, true);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenu menuFile;
    public javax.swing.JMenuItem mitemExport;
    public javax.swing.JMenuItem mitemImport;
    public javax.swing.JScrollPane scroll;
    public javax.swing.JTable table;
    public com.github.weisj.darklaf.components.text.SearchTextField tfFilter;
    // End of variables declaration//GEN-END:variables
}
