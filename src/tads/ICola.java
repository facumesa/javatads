package tads;

public interface ICola<T> {
    /*
    PRE: -
    POS: Retorna true si la cola es vacía, false en caso contrario.
    */
    public boolean esVacia();
    
    public void encolar(T n);
    
    public void desencolar();
    
    /*
    PRE: La cola no es vacía
    POS: Retorna el elemento que está al frente
    */
    public T front();
    
    public void vaciar();
    
    public int cantElementos();
    
}