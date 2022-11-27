package model.repositorios.repositoriosDBs;

import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class GenericRepositoryJPA<T> implements GenericRepository<T>{


  protected EntityManager entityManager;
  private Class<T> type;

  protected EntityTransaction entityTransaction;

  public EntityManager getEntityManager() {
    return entityManager;
  }

  public void setEntityManager(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  public void setEntityTransaction(EntityTransaction entityTransaction) {
    this.entityTransaction = entityTransaction;
  }

  public void settearEntitys(){
    EntityManager em = PerThreadEntityManagers.getEntityManager();
    this.setEntityManager(em);
    EntityTransaction tx = em.getTransaction();
    this.setEntityTransaction(tx);
  }
  public GenericRepositoryJPA() {
    Type t = getClass().getGenericSuperclass();
    ParameterizedType pt = (ParameterizedType) t;
    type = (Class) pt.getActualTypeArguments()[0];
  }

  public void agregar(T t) {
   // entityTransaction.begin();
    entityManager.persist(t);
   // entityTransaction.commit();
  }

  public void agregarBoostrap(T t) {
     entityTransaction.begin();
    entityManager.persist(t);
     entityTransaction.commit();
  }


  public void borrar(final Object objeto) {
   // entityTransaction.begin();
    entityManager.remove(entityManager.merge(objeto));
   // entityTransaction.commit();
  }

  public void borrarBoostrap(final Object objeto) {
     entityTransaction.begin();
      entityManager.remove(entityManager.merge(objeto));
     entityTransaction.commit();
  }

  public T encontrar(final Object id) {
    return (T) entityManager.find(type, id);
  }

  public void actualizar(final T t) {
   // entityTransaction.begin();
    entityManager.merge(t);
   // entityTransaction.commit();
  }

  public void actualizarBoostrap(final T t) {
     entityTransaction.begin();
    entityManager.merge(t);
     entityTransaction.commit();
  }

/* Otra forma de actualizar un object
  public void actualizarValor(Double id_object,double valor, EntityManager em){

    EntityTransaction tx = em.getTransaction();
    FactorDeEmision objectEncontrado = em.find(object.class, id_object);
    tx.begin();
    objectEncontrado.setValor(valor);
    tx.commit();
  }
*/
  public List<T> buscarTodos() {
    CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
    CriteriaQuery<T> criteriaQuery = cb.createQuery(type);
    Root<T> root = criteriaQuery.from(type);
    criteriaQuery.select(root);
    TypedQuery<T> query = entityManager.createQuery(criteriaQuery);
    return query.getResultList();
  }
}