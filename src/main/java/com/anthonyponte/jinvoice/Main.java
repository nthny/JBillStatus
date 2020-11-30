/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anthonyponte.jinvoice;

import com.anthonyponte.jinvoice.controller.UserController;
import com.anthonyponte.jinvoice.view.UserFrame;
import com.formdev.flatlaf.intellijthemes.FlatArcDarkIJTheme;
import java.awt.Color;
import java.awt.Insets;
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
          init();

          UserFrame userFrame = new UserFrame();
          new UserController(userFrame).start();
        });
  }

  private static void init() {
    // JFrame
    JFrame.setDefaultLookAndFeelDecorated(true);
    // Button
    UIManager.put("Button.arc", 999);
    // Component
    UIManager.put("Component.arc", 999);
    // ProgressBar
    UIManager.put("ProgressBar.arc", 999);
    // TextComponent
    UIManager.put("TextComponent.arc", 999);
    // ScrollBar
    UIManager.put("ScrollBar.trackInsets", new Insets(2, 4, 2, 4));
    UIManager.put("ScrollBar.thumbInsets", new Insets(2, 2, 2, 2));
    UIManager.put("ScrollBar.track", new Color(0xe0e0e0));
  }
}
