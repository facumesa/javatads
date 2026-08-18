package tads;

public class Pila<T> implements IPila<T>{
    private Nodo<T> pila;
    private int cantidad;

    public Pila() {
        this.pila = null;
        this.cantidad = 0;
    }

    @Override
    public boolean esVacia() {
         return this.pila == null;
    }

    @Override
    public void apilar(T n) {
        Nodo<T> nuevo = new Nodo(n);
        
        nuevo.setSiguiente(this.pila);
        this.pila = nuevo;
        
        this.cantidad++;
    }

    @Override
    public void desapilar() {
        if(!this.esVacia()){
            if(this.pila.getSiguiente() == null){ 
                this.vaciar();
            }else{
                Nodo<T> aBorrar = this.pila;
                this.pila = this.pila.getSiguiente();
                aBorrar.setSiguiente(null);
                this.cantidad--;
            }
        }
    }
    
    //Preguntar
    public T pop(){
        if (pila == null) {
            return null;
        }
        Nodo<T> aux = pila;
        pila = aux.getSiguiente();
        aux.setSiguiente(null);
        return aux.getDato();
    }

    @Override
    public T top() {
       return this.pila.getDato();
    }

    @Override
    public void vaciar() {
        this.pila = null;
        this.cantidad = 0;
    }

    @Override
    public int cantElementos() {
        return this.cantidad;
    }
    

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}