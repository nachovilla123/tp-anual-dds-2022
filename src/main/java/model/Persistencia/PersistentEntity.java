package model.Persistencia;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;
@Getter
@MappedSuperclass
public abstract class PersistentEntity {


  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public long id;

}
