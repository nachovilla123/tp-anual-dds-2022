package model.organizacion;


import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class Contacto {
    String emailDeContacto;
    int NumeroDeContactoWPP;
    String nombreDelContacto;

    public Contacto(String emailDeContacto,int numeroDeContactoWPP,String nombreDelContacto){
        this.emailDeContacto = emailDeContacto;
        this.NumeroDeContactoWPP = numeroDeContactoWPP;
        this.nombreDelContacto = nombreDelContacto;
    }

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


