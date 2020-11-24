/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anthonyponte.jinvoice;

import com.anthonyponte.jinvoice.controller.UserController;
import com.anthonyponte.jinvoice.view.UserFrame;
import com.formdev.flatlaf.intellijthemes.FlatArcDarkIJTheme;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/** @author nthny */
public class Main {

  /** @param args the command line arguments */
  public static void main(String[] args) {
    SwingUtilities.invokeLater(
        () -> {
          FlatArcDarkIJTheme.install();
          customizeUI();

          UserFrame userFrame = new UserFrame();
          new UserController(userFrame).start();
        });
  }

  private static void customizeUI() {
    // Frame
    JFrame.setDefaultLookAndFeelDecorated(true);
    // Button
    UIManager.put("Button.arc", 999);
    // Component
    UIManager.put("Component.arc", 999);
    // ProgressBar
    UIManager.put("ProgressBar.arc", 999);
    // TextComponent
    UIManager.put("TextComponent.arc", 999);
    // Table
    UIManager.put("Table.showHorizontalLines", true);
    UIManager.put("Table.showVerticalLines", true);
  }
}
