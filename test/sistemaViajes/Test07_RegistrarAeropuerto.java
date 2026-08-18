package sistemaViajes;

import sistemaViajes.Retorno;
import sistemaViajes.ImplementacionSistema;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import sistemaViajes.Sistema;

public class Test07_RegistrarAeropuerto {

    private Retorno retorno;
    private final Sistema s = new ImplementacionSistema();

    @Before
    public void setUp() {
        s.inicializarSistema(); 
    }

    @Test
    public void registrarAeropuertoExitoso() {
        retorno = s.registrarAeropuerto("MVD", "Aeropuerto Internacional de Carrasco");
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());

        retorno = s.registrarAeropuerto("PDP", "Aeropuerto de Laguna del Sauce");
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
    }

    @Test
    public void registrarAeropuertoCodigoVacioONull() {
        retorno = s.registrarAeropuerto(null, "Aeropuerto de Carrasco");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        retorno = s.registrarAeropuerto("", "Aeropuerto de Carrasco");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        retorno = s.registrarAeropuerto("   ", "Aeropuerto de Carrasco");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());
    }

    @Test
    public void registrarAeropuertoNombreVacioONull() {
        retorno = s.registrarAeropuerto("MVD", null);
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        retorno = s.registrarAeropuerto("MVD", "");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        retorno = s.registrarAeropuerto("MVD", "     ");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());
    }

    @Test
    public void registrarAeropuertoDuplicado() {
        retorno = s.registrarAeropuerto("MVD", "Aeropuerto de Carrasco");
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());

        retorno = s.registrarAeropuerto("MVD", "Carrasco Viejo");
        assertEquals(Retorno.Resultado.ERROR_2, retorno.getResultado());
    }
}