package sistemaViajes;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class Test14_RealizarCheckIn {

    private Retorno retorno;
    private final Sistema s = new ImplementacionSistema();

    @Before
    public void setUp() {
        s.inicializarSistema();

        s.registrarAeropuerto("MVD", "Carrasco");
        s.registrarAeropuerto("EZE", "Ezeiza");
        s.registrarVuelo("MVD", "EZE", "VU001", 2, 250);

        s.registrarPasajero("1.111.111-1", "Ana", 25, Categoria.ESTANDAR);
        s.registrarPasajero("2.222.222-2", "Luis", 30, Categoria.FRECUENTE);
        s.registrarPasajero("3.333.333-3", "Pedro", 40, Categoria.PLATINO);
    }

    @Test
    public void realizarCheckInOk() {
        s.realizarReserva("VU001", "1.111.111-1");
        s.abrirVuelo("VU001");

        Retorno r = s.realizarCheckIn("VU001", "1.111.111-1");

        assertEquals(Retorno.Resultado.OK, r.getResultado());
    }

    @Test
    public void realizarCheckInAumentaConfirmados() {
        s.realizarReserva("VU001", "1.111.111-1");
        s.abrirVuelo("VU001");
        s.realizarCheckIn("VU001", "1.111.111-1");

        Retorno info = s.obtenerInformacionDeVuelo("VU001");

        assertEquals("MVD:EZE;VU001;2;250;ABIERTO;1;1", info.getValorString());
    }

    @Test
    public void realizarCheckInError1ParametroNull() {
        Retorno r = s.realizarCheckIn(null, "1.111.111-1");

        assertEquals(Retorno.Resultado.ERROR_1, r.getResultado());
    }

    @Test
    public void realizarCheckInError1ParametroVacio() {
        Retorno r = s.realizarCheckIn("", "1.111.111-1");

        assertEquals(Retorno.Resultado.ERROR_1, r.getResultado());
    }

    @Test
    public void realizarCheckInError2CedulaInvalida() {
        Retorno r = s.realizarCheckIn("VU001", "1.11.111");

        assertEquals(Retorno.Resultado.ERROR_2, r.getResultado());
    }

    @Test
    public void realizarCheckInError3VueloNoExiste() {
        Retorno r = s.realizarCheckIn("VU999", "1.111.111-1");

        assertEquals(Retorno.Resultado.ERROR_3, r.getResultado());
    }

    @Test
    public void realizarCheckInError4PasajeroNoExiste() {
        Retorno r = s.realizarCheckIn("VU001", "9.999.999-9");

        assertEquals(Retorno.Resultado.ERROR_4, r.getResultado());
    }

    @Test
    public void realizarCheckInError5VueloProgramado() {
        s.realizarReserva("VU001", "1.111.111-1");

        Retorno r = s.realizarCheckIn("VU001", "1.111.111-1");

        assertEquals(Retorno.Resultado.ERROR_5, r.getResultado());
    }

    @Test
    public void realizarCheckInError6SinReserva() {
        s.abrirVuelo("VU001");

        Retorno r = s.realizarCheckIn("VU001", "1.111.111-1");

        assertEquals(Retorno.Resultado.ERROR_6, r.getResultado());
    }

    @Test
    public void realizarCheckInError7YaHizoCheckIn() {
        s.realizarReserva("VU001", "1.111.111-1");
        s.abrirVuelo("VU001");
        s.realizarCheckIn("VU001", "1.111.111-1");

        Retorno r = s.realizarCheckIn("VU001", "1.111.111-1");

        assertEquals(Retorno.Resultado.ERROR_7, r.getResultado());
    }

    @Test
    public void realizarCheckInError8CapacidadMaxima() {
        s.realizarReserva("VU001", "1.111.111-1");
        s.realizarReserva("VU001", "2.222.222-2");
        s.realizarReserva("VU001", "3.333.333-3");

        s.abrirVuelo("VU001");

        s.realizarCheckIn("VU001", "1.111.111-1");
        s.realizarCheckIn("VU001", "2.222.222-2");

        Retorno r = s.realizarCheckIn("VU001", "3.333.333-3");

        assertEquals(Retorno.Resultado.ERROR_8, r.getResultado());
    }
}