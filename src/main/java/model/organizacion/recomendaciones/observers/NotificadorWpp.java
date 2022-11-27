package model.organizacion.recomendaciones.observers;

import model.organizacion.recomendaciones.senders.AdapterNotificadorWPP;

public class NotificadorWpp implements EstrategiaNotificacion {
  AdapterNotificadorWPP adapter;

  public void NotificadorWpp (AdapterNotificadorWPP adaptador) {
    this.adapter = adaptador;
  }
  public void setAdapter(AdapterNotificadorWPP adapter) {
    this.adapter = adapter;
  }

  @Override
  public void enviarGuia(String guiaRecomendacion) {
    this.adapter.enviarWPP(guiaRecomendacion);
  }

}

