package sistemaViajes;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class Test15_EmbarqueYDespegueDeVuelo {

    private Retorno retorno;
    private final Sistema s = new ImplementacionSistema();

    @Before
    public void setUp() {
        s.inicializarSistema();
    }

    @Test
    public void embarqueOkUnVuelo() {
        s.registrarAeropuerto("MVD", "Carrasco");
        s.registrarAeropuerto("EZE", "Ezeiza");

        s.registrarVuelo("MVD", "EZE", "VU001", 100, 250);
        s.abrirVuelo("VU001");
        s.cerrarVuelo("VU001");

        Retorno r = s.embarqueYDespegueDeVuelo("MVD");

        assertEquals(Retorno.Resultado.OK, r.getResultado());
        assertEquals("VU001", r.getValorString());
        assertEquals(0, r.getValorEntero());
    }

    @Test
    public void embarqueError1CodigoNull() {
        Retorno r = s.embarqueYDespegueDeVuelo(null);

        assertEquals(Retorno.Resultado.ERROR_1, r.getResultado());
    }

    @Test
    public void embarqueError1CodigoVacio() {
        Retorno r = s.embarqueYDespegueDeVuelo("");

        assertEquals(Retorno.Resultado.ERROR_1, r.getResultado());
    }

    @Test
    public void embarqueError1CodigoConEspacios() {
        Retorno r = s.embarqueYDespegueDeVuelo("   ");

        assertEquals(Retorno.Resultado.ERROR_1, r.getResultado());
    }

    @Test
    public void embarqueError2AeropuertoNoExiste() {
        Retorno r = s.embarqueYDespegueDeVuelo("MVD");

        assertEquals(Retorno.Resultado.ERROR_2, r.getResultado());
    }

    @Test
    public void embarqueError3SinVuelosEnCola() {
        s.registrarAeropuerto("MVD", "Carrasco");

        Retorno r = s.embarqueYDespegueDeVuelo("MVD");

        assertEquals(Retorno.Resultado.ERROR_3, r.getResultado());
    }

    @Test
    public void embarqueRespetaOrdenFIFO() {
        s.registrarAeropuerto("MVD", "Carrasco");
        s.registrarAeropuerto("EZE", "Ezeiza");

        s.registrarVuelo("MVD", "EZE", "VU001", 100, 250);
        s.registrarVuelo("MVD", "EZE", "VU002", 100, 250);
        s.registrarVuelo("MVD", "EZE", "VU003", 100, 250);

        s.abrirVuelo("VU001");
        s.cerrarVuelo("VU001");

        s.abrirVuelo("VU002");
        s.cerrarVuelo("VU002");

        s.abrirVuelo("VU003");
        s.cerrarVuelo("VU003");

        Retorno r1 = s.embarqueYDespegueDeVuelo("MVD");
        Retorno r2 = s.embarqueYDespegueDeVuelo("MVD");
        Retorno r3 = s.embarqueYDespegueDeVuelo("MVD");

        assertEquals("VU001", r1.getValorString());
        assertEquals(2, r1.getValorEntero());

        assertEquals("VU002", r2.getValorString());
        assertEquals(1, r2.getValorEntero());

        assertEquals("VU003", r3.getValorString());
        assertEquals(0, r3.getValorEntero());
    }

    @Test
    public void embarqueCambiaEstadoAFinalizado() {
        s.registrarAeropuerto("MVD", "Carrasco");
        s.registrarAeropuerto("EZE", "Ezeiza");

        s.registrarVuelo("MVD", "EZE", "VU001", 100, 250);
        s.abrirVuelo("VU001");
        s.cerrarVuelo("VU001");

        s.embarqueYDespegueDeVuelo("MVD");

        Retorno info = s.obtenerInformacionDeVuelo("VU001");

        assertEquals(Retorno.Resultado.OK, info.getResultado());
        assertEquals("MVD:EZE;VU001;100;250;FINALIZADO;0;0", info.getValorString());
    }

    @Test
    public void embarqueNoAfectaColaDeOtroAeropuerto() {
        s.registrarAeropuerto("MVD", "Carrasco");
        s.registrarAeropuerto("EZE", "Ezeiza");
        s.registrarAeropuerto("AEP", "Aeroparque");

        s.registrarVuelo("MVD", "EZE", "VU001", 100, 250);
        s.registrarVuelo("AEP", "EZE", "VU002", 100, 250);

        s.abrirVuelo("VU001");
        s.cerrarVuelo("VU001");

        s.abrirVuelo("VU002");
        s.cerrarVuelo("VU002");

        Retorno r = s.embarqueYDespegueDeVuelo("MVD");
        Retorno aeropuertoAEP = s.obtenerAeropuerto("AEP");

        assertEquals(Retorno.Resultado.OK, r.getResultado());
        assertEquals("VU001", r.getValorString());

        assertEquals(Retorno.Resultado.OK, aeropuertoAEP.getResultado());
        assertEquals(1, aeropuertoAEP.getValorEntero());
    }

    @Test
    public void embarqueLuegoDeVaciarColaDaError3() {
        s.registrarAeropuerto("MVD", "Carrasco");
        s.registrarAeropuerto("EZE", "Ezeiza");

        s.registrarVuelo("MVD", "EZE", "VU001", 100, 250);
        s.abrirVuelo("VU001");
        s.cerrarVuelo("VU001");

        s.embarqueYDespegueDeVuelo("MVD");

        Retorno r = s.embarqueYDespegueDeVuelo("MVD");

        assertEquals(Retorno.Resultado.ERROR_3, r.getResultado());
    }
}