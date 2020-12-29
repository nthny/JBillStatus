/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anthonyponte.jbillstatus;

import com.anthonyponte.jbillstatus.controller.UserController;
import com.anthonyponte.jbillstatus.view.UserFrame;
import com.github.weisj.darklaf.LafManager;
import com.github.weisj.darklaf.theme.DarculaTheme;
import javax.swing.SwingUtilities;

/** @author nthny */
public class Main {

  /** @param args the command line arguments */
  public static void main(String[] args) {
    SwingUtilities.invokeLater(
        () -> {
          LafManager.install(new DarculaTheme());
          UserFrame userFrame = new UserFrame();
          new UserController(userFrame).start();
        });
  }
}