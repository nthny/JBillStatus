package com.anthonyponte.jbillstatus.impl;

import jakarta.xml.soap.SOAPElement;
import jakarta.xml.soap.SOAPEnvelope;
import jakarta.xml.soap.SOAPException;
import jakarta.xml.soap.SOAPHeader;
import jakarta.xml.soap.SOAPMessage;
import jakarta.xml.ws.handler.MessageContext;
import jakarta.xml.ws.handler.soap.SOAPHandler;
import jakarta.xml.ws.handler.soap.SOAPMessageContext;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.namespace.QName;

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
