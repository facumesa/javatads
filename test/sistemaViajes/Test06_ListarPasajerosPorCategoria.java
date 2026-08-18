package sistemaViajes;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class Test06_ListarPasajerosPorCategoria {

    private Retorno retorno;
    private final Sistema s = new ImplementacionSistema();


    @Before
    public void setUp() {
        s.inicializarSistema();
    }

    @Test
    public void listarCategoriaSinPasajerosDevuelveVacio() {
        Retorno r = s.listarPasajerosPorCategoría(Categoria.PLATINO);

        assertEquals(Retorno.Resultado.OK, r.getResultado());
        assertEquals("", r.getValorString());
    }

    @Test
    public void listarPlatinoOk() {
        s.registrarPasajero("1.111.111-1", "Ana", 25, Categoria.PLATINO);
        s.registrarPasajero("2.222.222-2", "Luis", 30, Categoria.FRECUENTE);
        s.registrarPasajero("3.333.333-3", "Pedro", 40, Categoria.PLATINO);

        Retorno r = s.listarPasajerosPorCategoría(Categoria.PLATINO);

        assertEquals(Retorno.Resultado.OK, r.getResultado());
        assertEquals(
                "1.111.111-1;Ana;25;Platino|3.333.333-3;Pedro;40;Platino",
                r.getValorString()
        );
    }

    @Test
    public void listarFrecuenteOk() {
        s.registrarPasajero("1.111.111-1", "Ana", 25, Categoria.PLATINO);
        s.registrarPasajero("2.222.222-2", "Luis", 30, Categoria.FRECUENTE);
        s.registrarPasajero("3.333.333-3", "Pedro", 40, Categoria.ESTANDAR);
        s.registrarPasajero("4.444.444-4", "Sofia", 28, Categoria.FRECUENTE);

        Retorno r = s.listarPasajerosPorCategoría(Categoria.FRECUENTE);

        assertEquals(Retorno.Resultado.OK, r.getResultado());
        assertEquals(
                "2.222.222-2;Luis;30;Frecuente|4.444.444-4;Sofia;28;Frecuente",
                r.getValorString()
        );
    }

    @Test
    public void listarEstandarOk() {
        s.registrarPasajero("1.111.111-1", "Ana", 25, Categoria.ESTANDAR);
        s.registrarPasajero("2.222.222-2", "Luis", 30, Categoria.FRECUENTE);
        s.registrarPasajero("3.333.333-3", "Pedro", 40, Categoria.ESTANDAR);

        Retorno r = s.listarPasajerosPorCategoría(Categoria.ESTANDAR);

        assertEquals(Retorno.Resultado.OK, r.getResultado());
        assertEquals(
                "1.111.111-1;Ana;25;Estándar|3.333.333-3;Pedro;40;Estándar",
                r.getValorString()
        );
    }

    @Test
    public void listarEsporadicoOk() {
        s.registrarPasajero("1.111.111-1", "Ana", 25, Categoria.ESPORADICO);
        s.registrarPasajero("2.222.222-2", "Luis", 30, Categoria.FRECUENTE);
        s.registrarPasajero("3.333.333-3", "Pedro", 40, Categoria.ESPORADICO);

        Retorno r = s.listarPasajerosPorCategoría(Categoria.ESPORADICO);

        assertEquals(Retorno.Resultado.OK, r.getResultado());
        assertEquals(
                "1.111.111-1;Ana;25;Esporádico|3.333.333-3;Pedro;40;Esporádico",
                r.getValorString()
        );
    }

    @Test
    public void listarCategoriaOrdenaPorCedulaAscendente() {
        s.registrarPasajero("3.333.333-3", "Pedro", 40, Categoria.PLATINO);
        s.registrarPasajero("1.111.111-1", "Ana", 25, Categoria.PLATINO);
        s.registrarPasajero("2.222.222-2", "Luis", 30, Categoria.PLATINO);

        Retorno r = s.listarPasajerosPorCategoría(Categoria.PLATINO);

        assertEquals(Retorno.Resultado.OK, r.getResultado());
        assertEquals(
                "1.111.111-1;Ana;25;Platino|2.222.222-2;Luis;30;Platino|3.333.333-3;Pedro;40;Platino",
                r.getValorString()
        );
    }

    @Test
    public void listarCategoriaNoIncluyeOtrasCategorias() {
        s.registrarPasajero("1.111.111-1", "Ana", 25, Categoria.PLATINO);
        s.registrarPasajero("2.222.222-2", "Luis", 30, Categoria.FRECUENTE);
        s.registrarPasajero("3.333.333-3", "Pedro", 40, Categoria.ESTANDAR);
        s.registrarPasajero("4.444.444-4", "Sofia", 28, Categoria.ESPORADICO);

        Retorno r = s.listarPasajerosPorCategoría(Categoria.FRECUENTE);

        assertEquals(Retorno.Resultado.OK, r.getResultado());
        assertEquals(
                "2.222.222-2;Luis;30;Frecuente",
                r.getValorString()
        );
    }

    @Test
    public void listarCategoriaNoTerminaConPipe() {
        s.registrarPasajero("1.111.111-1", "Ana", 25, Categoria.PLATINO);
        s.registrarPasajero("2.222.222-2", "Luis", 30, Categoria.PLATINO);

        Retorno r = s.listarPasajerosPorCategoría(Categoria.PLATINO);

        assertFalse(r.getValorString().endsWith("|"));
    }
}