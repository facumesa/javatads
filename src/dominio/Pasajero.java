
package dominio;

import sistemaViajes.Categoria;

public class Pasajero implements Comparable<Pasajero>{
    private String cedula;
    private String nombre;
    private int edad;
    private Categoria categoria;

    public Pasajero(String cedula, String nombre, int edad, Categoria categoria) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.edad = edad;
        this.categoria = categoria;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return cedula + ";" + nombre + ";" + edad + ";" + categoria.getTexto();
    }

    @Override
    public int compareTo(Pasajero otro) {
      
        int cedulaActual = Integer.parseInt(cedula.replace(".", "").replace("-", ""));
        int cedulaOtra = Integer.parseInt(otro.getCedula().replace(".", "").replace("-", ""));
        
        return Integer.compare(cedulaActual, cedulaOtra);
    }
    
    @Override
    public boolean equals(Object obj) {
    if (this == obj) {
        return true;
    }

    if (obj == null) {
        return false;
    }

    Pasajero otro = (Pasajero) obj;
    return this.cedula.equals(otro.cedula);
}
}
