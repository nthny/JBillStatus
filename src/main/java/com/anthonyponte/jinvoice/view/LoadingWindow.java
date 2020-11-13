/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.anthonyponte.jinvoice.view;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JWindow;
import javax.swing.SwingConstants;

/** @author nthny */
public class LoadingWindow extends JWindow {

  public LoadingWindow() {
    init();
  }

  private void init() {
    JLabel label = new JLabel();
    label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/loading.gif")));
    getContentPane().add(label, SwingConstants.CENTER);
    setBackground(new Color(0.5f, 0.5f, 0.5f, 0.5f));
    setSize(label.getIcon().getIconWidth(), label.getIcon().getIconHeight());
    setLocationRelativeTo(null);
  }
}
