package dominio;

import sistemaViajes.Estado;
import tads.Lista;

public class Vuelo implements Comparable<Vuelo> {

    private String codigoAeropuertoOrigen;
    private String codigoAeropuertoDestino;
    private String codigoDeVuelo;
    private int capacidad;
    private int costoEnDolares;
    private Estado estado;
    private int cantidadConfirmados;
    private Lista<Reserva> reservas;

    public Vuelo(String codigoAeropuertoOrigen,
            String codigoAeropuertoDestino,
            String codigoDeVuelo,
            int capacidad,
            int costoEnDolares) {

        this.codigoAeropuertoOrigen = codigoAeropuertoOrigen;
        this.codigoAeropuertoDestino = codigoAeropuertoDestino;
        this.codigoDeVuelo = codigoDeVuelo;
        this.capacidad = capacidad;
        this.costoEnDolares = costoEnDolares;
        this.estado = Estado.PROGRAMADO;
        this.reservas = new Lista<>();
    }

    public String getCodigoAeropuertoOrigen() {
        return codigoAeropuertoOrigen;
    }

    public void setCodigoAeropuertoOrigen(String codigoAeropuertoOrigen) {
        this.codigoAeropuertoOrigen = codigoAeropuertoOrigen;
    }

    public String getCodigoAeropuertoDestino() {
        return codigoAeropuertoDestino;
    }

    public void setCodigoAeropuertoDestino(String codigoAeropuertoDestino) {
        this.codigoAeropuertoDestino = codigoAeropuertoDestino;
    }

    public String getCodigoDeVuelo() {
        return codigoDeVuelo;
    }

    public void setCodigoDeVuelo(String codigoDeVuelo) {
        this.codigoDeVuelo = codigoDeVuelo;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public int getCostoEnDolares() {
        return costoEnDolares;
    }

    public void setCostoEnDolares(int costoEnDolares) {
        this.costoEnDolares = costoEnDolares;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Lista<Reserva> getReservas() {
        return reservas;
    }

    public int getConfirmados() {
        return cantidadConfirmados;
    }

    public void confirmarPasajero() {
        this.cantidadConfirmados++;
    }

    public int cantidadReservasSinCheckIn() {

        int contador = 0;

        for (int i = 0; i < reservas.cantElementos(); i++) {

            Reserva r = reservas.obtenerElemento(i);

            if (!r.isCheckInRealizado()) {
                contador++;
            }
        }

        return contador;
    }

    public String listarPasajerosConfirmados() {

        Lista<Pasajero> aux = new Lista<>();

        for (int i = 0; i < reservas.cantElementos(); i++) {

            Reserva r = reservas.obtenerElemento(i);
            Pasajero p = r.getPasajero();
            
            if(r.isCheckInRealizado()){
                aux.agregarOrd(p);
            }
          
        }

        return aux.listarAscendente();
    }

    @Override
    public String toString() {
        return codigoAeropuertoOrigen + ":"
                + codigoAeropuertoDestino + ";"
                + codigoDeVuelo + ";"
                + capacidad + ";"
                + costoEnDolares + ";"
                + estado + ";"
                + reservas.cantElementos() + ";"
                + cantidadConfirmados;
    }

    @Override
    public int compareTo(Vuelo otro) {
        return this.codigoDeVuelo.compareTo(otro.codigoDeVuelo);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        Vuelo otro = (Vuelo) obj;
        return this.codigoDeVuelo.equals(otro.codigoDeVuelo);
    }

}
