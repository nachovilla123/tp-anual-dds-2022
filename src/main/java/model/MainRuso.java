package model;

import builders.OrganizacionFactory;
import builders.TramoFactory;
import model.organizacion.Organizacion;
import model.repositorios.repositoriosDBs.RepositorioDeOrganizaciones;

public class MainRuso {


  public static void main(String[] args) {
    TramoFactory tramoFactory = new TramoFactory();
    OrganizacionFactory organizacionFactory = new OrganizacionFactory();
/*
    System.out.println("Hola");
    Tramo tramo = tramoFactory.tramoConHCde20();


    EntityManager em = PerThreadEntityManagers.getEntityManager();
    EntityTransaction tx = em.getTransaction();
    System.out.println("Antes de buscar al usuario");
    Usuario usuarioEncontrado = RepositorioUsuario.getInstance().encontrar(1L);
    System.out.println(usuarioEncontrado.getPersona_asociada().getTramosNoRegistrados().size());

    usuarioEncontrado.getPersona_asociada().agregarTramoNoRegistrado(tramo); //agrego tramo desde el usuario
    System.out.println("Antes de mergear");
    RepositorioUsuario.getInstance().actualizar(usuarioEncontrado);
    System.out.println("Buscando despues del merge");
    System.out.println(RepositorioPersona.getInstance().encontrar(1L).getTramosNoRegistrados().size()); // busco la persona y accedo a los tramos a partir de ahi
  */
    //Organizacion organizacion = organizacionFactory.organizacionCALCON();
   // RepositorioDeOrganizaciones.getInstance().agregar(organizacion);

    Organizacion organizacionPrueba = RepositorioDeOrganizaciones.getInstance().encontrar(3L);
    System.out.println(organizacionPrueba.getRazonSocial());
    organizacionPrueba.setRazonSocial("Manzana");
    RepositorioDeOrganizaciones.getInstance().actualizar(organizacionPrueba);

  }

}