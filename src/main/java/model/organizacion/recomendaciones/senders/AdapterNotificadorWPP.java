package model.organizacion.recomendaciones.senders;

import model.organizacion.Contacto;
import model.repositorios.repositoriosDBs.RepositorioDeOrganizaciones;

import java.util.List;

public class AdapterNotificadorWPP implements WhatsAppSender {

  RepositorioDeOrganizaciones repoOrganizaciones;

  public AdapterNotificadorWPP(RepositorioDeOrganizaciones repo) {
    this.repoOrganizaciones = repo;
  }

  @Override
  public void enviarWPP(String guiaDeRecomendacion) {
    this.contactosEnElSistema().forEach(contacto -> this.efectivizarEnvio(contacto, guiaDeRecomendacion));
    //System.out.println("Enviando email a "+ notificacion.getEmail()+" por JavaMail: '"+notificacion.getMensaje()+"'");
  }

  private void efectivizarEnvio(Contacto contacto, String guia) {
    //TODO prox entregas
    System.out.println("EL contacto es: "+contacto.getNombreDelContacto() +" cuyo NUMERO es: " + contacto.getNumeroDeContactoWPP() + "y la guia enviada es:"+ guia);
  }

  public List<Contacto> contactosEnElSistema() {
    return repoOrganizaciones.obtenerTodosLosContactosDelSistema();
  }

}
