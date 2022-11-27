package builders;

import model.organizacion.Miembro;
import model.organizacion.Organizacion;
import model.organizacion.Persona;
import model.organizacion.trayecto.Trayecto;

public class MiembroFactory {

  //OTROS FACTORYS

  OrganizacionFactory organizacionFactory = new OrganizacionFactory();
  Organizacion APPLE = organizacionFactory.organizacionAppleEmpresa();
  //BUILDER TRAYECTO
  TrayectoFactory trayectoF = new TrayectoFactory();
  //BUILDER PERSONA
  PersonaFactory personaFactory = new PersonaFactory();
  //TramoFactory
  TramoFactory tramoFactory = new TramoFactory();


  private Miembro generarMiembro(Persona persona){
    return new Miembro(persona);
  }

  public Miembro miembroIgnacio() {

    return this.generarMiembro(null);
  }

  public  Miembro miembroMateo() {
    Persona mateo = personaFactory.personaMateo();
    return this.generarMiembro(mateo);}

  public  Miembro miembroNicoG() {return this.generarMiembro(personaFactory.personaNicoG());}

  public  Miembro miembroNicoO() {return this.generarMiembro(personaFactory.personaNicoO());}

  public Miembro miembroAlex() {return this.generarMiembro(personaFactory.personaAlex());}

  public  Miembro miembroPipe() {return this.generarMiembro(personaFactory.personaPipe());}

  //------------------------ MIEMBROS CON TRAYECTOS CARGADOS ------------------------//

  public Miembro miembroCon60dehc(){
    Trayecto trayectocon60hc_01 = trayectoF.untrayectoCon3tramos60hc();

    Miembro villa = this.miembroIgnacio();
    villa.registrarTrayecto(trayectocon60hc_01);
    return villa;
  }

  public Miembro miembroCon40dehc(){
    Trayecto trayectocon40hc = trayectoF.untrayectoCon2tramos40hc();

    Miembro pipe = miembroPipe();
    pipe.registrarTrayecto(trayectocon40hc);
    return pipe;
  }



}
