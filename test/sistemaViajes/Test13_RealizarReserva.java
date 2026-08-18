package sistemaViajes;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class Test13_RealizarReserva {

    private Retorno retorno;
    private final Sistema s = new ImplementacionSistema();

    @Before
    public void setUp() {
        s.inicializarSistema();

        s.registrarAeropuerto("MVD", "Carrasco");
        s.registrarAeropuerto("EZE", "Ezeiza");
        s.registrarVuelo("MVD", "EZE", "VU001", 10, 250);

        s.registrarPasajero("1.111.111-1", "Ana", 25, Categoria.ESTANDAR);
        s.registrarPasajero("2.222.222-2", "Luis", 30, Categoria.FRECUENTE);
    }

    @Test
    public void realizarReservaOk() {
        Retorno r = s.realizarReserva("VU001", "1.111.111-1");

        assertEquals(Retorno.Resultado.OK, r.getResultado());
    }

    @Test
    public void realizarReservaAumentaCantidadReservas() {
        s.realizarReserva("VU001", "1.111.111-1");

        Retorno info = s.obtenerInformacionDeVuelo("VU001");

        assertEquals("MVD:EZE;VU001;10;250;PROGRAMADO;1;0", info.getValorString());
    }

    @Test
    public void realizarReservaOkVueloAbierto() {
        s.abrirVuelo("VU001");

        Retorno r = s.realizarReserva("VU001", "1.111.111-1");

        assertEquals(Retorno.Resultado.OK, r.getResultado());
    }

    @Test
    public void realizarReservaError1ParametroNull() {
        Retorno r = s.realizarReserva(null, "1.111.111-1");

        assertEquals(Retorno.Resultado.ERROR_1, r.getResultado());
    }

    @Test
    public void realizarReservaError1ParametroVacio() {
        Retorno r = s.realizarReserva("", "1.111.111-1");

        assertEquals(Retorno.Resultado.ERROR_1, r.getResultado());
    }

    @Test
    public void realizarReservaError2CedulaInvalida() {
        Retorno r = s.realizarReserva("VU001", "1.11.111");

        assertEquals(Retorno.Resultado.ERROR_2, r.getResultado());
    }

    @Test
    public void realizarReservaError3VueloNoExiste() {
        Retorno r = s.realizarReserva("VU999", "1.111.111-1");

        assertEquals(Retorno.Resultado.ERROR_3, r.getResultado());
    }

    @Test
    public void realizarReservaError4PasajeroNoExiste() {
        Retorno r = s.realizarReserva("VU001", "9.999.999-9");

        assertEquals(Retorno.Resultado.ERROR_4, r.getResultado());
    }

    @Test
    public void realizarReservaError5VueloCerrado() {
        s.abrirVuelo("VU001");
        s.cerrarVuelo("VU001");

        Retorno r = s.realizarReserva("VU001", "1.111.111-1");

        assertEquals(Retorno.Resultado.ERROR_5, r.getResultado());
    }

    @Test
    public void realizarReservaError6YaTieneReserva() {
        s.realizarReserva("VU001", "1.111.111-1");

        Retorno r = s.realizarReserva("VU001", "1.111.111-1");

        assertEquals(Retorno.Resultado.ERROR_6, r.getResultado());
    }

    @Test
    public void realizarReservaError7OverbookingCompleto() {
        s.registrarPasajero("3.333.333-3", "P3", 20, Categoria.ESTANDAR);
        s.registrarPasajero("4.444.444-4", "P4", 20, Categoria.ESTANDAR);
        s.registrarPasajero("5.555.555-5", "P5", 20, Categoria.ESTANDAR);
        s.registrarPasajero("6.666.666-6", "P6", 20, Categoria.ESTANDAR);
        s.registrarPasajero("7.777.777-7", "P7", 20, Categoria.ESTANDAR);
        s.registrarPasajero("8.888.888-8", "P8", 20, Categoria.ESTANDAR);
        s.registrarPasajero("9.999.999-9", "P9", 20, Categoria.ESTANDAR);
        s.registrarPasajero("1.222.222-2", "P10", 20, Categoria.ESTANDAR);
        s.registrarPasajero("1.333.333-3", "P11", 20, Categoria.ESTANDAR);
        s.registrarPasajero("1.444.444-4", "P12", 20, Categoria.ESTANDAR);

        s.realizarReserva("VU001", "1.111.111-1");
        s.realizarReserva("VU001", "2.222.222-2");
        s.realizarReserva("VU001", "3.333.333-3");
        s.realizarReserva("VU001", "4.444.444-4");
        s.realizarReserva("VU001", "5.555.555-5");
        s.realizarReserva("VU001", "6.666.666-6");
        s.realizarReserva("VU001", "7.777.777-7");
        s.realizarReserva("VU001", "8.888.888-8");
        s.realizarReserva("VU001", "9.999.999-9");
        s.realizarReserva("VU001", "1.222.222-2");
        s.realizarReserva("VU001", "1.333.333-3");

        Retorno r = s.realizarReserva("VU001", "1.444.444-4");

        assertEquals(Retorno.Resultado.ERROR_7, r.getResultado());
    }
}