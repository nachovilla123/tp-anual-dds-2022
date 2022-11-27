package model.implementacionCSV;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import model.exceptions.AbrirCSV;
import model.exceptions.AbrirLecturaCSV;
import model.exceptions.ArchivoInexistente;
import model.exceptions.CerrarCSV;

import java.io.*;
import java.util.List;


public class GestorCSV {

  private File archivo;
  public String path;
  private CSVReader lectura;
  private CSVWriter escritura;


  public GestorCSV(String path) {
    this.path = path;
  }


  public List<String[]> obtenerRegistros() {
    this.existe();
    this.comenzarLectura();
    List<String[]> registros;
    try {
      registros = this.lectura.readAll();
    } catch (IOException | CsvException e) {
      throw new AbrirLecturaCSV("Ha ocurrido un error durante la asignacion de los datos del archivo");
    }
    this.terminarLectura();
    return registros;
  }

  public void cerrarArchivo() {
    try {
      escritura.close();
    } catch (IOException e) {
      throw new CerrarCSV("Ha ocurrido un error al intentar cerrar el archivo CSV");
    }
  }

  public void abrirArchivo() {
    try {
      this.escritura = new CSVWriter(new FileWriter(this.archivo, true),
          ',',
          CSVWriter.NO_QUOTE_CHARACTER,
          CSVWriter.DEFAULT_ESCAPE_CHARACTER,
          CSVWriter.DEFAULT_LINE_END);
    } catch (IOException e) {
      throw new AbrirCSV("Ha ocurrido un error al intentar abrir el archivo CSV");
    }
  }

  public void comenzarLectura() {
    try {
      this.lectura = new CSVReader(new FileReader(this.archivo));
    } catch (FileNotFoundException e) {
      throw new AbrirLecturaCSV("Ha ocurrido un error al intentar abrir en modo lectura el archivo CSV");
    }
  }

  public void terminarLectura() {
    try {
      this.lectura.close();
    } catch (IOException e) {
      throw new ArchivoInexistente("Ocurrio un error al cerrar la lectura");
    }
  }

  public void existe() {
    this.archivo = new File(path); // creamos el archivo
    if (!archivo.exists()) { // validamos que exista!
      throw new ArchivoInexistente("No existe el archivo que se intento cargar.");
    }
  }

}






