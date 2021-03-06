package com.anthonyponte.jbillstatus;

import com.anthonyponte.jbillstatus.controller.UserController;
import com.anthonyponte.jbillstatus.view.UserFrame;
import com.github.weisj.darklaf.LafManager;
import com.github.weisj.darklaf.theme.DarculaTheme;
import javax.swing.SwingUtilities;

public class Main {

  public static void main(String[] args) {
    SwingUtilities.invokeLater(
        () -> {
          LafManager.install(new DarculaTheme());
          LafManager.setDecorationsEnabled(false);
          UserFrame userFrame = new UserFrame();
          new UserController(userFrame).start();
        });
  }
}
