package model.mediodetransporte.transportepublico;


import model.Persistencia.PersistentEntity;
import lombok.Getter;
import lombok.Setter;
import model.organizacion.trayecto.Ubicacion;

import javax.persistence.*;

@Setter
@Getter

@Entity
@Table(name = "parada")
public class Parada extends PersistentEntity {

  @Column
  int distProxima;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "parada_anterior")
  Parada paradaAnterior;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "parada_siguiente")
  Parada paradaSiguiente;

  //@OneToOne(cascade = CascadeType.ALL)
  //@JoinColumn(name = "ubicacion")
  @Embedded()
  Ubicacion ubicacion;

  public Parada(int distProxima,
                Parada paradaAnterior,
                Parada paradaSiguiente,
                Ubicacion ubicacion) {
    this.distProxima = distProxima;
    this.paradaAnterior = paradaAnterior;
    this.paradaSiguiente = paradaSiguiente;
    this.ubicacion = ubicacion;
  }
  public Parada(){}

}



