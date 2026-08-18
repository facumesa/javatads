package tads;

public class Cola<T> implements ICola<T>{
    private Nodo<T> cola;
    private Nodo<T> fin;
    private int cantidad;

    public Cola() {
        this.cola = null;
        this.fin = null;
        this.cantidad = 0;
    }

    @Override
    public boolean esVacia() {
         return this.fin == null;
    }

    @Override
    public void encolar(T n) {
        Nodo<T> nuevo = new Nodo(n);
        nuevo.setSiguiente(null);
            
        if(this.esVacia()){
            this.cola = nuevo;
        }else{
            this.fin.setSiguiente(nuevo);
        }
        
        this.fin = nuevo;
        this.cantidad++;
    }

    @Override
    public void desencolar() {
        if(!this.esVacia()){
            if(this.cola.getSiguiente() == null){ // Hay solo un nodo
                this.vaciar();
            }else{
                Nodo<T> aBorrar = this.cola;
                this.cola = this.cola.getSiguiente();
                aBorrar.setSiguiente(null);
                this.cantidad--;
            }
        }
    }

    @Override
    public T front() {
        return this.cola.getDato();
    }
    
    @Override
    public void vaciar() {
        this.cola = null;
        this.fin = null;
        this.cantidad = 0;
    }
    
    @Override
    public int cantElementos() {
        return this.cantidad;
    }
    
    
}