package tads;

public class Lista<T extends Comparable> implements IListaSimple<T> {

    private Nodo<T> inicio;
    private Nodo<T> fin;
    private int cantidad;

    public Lista() {
        this.inicio = null;
        this.fin = null;
        this.cantidad = 0;
    }

    public Nodo<T> getInicio() {
        return inicio;
    }

    public Nodo<T> getFin() {
        return fin;
    }

    @Override
    public int cantElementos() {
        return cantidad;
    }

    @Override
    public boolean esVacia() {
        return this.fin == null;
    }

    @Override
    public void agregarInicio(T n) {
        Nodo<T> nuevo = new Nodo(n);

        nuevo.setSiguiente(this.inicio);
        this.inicio = nuevo;

        if (this.esVacia()) {
            this.fin = nuevo;
        }

        this.cantidad++;
    }

    @Override
    public void agregarFinal(T n) {
        if (this.esVacia()) {
            this.agregarInicio(n);
        } else {
            Nodo<T> nuevo = new Nodo(n);
            nuevo.setSiguiente(null);
            this.fin.setSiguiente(nuevo);
            this.fin = nuevo;

            this.cantidad++;
        }
    }

    @Override
    public void agregarOrd(T n) {
        if (this.esVacia() || n.compareTo(this.inicio.getDato()) <= 0) {
            this.agregarInicio(n);
        } else if (n.compareTo(this.fin.getDato()) >= 0) {
            this.agregarFinal(n);
        } else {
            Nodo<T> nuevo = new Nodo(n);
            Nodo<T> aux = this.inicio;

            while (aux.getSiguiente().getDato().compareTo(n) < 0) {
                aux = aux.getSiguiente();
            }

            nuevo.setSiguiente(aux.getSiguiente());
            aux.setSiguiente(nuevo);

            this.cantidad++;
        }
    }

    @Override
    public boolean pertenece(T n) {
        Nodo<T> aux = this.inicio;
        while (aux != null && !aux.getDato().equals(n)) {
            aux = aux.getSiguiente();
        }
        return aux != null;
    }

    @Override
    public Pila listaAPila() {
        Pila p1 = new Pila();
        Pila p2 = new Pila();
        Nodo aux = inicio;
        while (aux != null) {
            p1.apilar(aux.getDato());
            aux = aux.getSiguiente();
        }
        while (!p1.esVacia()) {
            Object obj = p1.top();
            p2.apilar(obj);
            p1.desapilar();
        }
        return p2;
    }

    @Override
    public T obtenerElemento(int indice) {
        int pos = 0;
        Nodo<T> aux = this.inicio;

        while (pos != indice) {
            aux = aux.getSiguiente();
            pos++;
        }

        return aux.getDato();
    }

    @Override
    public T buscar(T dato) {
        Nodo<T> aux = this.inicio;

        while (aux != null) {
            if (aux.getDato().equals(dato)) {
                return aux.getDato();
            }
            aux = aux.getSiguiente();
        }

        return null;
    }

    @Override
    public String listarAscendente() {
        String resultado = "";
        Nodo<T> aux = inicio;

        while (aux != null) {
            resultado += aux.getDato().toString();

            if (aux.getSiguiente() != null) {
                resultado += "|";
            }

            aux = aux.getSiguiente();
        }

        return resultado;
    }
    
    @Override
    public String listarDescendente() {
        String pasajerosString = "";
        Nodo<T> aux = inicio;
        Pila pila = new Pila();
        while (aux != null) {
            pila.apilar(aux.getDato());
            aux = aux.getSiguiente();
        }
        while (!pila.esVacia()) {
            Object p = pila.top();

            if (!pasajerosString.equals("")) {
                pasajerosString += "|";
            }

            pasajerosString += p.toString();
            pila.desapilar();
        }

        return pasajerosString;
    }

}
