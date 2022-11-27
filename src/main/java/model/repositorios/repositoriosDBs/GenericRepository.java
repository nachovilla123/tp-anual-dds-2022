package model.repositorios.repositoriosDBs;

import java.util.List;

public interface GenericRepository<T>{

  void agregar(T t);

  void borrar(T t);

  T encontrar(T t);

  void actualizar(T t);

  List<T> buscarTodos();
}