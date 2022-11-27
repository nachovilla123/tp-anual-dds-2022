package model.mediodetransporte;

import model.implementacionCSV.FactorDeEmision;
import model.implementacionCSV.tipoDeConsumo.TipoDeConsumo;
import model.implementacionGeoDds.Distancia;
import model.implementacionGeoDds.ServicioGeoDds;

import lombok.Getter;
import lombok.Setter;
import model.organizacion.trayecto.Ubicacion;
import model.Persistencia.PersistentEntity;

import javax.persistence.*;
import java.io.IOException;

@Getter
@Setter

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class MedioDeTransporte extends PersistentEntity {


  @ManyToOne(cascade = CascadeType.MERGE)
  public FactorDeEmision factorDeEmision;

  public abstract Boolean esCompartible();

  public abstract Double cantidadDeCombustibleConsumidoPorKM(); // entrega 3, dice q depende  del transporte

  public Distancia distancia(Ubicacion origen, Ubicacion destino) {
    Distancia distDevuelta = null;
    try {
      distDevuelta = ServicioGeoDds.instancia().distanciaEntreLasUbicaciones(origen, destino);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return distDevuelta;
  }

  //Si yo hago que el fe este en kg unifico el valor final del calculo
  public double hcPorDistancia(Ubicacion origen, Ubicacion destino) {
      return this.calcularValorConsumo(this.distancia(origen,destino)) *getFactorDeEmision();
  }

  public double calcularValorConsumo(Distancia distancia) {
    return distancia.getValor() * this.cantidadDeCombustibleConsumidoPorKM();
  }
  public Double getFactorDeEmision() {
    return factorDeEmision.getValor();
  }
  public TipoDeConsumo getTipoDeConsumo(){
    return  factorDeEmision.getTipoDeConsumo();
  }




}

