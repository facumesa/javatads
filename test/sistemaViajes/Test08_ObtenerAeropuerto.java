package sistemaViajes;

import sistemaViajes.Retorno;
import sistemaViajes.ImplementacionSistema;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import sistemaViajes.Sistema;

public class Test08_ObtenerAeropuerto {

    private Retorno retorno;
    private final Sistema s = new ImplementacionSistema();

    @Before
    public void setUp() {
        s.inicializarSistema();

        s.registrarAeropuerto("MVD", "Carrasco");
        s.registrarAeropuerto("PDP", "Laguna del Sauce");
    }

    @Test
    public void obtenerAeropuertoExitoso() {
        retorno = s.obtenerAeropuerto("MVD");
        
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        
        assertEquals("MVD;Carrasco", retorno.getValorString());
        
        assertEquals(0, retorno.getValorEntero());
    }

    @Test
    public void obtenerAeropuertoCodigoInvalido() {
        retorno = s.obtenerAeropuerto("");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        retorno = s.obtenerAeropuerto(null);
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());
        
        retorno = s.obtenerAeropuerto("   ");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());
    }

    @Test
    public void obtenerAeropuertoNoExiste() {
        retorno = s.obtenerAeropuerto("XYZ");
        assertEquals(Retorno.Resultado.ERROR_2, retorno.getResultado());
    }
}