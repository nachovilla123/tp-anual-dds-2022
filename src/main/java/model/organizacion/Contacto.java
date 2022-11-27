package model.organizacion;


import lombok.Getter;
import lombok.Setter;
import model.Persistencia.PersistentEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Set;

@Getter
@Setter

@Entity
@Table(name = "contacto")
public class Contacto extends PersistentEntity {

    @Column
    String emailDeContacto;
    @Column
    int NumeroDeContactoWPP;
    @Column
    String nombreDelContacto;

    public Contacto(String emailDeContacto,int numeroDeContactoWPP,String nombreDelContacto){
        this.emailDeContacto = emailDeContacto;
        this.NumeroDeContactoWPP = numeroDeContactoWPP;
        this.nombreDelContacto = nombreDelContacto;
    }
    public Contacto(){}

    public boolean yaExiste(Set<Contacto> contactos) {
        return this.elCorreoEstaRegistrado(contactos) && this.elWPPEstaRegistrado(contactos);
    }

    // <--------- esta bien hacer esto aca? no me cierra mucho lo que hice --------->
    public boolean elCorreoEstaRegistrado(Set<Contacto> contactos){
        return contactos.stream().anyMatch( contacto -> contacto.emailDeContacto == this.emailDeContacto) ;
    }                                         // TODO ver de abstraer  contacto.emailDeContacto == this.emailDeContacto en una funcion

    public boolean elWPPEstaRegistrado(Set<Contacto> contactos){
        return contactos.stream().anyMatch( contacto -> contacto.NumeroDeContactoWPP == this.NumeroDeContactoWPP) ;
    }
    // <--------- esta bien hacer esto aca? --------->


}


