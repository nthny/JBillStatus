package com.anthonyponte.jbillstatus.controller;

import com.anthonyponte.jbillstatus.pojo.User;
import com.anthonyponte.jbillstatus.view.BillFrame;
import com.anthonyponte.jbillstatus.view.UserFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import javax.swing.SwingUtilities;

public class UserController {

  private final UserFrame frame;
  private final Preferences prefs;
  private final String TAG_RUC = "RUC";
  private final String TAG_USERNAME = "USERNAME";

  public UserController(UserFrame frame) {
    this.frame = frame;
    this.prefs = Preferences.userRoot().node(this.getClass().getName());
    this.prefs.get(TAG_RUC, "");
    this.prefs.get(TAG_USERNAME, "");
    init();
  }

  public void start() {
    frame.setVisible(true);
    frame.tfRuc.addKeyListener(kl);
    frame.tfUsername.addKeyListener(kl);
    frame.tfPassword.addKeyListener(kl);
    frame.btnLogin.addActionListener(al);
  }

  private void init() {
    String ruc = prefs.get(TAG_RUC, "");
    String username = prefs.get(TAG_USERNAME, "");
    if (!ruc.isEmpty() && !username.isEmpty()) {
      frame.tfRuc.setText(ruc);
      frame.tfUsername.setText(username);
      frame.tfPassword.requestFocus();
      frame.cbRemember.setSelected(true);
    } else {
      frame.cbRemember.setSelected(false);
    }
  }

  private final KeyListener kl =
      new KeyAdapter() {
        @Override
        public void keyReleased(KeyEvent e) {
          if (frame.tfRuc.getText().isEmpty()
              || frame.tfUsername.getText().isEmpty()
              || frame.tfPassword.getPassword().length == 0) {
            frame.btnLogin.setEnabled(false);
          } else {
            frame.btnLogin.setEnabled(true);
          }
        }
      };

  private final ActionListener al =
      new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          try {
            User user = User.getInstance();
            user.setRuc(frame.tfRuc.getText());
            user.setUsername(frame.tfUsername.getText());
            user.setPassword(String.valueOf(frame.tfPassword.getPassword()));

            if (frame.cbRemember.isSelected()) {
              prefs.put(TAG_RUC, frame.tfRuc.getText());
              prefs.put(TAG_USERNAME, frame.tfUsername.getText());
            } else {
              prefs.clear();
            }

            frame.dispose();

            SwingUtilities.invokeLater(
                () -> {
                  BillFrame billFrame = new BillFrame();
                  new BillController(billFrame).start();
                });
          } catch (BackingStoreException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
          }
        }
      };
}
