/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.anthonyponte.jinvoice.controller;

import com.anthonyponte.jinvoice.view.DialogFrame;
import com.anthonyponte.jinvoice.view.MainFrame;
import com.github.weisj.darklaf.ui.text.DarkTextUI;
import java.awt.event.ActionEvent;
import java.util.prefs.Preferences;

/** @author nthny */
public class DialogController {

  private final DialogFrame frame;
  private final MainFrame mainFrame;
  private Preferences prefs;

  public DialogController(DialogFrame frame, MainFrame mainFrame) {
    this.frame = frame;
    this.mainFrame = mainFrame;
    init();
  }

  public void start() {
    prefs = Preferences.userNodeForPackage(this.getClass());

    frame.setLocationRelativeTo(mainFrame);
    frame.setVisible(true);
  }

  private void init() {
    frame.btnSave.addActionListener(
        (ActionEvent e) -> {
          frame.txtRuc.putClientProperty(
              DarkTextUI.KEY_HAS_ERROR, frame.txtRuc.getText().isEmpty());
          frame.txtUsername.putClientProperty(
              DarkTextUI.KEY_HAS_ERROR, frame.txtUsername.getText().isEmpty());
          frame.txtPassword.putClientProperty(
              DarkTextUI.KEY_HAS_ERROR, frame.txtPassword.getText().isEmpty());
        });
  }
}
