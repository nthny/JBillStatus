/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.anthonyponte.jinvoice.pojo;

/** @author nthny */
public class User {
  private String ruc;
  private String username;
  private String password;

  public User() {}

  public User(String ruc, String username, String password) {
    this.ruc = ruc;
    this.username = username;
    this.password = password;
  }

  public String getRuc() {
    return ruc;
  }

  public void setRuc(String ruc) {
    this.ruc = ruc;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public String toString() {
    return "User{" + "ruc=" + ruc + ", username=" + username + ", password=" + password + '}';
  }
}
