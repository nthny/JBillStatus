/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.anthonyponte.jbillstatus.pojo;

/** @author nthny */
public class User {

  private static User instance;
  private String ruc;
  private String username;
  private String password;

  private User() {}

  public static User getInstance() {
    if (instance == null) {
      instance = new User();
    }
    return instance;
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
