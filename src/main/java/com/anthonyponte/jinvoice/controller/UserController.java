/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.anthonyponte.jinvoice.controller;

import com.anthonyponte.jinvoice.pojo.User;
import com.anthonyponte.jinvoice.view.BillFrame;
import com.anthonyponte.jinvoice.view.UserFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;

/** @author nthny */
public class UserController implements KeyListener, ActionListener {

  private final UserFrame frame;

  public UserController(UserFrame frame) {
    this.frame = frame;
  }

  public void start() {
    frame.setVisible(true);
    frame.tfRuc.addKeyListener(this);
    frame.tfUsername.addKeyListener(this);
    frame.tfPassword.addKeyListener(this);
    frame.btnEnter.addActionListener(this);
  }

  @Override
  public void keyTyped(KeyEvent e) {}

  @Override
  public void keyPressed(KeyEvent e) {}

  @Override
  public void keyReleased(KeyEvent e) {
    if (frame.tfRuc.getText().isEmpty()
        || frame.tfUsername.getText().isEmpty()
        || frame.tfPassword.getPassword().length == 0) {
      frame.btnEnter.setEnabled(false);
    } else {
      frame.btnEnter.setEnabled(true);
    }
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    try {
      User user = User.getInstance();
      user.setRuc(frame.tfRuc.getText());
      user.setUsername(frame.tfUsername.getText());
      user.setPassword(String.valueOf(frame.tfPassword.getPassword()));

      frame.dispose();

      SwingUtilities.invokeLater(
          () -> {
            BillFrame mainFrame = new BillFrame();
            new BillController(mainFrame).start();
          });
    } catch (Exception ex) {
      Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
}
