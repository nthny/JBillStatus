/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anthonyponte.jinvoice;

import com.anthonyponte.jinvoice.controller.UserController;
import com.anthonyponte.jinvoice.view.UserFrame;
import com.formdev.flatlaf.intellijthemes.FlatArcDarkIJTheme;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/** @author nthny */
public class Main {

  /** @param args the command line arguments */
  public static void main(String[] args) {
    SwingUtilities.invokeLater(
        () -> {
          FlatArcDarkIJTheme.install();
          UIManager.put("Table.showHorizontalLines", true);
          UIManager.put("Table.showVerticalLines", true);
          UIManager.put("Table.rowHeight", 25);

          UserFrame userFrame = new UserFrame();
          new UserController(userFrame).start();
        });
  }
}
