package sistemaViajes;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class Test16_ConsultaDisponibilidad {

    private Retorno retorno;
    private final Sistema s = new ImplementacionSistema();

    @Before
    public void setUp() {
        s.inicializarSistema();
    }

    @Test
    public void consultaDisponibilidadError1CantidadCero() {

        int[][] matriz = new int[6][26];

        Retorno r = s.consultaDisponibilidad(
                matriz,
                0,
                Clase.PRIMERA
        );

        assertEquals(Retorno.Resultado.ERROR_1, r.getResultado());
    }

    @Test
    public void consultaDisponibilidadError1CantidadNegativa() {

        int[][] matriz = new int[6][26];

        Retorno r = s.consultaDisponibilidad(
                matriz,
                -1,
                Clase.PRIMERA
        );

        assertEquals(Retorno.Resultado.ERROR_1, r.getResultado());
    }

    @Test
    public void consultaDisponibilidadPrimeraClaseUnaOpcion() {

        int[][] matriz = new int[6][26];

        // Ocupo todo
        for (int f = 0; f < 6; f++) {
            for (int c = 0; c < 26; c++) {
                matriz[f][c] = 1;
            }
        }

        // Dejo libre solo A1-B1-C1
        matriz[0][0] = 0;
        matriz[1][0] = 0;
        matriz[2][0] = 0;

        Retorno r = s.consultaDisponibilidad(
                matriz,
                3,
                Clase.PRIMERA
        );

        assertEquals(Retorno.Resultado.OK, r.getResultado());
        assertEquals(1, r.getValorEntero());
        assertEquals("A1-B1-C1", r.getValorString());
    }

    @Test
    public void consultaDisponibilidadDosOpcionesMismaColumna() {

        int[][] matriz = new int[6][26];

        for (int f = 0; f < 6; f++) {
            for (int c = 0; c < 26; c++) {
                matriz[f][c] = 1;
            }
        }

        // Columna 4 libre completa
        for (int f = 0; f < 6; f++) {
            matriz[f][3] = 0;
        }

        Retorno r = s.consultaDisponibilidad(
                matriz,
                3,
                Clase.EJECUTIVA
        );

        assertEquals(Retorno.Resultado.OK, r.getResultado());

        assertEquals(4, r.getValorEntero());

        assertEquals(
                "A4-B4-C4|B4-C4-D4|C4-D4-E4|D4-E4-F4",
                r.getValorString()
        );
    }

    @Test
    public void consultaDisponibilidadSinOpciones() {

        int[][] matriz = new int[6][26];

        for (int f = 0; f < 6; f++) {
            for (int c = 0; c < 26; c++) {
                matriz[f][c] = 1;
            }
        }

        Retorno r = s.consultaDisponibilidad(
                matriz,
                2,
                Clase.TURISTA
        );

        assertEquals(Retorno.Resultado.OK, r.getResultado());
        assertEquals(0, r.getValorEntero());
        assertEquals("", r.getValorString());
    }

    @Test
    public void consultaDisponibilidadVariasColumnas() {

        int[][] matriz = new int[6][26];

        for (int f = 0; f < 6; f++) {
            for (int c = 0; c < 26; c++) {
                matriz[f][c] = 1;
            }
        }

        // A4-B4 libres
        matriz[0][3] = 0;
        matriz[1][3] = 0;

        // A5-B5 libres
        matriz[0][4] = 0;
        matriz[1][4] = 0;

        Retorno r = s.consultaDisponibilidad(
                matriz,
                2,
                Clase.EJECUTIVA
        );

        assertEquals(Retorno.Resultado.OK, r.getResultado());

        assertEquals(2, r.getValorEntero());

        assertEquals(
                "A4-B4|A5-B5",
                r.getValorString()
        );
    }
}