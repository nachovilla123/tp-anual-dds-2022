package model.tareaCalendarizada;

import it.sauronsoftware.cron4j.Scheduler;
import lombok.Getter;
import lombok.Setter;
//TODO Se debe configurar una tarea calendarizada, que se encargue del envío de las notificaciones.

public class TimerTaskJobsForCronJobs {
  @Setter
  @Getter
  String cronTab = "0 7 * * *"  ; // seteo esta porque si TODO cron tab every day at 7 am



  //https://www.sauronsoftware.it/projects/cron4j/manual.php
  public static void main(String[] args){
    String cronTab = "0 7 * * *" ;

    // Creates a Scheduler instance.
    Scheduler s = new Scheduler();
    // Schedule a once-a-minute task.
    s.schedule(cronTab, new Runnable() { // buscar TODO cron tab every day at 7 am
      // TODO pagina para generar un cron tab http://www.cronmaker.com/;jsessionid=node01rczr1dk3t1m219hir0nsunfn61304820.node0?0
      public void run() {
        System.out.println("Another minute ticked away..."); // esta linea la sacamos

        // TODO meter el codigo que necesitemos para envio de notificaciones

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
