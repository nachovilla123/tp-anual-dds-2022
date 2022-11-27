package model.tareaCalendarizada;

import it.sauronsoftware.cron4j.Scheduler;
import lombok.Getter;
import lombok.Setter;
import model.organizacion.recomendaciones.EnviadorDeRecomendaciones;
import model.organizacion.recomendaciones.observers.EstrategiaNotificacion;
import model.organizacion.recomendaciones.observers.NotificadorMail;
import model.organizacion.recomendaciones.senders.AdapterNotificadorMail;
import model.repositorios.repositoriosDBs.RepositorioDeOrganizaciones;

public class TareaCalendarizada {

  //Agregado para poder enviar las notificaciones
  EnviadorDeRecomendaciones enviadorDeRecomendaciones;

  @Setter
  @Getter
  String cronTab = "0 7 * * *"  ; // seteo esta porque si TODO cron tab every day at 7 am
  //https://www.sauronsoftware.it/projects/cron4j/manual.php

  public void correrTareaCalendarizada(){
    // Creates a Scheduler instance.
    Scheduler s = new Scheduler();
    // Schedule a once-a-minute task.
    AdapterNotificadorMail adapterMail = new AdapterNotificadorMail(RepositorioDeOrganizaciones.getInstance());
    NotificadorMail notiMail = new NotificadorMail(adapterMail);
    enviadorDeRecomendaciones.agregarEstrategia(notiMail);

    s.schedule(cronTab, new Runnable() { // buscar TODO cron tab every day at 7 am
      // TODO pagina para generar un cron tab http://www.cronmaker.com/;jsessionid=node01rczr1dk3t1m219hir0nsunfn61304820.node0?0
      public void run() {
        System.out.println("Enviando notificaciones");
        enviadorDeRecomendaciones.enviarRecomendacion();
      }

    });
    // Starts the scheduler.
    s.start();
    // Will run for ten minutes.
    try {
      Thread.sleep(1000L * 60L * 10L);
    } catch (InterruptedException e) {
      ;
    }
    // Stops the scheduler.
    s.stop();
  }

}