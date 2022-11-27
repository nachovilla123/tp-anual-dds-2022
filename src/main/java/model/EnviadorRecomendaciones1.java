package model;


import model.organizacion.recomendaciones.EnviadorDeRecomendaciones;
import model.organizacion.recomendaciones.observers.EstrategiaNotificacion;
import model.organizacion.recomendaciones.observers.NotificadorMail;
import model.organizacion.recomendaciones.senders.AdapterNotificadorMail;
import model.repositorios.repositoriosDBs.RepositorioDeOrganizaciones;

import java.util.ArrayList;
import java.util.List;

public class EnviadorRecomendaciones1 {

  public static void main(String[] args) {//String cronTab = "0 7 * * *" ; //codigo para q corra 1 vez a las 7 am}

    AdapterNotificadorMail adapterMail = new AdapterNotificadorMail(RepositorioDeOrganizaciones.getInstance());
    NotificadorMail notiMail = new NotificadorMail(adapterMail);
    List<EstrategiaNotificacion> estrategias = new ArrayList<>();
    estrategias.add(notiMail);
    EnviadorDeRecomendaciones enviadorDeRecomendaciones = new EnviadorDeRecomendaciones("localhost:9000/Recomendaciones", estrategias);

    enviadorDeRecomendaciones.enviarRecomendacion();

  }
}
