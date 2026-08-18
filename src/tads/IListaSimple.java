
package tads;

public interface IListaSimple<T> {
    void agregarOrd(T n);
    boolean esVacia();
    void agregarInicio(T n);
    void agregarFinal(T n);
    Pila listaAPila();
    boolean pertenece(T n);
    public T obtenerElemento(int indice);
    public int cantElementos();
    public T buscar(T dato);
    String listarAscendente();
    String listarDescendente();
}
