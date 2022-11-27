package model.organizacion.recomendaciones;

//TODO ARREGLAR ESTE IMPORT import com.sun.corba.se.impl.orbutil.RepositoryIdStrings;
import model.organizacion.recomendaciones.observers.EstrategiaNotificacion;

import java.util.List;

public class EnviadorDeRecomendaciones {

  String guiaRecomendacion;
  List<EstrategiaNotificacion> estrategias;



  public EnviadorDeRecomendaciones( String guiaDeRecomendacion, List<EstrategiaNotificacion> estrategias){
    this.guiaRecomendacion = guiaDeRecomendacion;
    this.estrategias = estrategias;
  }

  //TODO poder enviar cada cierto tiempo las recomendaciones.
  public void enviarRecomendacion() {
    estrategias.forEach(estrategia -> estrategia.enviarGuia(this.guiaRecomendacion));
    //organizaciones.forEach(organizacion -> organizacion.enviarGuiaRecomendacionesAContactos(guiaRecomendacion));
  }

  public void agregarEstrategia(EstrategiaNotificacion estrategia) {
    estrategias.add(estrategia);
  }

}

