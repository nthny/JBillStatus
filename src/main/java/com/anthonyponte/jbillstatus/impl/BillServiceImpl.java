package com.anthonyponte.jbillstatus.impl;

import com.anthonyponte.jbillstatus.pojo.User;
import jakarta.xml.ws.BindingProvider;
import jakarta.xml.ws.handler.Handler;
import java.util.ArrayList;
import java.util.List;
import pe.gob.sunat.BillConsultService;
import pe.gob.sunat.BillService;
import pe.gob.sunat.StatusResponse;

public class BillServiceImpl implements BillService {

  private final User user = User.getInstance();

  @Override
  public StatusResponse getStatus(
      String rucComprobante,
      String tipoComprobante,
      String serieComprobante,
      Integer numeroComprobante) {

    BillConsultService service = new BillConsultService();
    BillService port = service.getBillConsultServicePort();
    BindingProvider binding = (BindingProvider) port;

    @SuppressWarnings("rawtypes")
    List<Handler> handlers = new ArrayList<>();
    SOAPHanlderImpl handler =
        new SOAPHanlderImpl(user.getRuc() + user.getUsername(), user.getPassword());
    handlers.add(handler);
    binding.getBinding().setHandlerChain(handlers);

    return port.getStatus(rucComprobante, tipoComprobante, serieComprobante, numeroComprobante);
  }

  @Override
  public StatusResponse getStatusCdr(
      String rucComprobante,
      String tipoComprobante,
      String serieComprobante,
      Integer numeroComprobante) {

    BillConsultService service = new BillConsultService();
    BillService port = service.getBillConsultServicePort();
    BindingProvider binding = (BindingProvider) port;

    @SuppressWarnings("rawtypes")
    List<Handler> handlers = new ArrayList<>();
    SOAPHanlderImpl handler =
        new SOAPHanlderImpl(user.getRuc() + user.getUsername(), user.getPassword());
    handlers.add(handler);
    binding.getBinding().setHandlerChain(handlers);

    return port.getStatusCdr(rucComprobante, tipoComprobante, serieComprobante, numeroComprobante);
  }
}
