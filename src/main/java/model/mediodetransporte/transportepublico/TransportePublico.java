package model.mediodetransporte.transportepublico;

import model.implementacionCSV.FactorDeEmision;
import model.implementacionCSV.tipoDeConsumo.TipoDeConsumo;
import model.implementacionGeoDds.Distancia;
import lombok.Getter;
import lombok.Setter;
import model.mediodetransporte.MedioDeTransporte;
import model.organizacion.trayecto.Ubicacion;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Getter
@Setter

@Entity
@Table(name = "transporte_publico")
public class TransportePublico extends MedioDeTransporte {

  @Column
  String linea;

  @Enumerated(EnumType.STRING)
  TipoTransporte tipoTransportePublico;

  @Column //todo REPASAR
  double cantidadDeCombustibleConsumidoPorKM = 2.0;

  //se va a modelar a la ida y a la vuelta por eso se hace solo una lista de paradas
  //@OneToMany(cascade = CascadeType.ALL)
  @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
  @OrderColumn
  List<Parada> paradas = new ArrayList<>();

  public TransportePublico() {

  }

  public enum TipoTransporte {
    COLECTIVO, FERROCARRIL, SUBTE
  }

  public TransportePublico(TipoTransporte tipoTransportePublico,
                           String linea,
                           List<Parada> paradas, FactorDeEmision factorDeEmision) {
    this.tipoTransportePublico = tipoTransportePublico;
    this.linea = linea;
    this.paradas = paradas;
    this.factorDeEmision= factorDeEmision;
  }


  public Boolean esCompartible() { return false; }

  @Override
  public Distancia distancia(Ubicacion origen, Ubicacion destino) {

    Parada paradaInicio = this.buscarParada(origen).get();
    Parada paradaFinal = this.buscarParada(destino).get();

    Integer acumuladorDistancia = 0;
    acumuladorDistancia = this.distanciaCalculada(paradaInicio, paradaFinal, acumuladorDistancia);

    return new Distancia(acumuladorDistancia.toString(), "km");
  }

  @Override
  public Double cantidadDeCombustibleConsumidoPorKM() {
    return cantidadDeCombustibleConsumidoPorKM;
  }


  Optional<Parada> buscarParada(Ubicacion ubicacion) {
    return paradas.stream().filter(parada -> parada.getUbicacion().esIgual(ubicacion)).findFirst();
  }

  Integer distanciaCalculada(Parada proxima, Parada destino, Integer acumulado){

    if (!(proxima == destino)) {
      acumulado += proxima.distProxima;
      return distanciaCalculada(proxima.getParadaSiguiente(), destino, acumulado);
    } else {
      return acumulado;
    }
  }
}



