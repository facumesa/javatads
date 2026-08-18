package sistemaViajes;

import sistemaViajes.Retorno;
import sistemaViajes.ImplementacionSistema;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import sistemaViajes.Sistema;

public class Test09_RegistrarVuelo {

    private Retorno retorno;
    private final Sistema s = new ImplementacionSistema();

    @Before
    public void setUp() {
        s.inicializarSistema();
    }

    @Test
    public void registrarVueloOk() {
        s.registrarAeropuerto("MVD", "Carrasco");
        s.registrarAeropuerto("EZE", "Ezeiza");

        Retorno r = s.registrarVuelo("MVD", "EZE", "VU001", 100, 250);

        assertEquals(Retorno.Resultado.OK, r.getResultado());
    }

    @Test
    public void registrarVueloError1CapacidadCero() {
        s.registrarAeropuerto("MVD", "Carrasco");
        s.registrarAeropuerto("EZE", "Ezeiza");

        Retorno r = s.registrarVuelo("MVD", "EZE", "VU001", 0, 250);

        assertEquals(Retorno.Resultado.ERROR_1, r.getResultado());
    }

    @Test
    public void registrarVueloError1CapacidadNegativa() {
        s.registrarAeropuerto("MVD", "Carrasco");
        s.registrarAeropuerto("EZE", "Ezeiza");

        Retorno r = s.registrarVuelo("MVD", "EZE", "VU001", -10, 250);

        assertEquals(Retorno.Resultado.ERROR_1, r.getResultado());
    }

    @Test
    public void registrarVueloError1CostoCero() {
        s.registrarAeropuerto("MVD", "Carrasco");
        s.registrarAeropuerto("EZE", "Ezeiza");

        Retorno r = s.registrarVuelo("MVD", "EZE", "VU001", 100, 0);

        assertEquals(Retorno.Resultado.ERROR_1, r.getResultado());
    }

    @Test
    public void registrarVueloError1CostoNegativo() {
        s.registrarAeropuerto("MVD", "Carrasco");
        s.registrarAeropuerto("EZE", "Ezeiza");

        Retorno r = s.registrarVuelo("MVD", "EZE", "VU001", 100, -250);

        assertEquals(Retorno.Resultado.ERROR_1, r.getResultado());
    }

    @Test
    public void registrarVueloError2OrigenNull() {
        s.registrarAeropuerto("EZE", "Ezeiza");

        Retorno r = s.registrarVuelo(null, "EZE", "VU001", 100, 250);

        assertEquals(Retorno.Resultado.ERROR_2, r.getResultado());
    }

    @Test
    public void registrarVueloError2OrigenVacio() {
        s.registrarAeropuerto("EZE", "Ezeiza");

        Retorno r = s.registrarVuelo("", "EZE", "VU001", 100, 250);

        assertEquals(Retorno.Resultado.ERROR_2, r.getResultado());
    }

    @Test
    public void registrarVueloError2DestinoNull() {
        s.registrarAeropuerto("MVD", "Carrasco");

        Retorno r = s.registrarVuelo("MVD", null, "VU001", 100, 250);

        assertEquals(Retorno.Resultado.ERROR_2, r.getResultado());
    }

    @Test
    public void registrarVueloError2DestinoVacio() {
        s.registrarAeropuerto("MVD", "Carrasco");

        Retorno r = s.registrarVuelo("MVD", "", "VU001", 100, 250);

        assertEquals(Retorno.Resultado.ERROR_2, r.getResultado());
    }

    @Test
    public void registrarVueloError2CodigoVueloNull() {
        s.registrarAeropuerto("MVD", "Carrasco");
        s.registrarAeropuerto("EZE", "Ezeiza");

        Retorno r = s.registrarVuelo("MVD", "EZE", null, 100, 250);

        assertEquals(Retorno.Resultado.ERROR_2, r.getResultado());
    }

    @Test
    public void registrarVueloError2CodigoVueloVacio() {
        s.registrarAeropuerto("MVD", "Carrasco");
        s.registrarAeropuerto("EZE", "Ezeiza");

        Retorno r = s.registrarVuelo("MVD", "EZE", "", 100, 250);

        assertEquals(Retorno.Resultado.ERROR_2, r.getResultado());
    }

    @Test
    public void registrarVueloError2CodigoVueloConEspacios() {
        s.registrarAeropuerto("MVD", "Carrasco");
        s.registrarAeropuerto("EZE", "Ezeiza");

        Retorno r = s.registrarVuelo("MVD", "EZE", "   ", 100, 250);

        assertEquals(Retorno.Resultado.ERROR_2, r.getResultado());
    }

    @Test
    public void registrarVueloError3NoExisteAeropuertoOrigen() {
        s.registrarAeropuerto("EZE", "Ezeiza");

        Retorno r = s.registrarVuelo("MVD", "EZE", "VU001", 100, 250);

        assertEquals(Retorno.Resultado.ERROR_3, r.getResultado());
    }

    @Test
    public void registrarVueloError4NoExisteAeropuertoDestino() {
        s.registrarAeropuerto("MVD", "Carrasco");

        Retorno r = s.registrarVuelo("MVD", "EZE", "VU001", 100, 250);

        assertEquals(Retorno.Resultado.ERROR_4, r.getResultado());
    }

    @Test
    public void registrarVueloError5CodigoVueloRepetido() {
        s.registrarAeropuerto("MVD", "Carrasco");
        s.registrarAeropuerto("EZE", "Ezeiza");
        s.registrarAeropuerto("AEP", "Aeroparque");

        s.registrarVuelo("MVD", "EZE", "VU001", 100, 250);

        Retorno r = s.registrarVuelo("MVD", "AEP", "VU001", 80, 200);

        assertEquals(Retorno.Resultado.ERROR_5, r.getResultado());
    }

    @Test
    public void registrarDosVuelosDiferentesOk() {
        s.registrarAeropuerto("MVD", "Carrasco");
        s.registrarAeropuerto("EZE", "Ezeiza");

        Retorno r1 = s.registrarVuelo("MVD", "EZE", "VU001", 100, 250);
        Retorno r2 = s.registrarVuelo("EZE", "MVD", "VU002", 120, 300);

        assertEquals(Retorno.Resultado.OK, r1.getResultado());
        assertEquals(Retorno.Resultado.OK, r2.getResultado());
    }

    @Test
    public void registrarVueloLuegoObtenerInformacionOk() {
        s.registrarAeropuerto("MVD", "Carrasco");
        s.registrarAeropuerto("EZE", "Ezeiza");

        s.registrarVuelo("MVD", "EZE", "VU001", 100, 250);

        Retorno r = s.obtenerInformacionDeVuelo("VU001");

        assertEquals(Retorno.Resultado.OK, r.getResultado());
        assertEquals("MVD:EZE;VU001;100;250;PROGRAMADO;0;0", r.getValorString());
    }
}
