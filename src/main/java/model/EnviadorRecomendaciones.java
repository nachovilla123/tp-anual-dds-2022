package model;

import it.sauronsoftware.cron4j.Scheduler;
import model.organizacion.recomendaciones.EnviadorDeRecomendaciones;
import model.organizacion.recomendaciones.observers.EstrategiaNotificacion;
import model.organizacion.recomendaciones.observers.NotificadorMail;
import model.organizacion.recomendaciones.senders.AdapterNotificadorMail;
import model.repositorios.repositoriosDBs.RepositorioDeOrganizaciones;

import java.util.ArrayList;
import java.util.List;

public class EnviadorRecomendaciones {

  public static void main(String[] args){
    //String cronTab = "0 7 * * *" ; //codigo para q corra 1 vez a las 7 am
    String cronTab = "*/1  * * * *" ;
    //  codigo para que corra cada un minuto
    // Creates a Scheduler instance.
    Scheduler s = new Scheduler();
    // Schedule a once-a-minute task.


    AdapterNotificadorMail adapterMail = new AdapterNotificadorMail(RepositorioDeOrganizaciones.getInstance());
    NotificadorMail notiMail = new NotificadorMail(adapterMail);
    List<EstrategiaNotificacion> estrategias = new ArrayList<>();
    estrategias.add(notiMail);
    EnviadorDeRecomendaciones enviadorDeRecomendaciones = new EnviadorDeRecomendaciones("localhost:9000/Recomendaciones",estrategias);

    s.schedule(cronTab, new Runnable() {
      // buscar TODO cron tab every day at 7 am
      // TODO pagina para generar un cron tab http://www.cronmaker.com/;jsessionid=node01rczr1dk3t1m219hir0nsunfn61304820.node0?0
      public void run() {
        System.out.println("Another minute ticked away..."); // esta linea la sacamos


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
