package repositories;


import builders.*;
import org.junit.jupiter.api.Test;
import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;
import model.organizacion.Organizacion;
import model.repositorios.repositoriosDBs.RepositorioDeOrganizaciones;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Repositorios {

  OrganizacionFactory organizacionFactory = new OrganizacionFactory();

  @Test
  public void findAL_reposiotorio_organizaciones(){

    RepositorioDeOrganizaciones repoOrganizaciones = new RepositorioDeOrganizaciones();
    EntityManager em = PerThreadEntityManagers.getEntityManager();
    repoOrganizaciones.setEntityManager(em);
    EntityTransaction tx = em.getTransaction();
    repoOrganizaciones.setEntityTransaction(tx);

    Organizacion orgApple= organizacionFactory.organizacionAppleEmpresa();
    Organizacion orgCalcon =organizacionFactory.organizacionCALCON();
    Organizacion orgUTN=organizacionFactory.organizacionUTN();

    repoOrganizaciones.agregar(orgApple);
    repoOrganizaciones.agregar(orgCalcon);
    repoOrganizaciones.agregar(orgUTN);

    assertEquals(3,repoOrganizaciones.buscarTodos().size());
  }


}
