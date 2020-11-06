/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anthonyponte.jinvoice;

import com.github.weisj.darklaf.LafManager;

/** @author nthny */
public class Main {

  /** @param args the command line arguments */
  public static void main(String[] args) {
    LafManager.installTheme(LafManager.getPreferredThemeStyle());
    MainFrame mainFrame = new MainFrame();
    new MainController(mainFrame).start();
  }
}
