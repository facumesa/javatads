package sistemaViajes;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class Test10_ObtenerInformacionDeVuelo {

    private Retorno retorno;
    private final Sistema s = new ImplementacionSistema();

    @Before
    public void setUp() {
        s.inicializarSistema();
    }

    @Test
    public void obtenerInformacionVueloOkSinReservas() {
        s.registrarAeropuerto("MVD", "Carrasco");
        s.registrarAeropuerto("EZE", "Ezeiza");
        s.registrarVuelo("MVD", "EZE", "VU001", 100, 250);

        Retorno r = s.obtenerInformacionDeVuelo("VU001");

        assertEquals(Retorno.Resultado.OK, r.getResultado());
        assertEquals("MVD:EZE;VU001;100;250;PROGRAMADO;0;0", r.getValorString());
    }

    @Test
    public void obtenerInformacionError1CodigoNull() {
        Retorno r = s.obtenerInformacionDeVuelo(null);

        assertEquals(Retorno.Resultado.ERROR_1, r.getResultado());
    }

    @Test
    public void obtenerInformacionError1CodigoVacio() {
        Retorno r = s.obtenerInformacionDeVuelo("");

        assertEquals(Retorno.Resultado.ERROR_1, r.getResultado());
    }

    @Test
    public void obtenerInformacionError1CodigoConEspacios() {
        Retorno r = s.obtenerInformacionDeVuelo("   ");

        assertEquals(Retorno.Resultado.ERROR_1, r.getResultado());
    }

    @Test
    public void obtenerInformacionError2VueloNoExiste() {
        s.registrarAeropuerto("MVD", "Carrasco");
        s.registrarAeropuerto("EZE", "Ezeiza");
        s.registrarVuelo("MVD", "EZE", "VU001", 100, 250);

        Retorno r = s.obtenerInformacionDeVuelo("VU999");

        assertEquals(Retorno.Resultado.ERROR_2, r.getResultado());
    }

    @Test
    public void obtenerInformacionOkEntreVariosVuelos() {
        s.registrarAeropuerto("MVD", "Carrasco");
        s.registrarAeropuerto("EZE", "Ezeiza");
        s.registrarAeropuerto("AEP", "Aeroparque");

        s.registrarVuelo("MVD", "EZE", "VU001", 100, 250);
        s.registrarVuelo("EZE", "AEP", "VU002", 80, 150);
        s.registrarVuelo("AEP", "MVD", "VU003", 120, 300);

        Retorno r = s.obtenerInformacionDeVuelo("VU002");

        assertEquals(Retorno.Resultado.OK, r.getResultado());
        assertEquals("EZE:AEP;VU002;80;150;PROGRAMADO;0;0", r.getValorString());
    }

    @Test
    public void obtenerInformacionOkConReservasSinCheckIn() {
        s.registrarPasajero("1.111.111-1", "Ana", 25, Categoria.ESTANDAR);
        s.registrarPasajero("2.222.222-2", "Luis", 30, Categoria.FRECUENTE);

        s.registrarAeropuerto("MVD", "Carrasco");
        s.registrarAeropuerto("EZE", "Ezeiza");

        s.registrarVuelo("MVD", "EZE", "VU001", 100, 250);

        s.realizarReserva("VU001", "1.111.111-1");
        s.realizarReserva("VU001", "2.222.222-2");

        Retorno r = s.obtenerInformacionDeVuelo("VU001");

        assertEquals(Retorno.Resultado.OK, r.getResultado());
        assertEquals("MVD:EZE;VU001;100;250;PROGRAMADO;2;0", r.getValorString());
    }

    @Test
    public void obtenerInformacionOkConReservasYCheckIn() {
        s.registrarPasajero("1.111.111-1", "Ana", 25, Categoria.ESTANDAR);
        s.registrarPasajero("2.222.222-2", "Luis", 30, Categoria.FRECUENTE);
        s.registrarPasajero("3.333.333-3", "Pedro", 40, Categoria.PLATINO);

        s.registrarAeropuerto("MVD", "Carrasco");
        s.registrarAeropuerto("EZE", "Ezeiza");

        s.registrarVuelo("MVD", "EZE", "VU001", 100, 250);

        s.realizarReserva("VU001", "1.111.111-1");
        s.realizarReserva("VU001", "2.222.222-2");
        s.realizarReserva("VU001", "3.333.333-3");

        s.abrirVuelo("VU001");

        s.realizarCheckIn("VU001", "1.111.111-1");
        s.realizarCheckIn("VU001", "3.333.333-3");

        Retorno r = s.obtenerInformacionDeVuelo("VU001");

        assertEquals(Retorno.Resultado.OK, r.getResultado());
        assertEquals("MVD:EZE;VU001;100;250;ABIERTO;3;2", r.getValorString());
    }

    @Test
    public void obtenerInformacionOkLuegoDeAbrirVuelo() {
        s.registrarAeropuerto("MVD", "Carrasco");
        s.registrarAeropuerto("EZE", "Ezeiza");

        s.registrarVuelo("MVD", "EZE", "VU001", 100, 250);
        s.abrirVuelo("VU001");

        Retorno r = s.obtenerInformacionDeVuelo("VU001");

        assertEquals(Retorno.Resultado.OK, r.getResultado());
        assertEquals("MVD:EZE;VU001;100;250;ABIERTO;0;0", r.getValorString());
    }

    @Test
    public void obtenerInformacionOkLuegoDeCerrarVuelo() {
        s.registrarPasajero("1.111.111-1", "Ana", 25, Categoria.ESTANDAR);

        s.registrarAeropuerto("MVD", "Carrasco");
        s.registrarAeropuerto("EZE", "Ezeiza");

        s.registrarVuelo("MVD", "EZE", "VU001", 100, 250);
        s.realizarReserva("VU001", "1.111.111-1");
        s.abrirVuelo("VU001");
        s.realizarCheckIn("VU001", "1.111.111-1");
        s.cerrarVuelo("VU001");

        Retorno r = s.obtenerInformacionDeVuelo("VU001");

        assertEquals(Retorno.Resultado.OK, r.getResultado());
        assertEquals("MVD:EZE;VU001;100;250;CERRADO;1;1", r.getValorString());
    }
}