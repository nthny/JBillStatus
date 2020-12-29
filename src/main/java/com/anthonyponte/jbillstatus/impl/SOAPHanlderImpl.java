/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anthonyponte.jbillstatus.impl;

import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

/** @author nthny */
public class SOAPHanlderImpl implements SOAPHandler<SOAPMessageContext> {

  private final String username;
  private final String password;

  public SOAPHanlderImpl(String username, String password) {
    this.username = username;
    this.password = password;
  }

  @Override
  public Set<QName> getHeaders() {
    return null;
  }

  @Override
  public boolean handleMessage(SOAPMessageContext c) {
    Boolean isRequest = (Boolean) c.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
    if (isRequest) {
      try {
        SOAPEnvelope envelope = c.getMessage().getSOAPPart().getEnvelope();

        SOAPHeader header = envelope.getHeader();
        if (header == null) {
          header = envelope.addHeader();
        }

        SOAPElement tagSecurity =
            header.addChildElement(
                "Security",
                "wsse",
                "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd");
        SOAPElement tagToken = tagSecurity.addChildElement("UsernameToken", "wsse");
        SOAPElement tagUsername = tagToken.addChildElement("Username", "wsse");
        SOAPElement tagPassword = tagToken.addChildElement("Password", "wsse");

        tagUsername.addTextNode(this.username);
        tagPassword.addTextNode(this.password);
      } catch (SOAPException ex) {
        Logger.getLogger(SOAPHanlderImpl.class.getName()).log(Level.SEVERE, null, ex);
      }
    }

    return true;
  }

  @Override
  public boolean handleFault(SOAPMessageContext c) {
    Boolean isRequest = (Boolean) c.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
    if (isRequest) {
      SOAPMessage m = c.getMessage();
      System.out.println(
          "com.anthonyponte.jinvoice.SOAPHanlderImpl.handleFault() " + m.getContentDescription());
    }
    return true;
  }

  @Override
  public void close(MessageContext mc) {}
}
