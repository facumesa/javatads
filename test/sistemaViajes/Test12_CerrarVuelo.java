package sistemaViajes;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class Test12_CerrarVuelo {

    private Retorno retorno;
    private final Sistema s = new ImplementacionSistema();

    @Before
    public void setUp() {
        s.inicializarSistema();
    }

    @Test
    public void cerrarVueloOkSinReservas() {
        s.registrarAeropuerto("MVD", "Carrasco");
        s.registrarAeropuerto("EZE", "Ezeiza");
        s.registrarVuelo("MVD", "EZE", "VU001", 100, 250);
        s.abrirVuelo("VU001");

        Retorno r = s.cerrarVuelo("VU001");

        assertEquals(Retorno.Resultado.OK, r.getResultado());
        assertEquals("", r.getValorString());
        assertEquals(0, r.getValorEntero());
    }

    @Test
    public void cerrarVueloError1CodigoNull() {
        Retorno r = s.cerrarVuelo(null);

        assertEquals(Retorno.Resultado.ERROR_1, r.getResultado());
    }

    @Test
    public void cerrarVueloError1CodigoVacio() {
        Retorno r = s.cerrarVuelo("");

        assertEquals(Retorno.Resultado.ERROR_1, r.getResultado());
    }

    @Test
    public void cerrarVueloError1CodigoConEspacios() {
        Retorno r = s.cerrarVuelo("   ");

        assertEquals(Retorno.Resultado.ERROR_1, r.getResultado());
    }

    @Test
    public void cerrarVueloError2VueloNoExiste() {
        Retorno r = s.cerrarVuelo("VU999");

        assertEquals(Retorno.Resultado.ERROR_2, r.getResultado());
    }

    @Test
    public void cerrarVueloError3VueloProgramado() {
        s.registrarAeropuerto("MVD", "Carrasco");
        s.registrarAeropuerto("EZE", "Ezeiza");
        s.registrarVuelo("MVD", "EZE", "VU001", 100, 250);

        Retorno r = s.cerrarVuelo("VU001");

        assertEquals(Retorno.Resultado.ERROR_3, r.getResultado());
    }

    @Test
    public void cerrarVueloError3VueloYaCerrado() {
        s.registrarAeropuerto("MVD", "Carrasco");
        s.registrarAeropuerto("EZE", "Ezeiza");
        s.registrarVuelo("MVD", "EZE", "VU001", 100, 250);
        s.abrirVuelo("VU001");
        s.cerrarVuelo("VU001");

        Retorno r = s.cerrarVuelo("VU001");

        assertEquals(Retorno.Resultado.ERROR_3, r.getResultado());
    }

    @Test
    public void cerrarVueloOkConReservasSinCheckIn() {
        s.registrarPasajero("1.111.111-1", "Ana", 25, Categoria.ESTANDAR);
        s.registrarPasajero("2.222.222-2", "Luis", 30, Categoria.FRECUENTE);

        s.registrarAeropuerto("MVD", "Carrasco");
        s.registrarAeropuerto("EZE", "Ezeiza");

        s.registrarVuelo("MVD", "EZE", "VU001", 100, 250);

        s.realizarReserva("VU001", "1.111.111-1");
        s.realizarReserva("VU001", "2.222.222-2");

        s.abrirVuelo("VU001");

        Retorno r = s.cerrarVuelo("VU001");

        assertEquals(Retorno.Resultado.OK, r.getResultado());
        assertEquals("", r.getValorString());
        assertEquals(2, r.getValorEntero());
    }

    @Test
    public void cerrarVueloOkConReservasYCheckIn() {
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

        Retorno r = s.cerrarVuelo("VU001");

        assertEquals(Retorno.Resultado.OK, r.getResultado());
        assertEquals("1.111.111-1;Ana;25;Estándar|3.333.333-3;Pedro;40;Platino", r.getValorString());
        assertEquals(1, r.getValorEntero());
    }

    @Test
    public void cerrarVueloCambiaEstadoACerrado() {
        s.registrarAeropuerto("MVD", "Carrasco");
        s.registrarAeropuerto("EZE", "Ezeiza");
        s.registrarVuelo("MVD", "EZE", "VU001", 100, 250);
        s.abrirVuelo("VU001");

        s.cerrarVuelo("VU001");

        Retorno info = s.obtenerInformacionDeVuelo("VU001");

        assertEquals(Retorno.Resultado.OK, info.getResultado());
        assertEquals("MVD:EZE;VU001;100;250;CERRADO;0;0", info.getValorString());
    }

    @Test
    public void cerrarVueloAgregaVueloAColaDelAeropuertoOrigen() {
        s.registrarAeropuerto("MVD", "Carrasco");
        s.registrarAeropuerto("EZE", "Ezeiza");

        s.registrarVuelo("MVD", "EZE", "VU001", 100, 250);
        s.abrirVuelo("VU001");
        s.cerrarVuelo("VU001");

        Retorno aeropuerto = s.obtenerAeropuerto("MVD");

        assertEquals(Retorno.Resultado.OK, aeropuerto.getResultado());
        assertEquals(1, aeropuerto.getValorEntero());
    }

    @Test
    public void cerrarVariosVuelosMantieneColaEnOrigen() {
        s.registrarAeropuerto("MVD", "Carrasco");
        s.registrarAeropuerto("EZE", "Ezeiza");

        s.registrarVuelo("MVD", "EZE", "VU001", 100, 250);
        s.registrarVuelo("MVD", "EZE", "VU002", 100, 250);

        s.abrirVuelo("VU001");
        s.abrirVuelo("VU002");

        s.cerrarVuelo("VU001");
        s.cerrarVuelo("VU002");

        Retorno aeropuerto = s.obtenerAeropuerto("MVD");

        assertEquals(Retorno.Resultado.OK, aeropuerto.getResultado());
        assertEquals(2, aeropuerto.getValorEntero());
    }
}