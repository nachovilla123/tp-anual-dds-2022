package model.organizacion.recomendaciones.observers;

import model.organizacion.recomendaciones.senders.AdapterNotificadorMail;

public class NotificadorMail implements EstrategiaNotificacion {

  AdapterNotificadorMail adapter;

  public NotificadorMail(AdapterNotificadorMail adapter) {
    this.adapter = adapter;
  }

  public void setAdapter(AdapterNotificadorMail adapter) {
    this.adapter = adapter;
  }
  @Override
  public void enviarGuia(String guiaRecomendacion) {
    this.adapter.enviarEmail(guiaRecomendacion);
  }



}
