package sistemaViajes;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class Test11_AbrirVuelo {

    private Retorno retorno;
    private final Sistema s = new ImplementacionSistema();

    @Before
    public void setUp() {
        s.inicializarSistema();
    }

    @Test
    public void abrirVueloOk() {
        s.registrarAeropuerto("MVD", "Carrasco");
        s.registrarAeropuerto("EZE", "Ezeiza");
        s.registrarVuelo("MVD", "EZE", "VU001", 100, 250);

        Retorno r = s.abrirVuelo("VU001");

        assertEquals(Retorno.Resultado.OK, r.getResultado());
    }

    @Test
    public void abrirVueloError1CodigoNull() {
        Retorno r = s.abrirVuelo(null);

        assertEquals(Retorno.Resultado.ERROR_1, r.getResultado());
    }

    @Test
    public void abrirVueloError1CodigoVacio() {
        Retorno r = s.abrirVuelo("");

        assertEquals(Retorno.Resultado.ERROR_1, r.getResultado());
    }

    @Test
    public void abrirVueloError1CodigoConEspacios() {
        Retorno r = s.abrirVuelo("   ");

        assertEquals(Retorno.Resultado.ERROR_1, r.getResultado());
    }

    @Test
    public void abrirVueloError2VueloNoExiste() {
        s.registrarAeropuerto("MVD", "Carrasco");
        s.registrarAeropuerto("EZE", "Ezeiza");

        Retorno r = s.abrirVuelo("VU999");

        assertEquals(Retorno.Resultado.ERROR_2, r.getResultado());
    }

    @Test
    public void abrirVueloError3YaAbierto() {
        s.registrarAeropuerto("MVD", "Carrasco");
        s.registrarAeropuerto("EZE", "Ezeiza");

        s.registrarVuelo("MVD", "EZE", "VU001", 100, 250);
        s.abrirVuelo("VU001");

        Retorno r = s.abrirVuelo("VU001");

        assertEquals(Retorno.Resultado.ERROR_3, r.getResultado());
    }

    @Test
    public void abrirVueloCambiaEstadoACorrectamente() {
        s.registrarAeropuerto("MVD", "Carrasco");
        s.registrarAeropuerto("EZE", "Ezeiza");

        s.registrarVuelo("MVD", "EZE", "VU001", 100, 250);

        Retorno abrir = s.abrirVuelo("VU001");
        Retorno info = s.obtenerInformacionDeVuelo("VU001");

        assertEquals(Retorno.Resultado.OK, abrir.getResultado());
        assertEquals(
                "MVD:EZE;VU001;100;250;ABIERTO;0;0",
                info.getValorString()
        );
    }

    @Test
    public void abrirDosVuelosDiferentes() {
        s.registrarAeropuerto("MVD", "Carrasco");
        s.registrarAeropuerto("EZE", "Ezeiza");

        s.registrarVuelo("MVD", "EZE", "VU001", 100, 250);
        s.registrarVuelo("EZE", "MVD", "VU002", 120, 300);

        Retorno r1 = s.abrirVuelo("VU001");
        Retorno r2 = s.abrirVuelo("VU002");

        assertEquals(Retorno.Resultado.OK, r1.getResultado());
        assertEquals(Retorno.Resultado.OK, r2.getResultado());
    }

    @Test
    public void abrirVueloNoAfectaOtrosVuelos() {
        s.registrarAeropuerto("MVD", "Carrasco");
        s.registrarAeropuerto("EZE", "Ezeiza");

        s.registrarVuelo("MVD", "EZE", "VU001", 100, 250);
        s.registrarVuelo("EZE", "MVD", "VU002", 120, 300);

        s.abrirVuelo("VU001");

        Retorno info1 = s.obtenerInformacionDeVuelo("VU001");
        Retorno info2 = s.obtenerInformacionDeVuelo("VU002");

        assertEquals(
                "MVD:EZE;VU001;100;250;ABIERTO;0;0",
                info1.getValorString()
        );

        assertEquals(
                "EZE:MVD;VU002;120;300;PROGRAMADO;0;0",
                info2.getValorString()
        );
    }
}