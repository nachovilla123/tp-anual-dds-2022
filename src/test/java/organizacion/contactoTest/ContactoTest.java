package organizacion.contactoTest;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import model.organizacion.Organizacion;
import model.organizacion.Contacto;
import model.organizacion.trayecto.Ubicacion;

import static org.junit.jupiter.api.Assertions.*;

class ContactoTest {

    Contacto contacto_01 = new Contacto("p@gmail.com",1155113871,"Aquiles Bailo");
    Contacto contacto_02 = new Contacto("a@hotmail.com",1155113871,"Aitor Menta");
    Ubicacion ubicacion_01 = new Ubicacion(1, "Pedro Goyena", 2000);
    Organizacion organizacion_01 = new Organizacion("SA", Organizacion.Tipo.GUBERNAMENTAL,ubicacion_01, Organizacion.Clasificacion.EMPRESA_SECTOR_SECUNDARIO);


    @Before
    public void setup() {


    }

    @Test
    void contacto_guarda_correo() {
        assertEquals("p@gmail.com",contacto_01.getEmailDeContacto());
    }

    @Test
    void contacto_guarda_wpp() {
        assertEquals(1155113871,contacto_01.getNumeroDeContactoWPP());
    }

    @Test
    void contacto_guarda_su_nombre() {
        assertEquals("Aquiles Bailo",contacto_01.getNombreDelContacto());
    }

    @Test
    void se_cargan_dos_contactos_a_organizacion() {
        organizacion_01.agregarContacto(contacto_01);
        organizacion_01.agregarContacto(contacto_02);

        assertEquals(2,organizacion_01.getContactos().size());
    }

    /*
    //TODO no me salio
    @Test //(expected = RuntimeException.class, message = "El correo o wpp del contacto ya esta registrado")
    void se_cargan_un_contacto_existente_y_se_tira_excepcion() {
        organizacion_01.agregarContacto(contacto_01);
        organizacion_01.agregarContacto(contacto_02);
        organizacion_01.agregarContacto(contacto_02);

        assertEquals(2, organizacion_01.getContactos().size());
        //assertThrows(RuntimeException.class,organizacion_01.validarExistenciaDeContacto(contacto_02));
    }

     */







}