package dominio;

import tads.Cola;

public class Aeropuerto implements Comparable<Aeropuerto> {

    private String codigo;
    private String nombre;
    private Cola<Vuelo> vuelosCola;

    public Aeropuerto(String codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.vuelosCola = new Cola<>();
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public Cola<Vuelo> getVuelosCola() {
        return vuelosCola;
    }

    @Override
    public String toString() {
        return codigo + ";" + nombre;
    }

    @Override
    public int compareTo(Aeropuerto otro) {
        return this.codigo.compareTo(otro.codigo);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        
        if (obj instanceof String) {
            return this.codigo.equals((String) obj);
        }

        if (obj instanceof Aeropuerto) {
            Aeropuerto otro = (Aeropuerto) obj;
            return this.codigo.equals(otro.codigo);
        }

        return false;
    }
}
