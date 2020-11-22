/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.anthonyponte.jinvoice.controller;

import com.anthonyponte.jinvoice.pojo.User;
import com.anthonyponte.jinvoice.view.UserDialog;
import com.anthonyponte.jinvoice.view.MainFrame;
import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

/** @author nthny */
public class UserController {

  private final UserDialog userDialog;
  private final MainFrame mainFrame;

  public UserController(UserDialog userDialog, MainFrame mainFrame) {
    this.userDialog = userDialog;
    this.mainFrame = mainFrame;
    init();
  }

  public void start() {
    userDialog.setVisible(true);
  }

  private void init() {
    userDialog.btnEnter.addActionListener((ActionEvent e) -> {
          try {

            User user = User.getInstance();
            user.setRuc(userDialog.txtRuc.getText());
            user.setUsername(userDialog.txtUsername.getText());
            user.setPassword(userDialog.txtPassword.getText());

            userDialog.dispose();

            //          frame.txtRuc.putClientProperty(
            //              DarkTextUI.KEY_HAS_ERROR, frame.txtRuc.getText().isEmpty());
            //          frame.txtUsername.putClientProperty(
            //              DarkTextUI.KEY_HAS_ERROR, frame.txtUsername.getText().isEmpty());
            //          frame.txtPassword.putClientProperty(
            //              DarkTextUI.KEY_HAS_ERROR, frame.txtPassword.getText().isEmpty());
          } catch (Exception ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
          }
        });
  }
}
