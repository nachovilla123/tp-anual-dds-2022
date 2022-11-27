package builders;


import model.mediodetransporte.MedioDeTransporte;
import model.organizacion.Persona;
import model.organizacion.trayecto.Tramo;
import model.organizacion.trayecto.Ubicacion;

public class PersonaFactory {
  private Persona Persona(String nombre, String apellido, Integer numeroDocumento, Persona.TipoDocumento tipoDocumento){
    return new Persona(nombre, apellido, numeroDocumento, tipoDocumento);
  }

  public Persona personaIgnacio() {
/*
    Persona ignacio =
    Tramo tramoDePrueba = new Tramo( "hacia falcultad de derecho",  medioDeTransporte, Ubicacion origen, Ubicacion destino))
    ignacio.agregarTramoNoRegistrado(
       */
    return this.Persona("Ignacio", "Villarruel", 43500500, Persona.TipoDocumento.DNI);


  }

  public  Persona personaMateo() {return this.Persona("Mateo","Silva",43400400, Persona.TipoDocumento.DNI);}

  public  Persona personaNicoG() {return this.Persona("Nico","Galfione",43400400, Persona.TipoDocumento.DNI);}

  public  Persona personaNicoO() {return this.Persona("Nicolas","Occhi",43400400, Persona.TipoDocumento.DNI);}

  public Persona personaAlex() {return this.Persona("Alex","Kalinin",43400400, Persona.TipoDocumento.DNI);}

  public  Persona personaPipe() {return this.Persona("Pipe","Nani",43400400, Persona.TipoDocumento.DNI);}

}
