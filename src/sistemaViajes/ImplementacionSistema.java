package sistemaViajes;

//Facundo Mesa | 359734

import dominio.Aeropuerto;
import dominio.Pasajero;
import dominio.Reserva;
import dominio.Vuelo;
import tads.Lista;

public class ImplementacionSistema implements Sistema {

    private Lista<Pasajero> pasajeros;
    private Lista<Pasajero> pasajerosPlatino;
    private Lista<Pasajero> pasajerosFrecuente;
    private Lista<Pasajero> pasajerosEstandar;
    private Lista<Pasajero> pasajerosEsporadico;
    private Lista<Aeropuerto> aeropuertos;
    private Lista<Vuelo> vuelos;

    @Override
    public Retorno inicializarSistema() {
        this.pasajeros = new Lista<>();
        this.aeropuertos = new Lista<>();
        this.vuelos = new Lista<>();
        this.pasajerosPlatino = new Lista<>();
        this.pasajerosFrecuente = new Lista<>();
        this.pasajerosEstandar = new Lista<>();
        this.pasajerosEsporadico = new Lista<>();
        return Retorno.ok();
    }

    @Override
    public Retorno registrarPasajero(String cedula, String nombre, int edad, Categoria categoria) {

        if (cedula == null || cedula.isBlank() || nombre == null || nombre.isBlank() || categoria == null) {
            return Retorno.error1();
        }
        if (!validarCedula(cedula)) {
            return Retorno.error2();
        }
        if (edad < 0) {
            return Retorno.error3();
        }

        Pasajero nuevo = new Pasajero(cedula, nombre, edad, categoria);

        if (pasajeros.pertenece(nuevo)) {
            return Retorno.error4();
        }

        pasajeros.agregarOrd(nuevo);

        switch (categoria) {
            case PLATINO:
                pasajerosPlatino.agregarOrd(nuevo);
                break;
            case FRECUENTE:
                pasajerosFrecuente.agregarOrd(nuevo);
                break;
            case ESTANDAR:
                pasajerosEstandar.agregarOrd(nuevo);
                break;
            case ESPORADICO:
                pasajerosEsporadico.agregarOrd(nuevo);
                break;
            default:
                break;
        }
        return Retorno.ok();
    }

    @Override
    public Retorno buscarPasajero(String cedula) {
        if (cedula == null || !validarCedula(cedula)) {
            return Retorno.error1();
        }

        Pasajero p = new Pasajero(cedula, "", 0, null);
        Pasajero pasajeroBuscado = pasajeros.buscar(p);

        if (pasajeroBuscado == null) {
            return Retorno.error2();
        }

        return Retorno.ok(pasajeroBuscado.toString());

    }

    @Override
    public Retorno listarPasajerosAscendente() {
        return Retorno.ok(pasajeros.listarAscendente());
    }

    @Override
    public Retorno listarPasajerosDescendente() {
        return Retorno.ok(pasajeros.listarDescendente());
    }

    @Override
    public Retorno listarPasajerosPorCategoría(Categoria unaCategoria) {
        if (unaCategoria == Categoria.PLATINO) {
            return Retorno.ok(pasajerosPlatino.listarAscendente());
        }

        if (unaCategoria == Categoria.FRECUENTE) {
            return Retorno.ok(pasajerosFrecuente.listarAscendente());
        }

        if (unaCategoria == Categoria.ESTANDAR) {
            return Retorno.ok(pasajerosEstandar.listarAscendente());
        }

        return Retorno.ok(pasajerosEsporadico.listarAscendente());
    }

    @Override
    public Retorno registrarAeropuerto(String codigo, String nombre) {
        if (codigo == null || codigo.isBlank() || nombre == null || nombre.isBlank()) {
            return Retorno.error1();
        }
        Aeropuerto dePrueba = new Aeropuerto(codigo, "");
        if (this.aeropuertos.pertenece(dePrueba)) {
            return Retorno.error2();
        }
        Aeropuerto a = new Aeropuerto(codigo, nombre);
        aeropuertos.agregarInicio(a);
        return Retorno.ok();
    }

    @Override
    public Retorno obtenerAeropuerto(String codigo) {
        if (codigo == null || codigo.isBlank()) {
            return Retorno.error1();
        }

        Aeropuerto a = new Aeropuerto(codigo, "");
        Aeropuerto aeropuertoBuscado = aeropuertos.buscar(a);

        if (aeropuertoBuscado == null) {
            return Retorno.error2();
        }

        return Retorno.ok(aeropuertoBuscado.toString(), aeropuertoBuscado.getVuelosCola().cantElementos());
    }

    @Override
    public Retorno registrarVuelo(String codigoAeropuertoOrigen, String codigoAeropuertoDestino, String codigoDeVuelo, int capacidad, int costoEnDolares) {

        if (capacidad <= 0 || costoEnDolares <= 0) {
            return Retorno.error1();
        }
        if (codigoAeropuertoOrigen == null || codigoAeropuertoOrigen.isBlank() || codigoAeropuertoDestino == null || codigoAeropuertoDestino.isBlank() || codigoDeVuelo == null || codigoDeVuelo.isBlank()) {
            return Retorno.error2();
        }

        Aeropuerto origen = aeropuertos.buscar(new Aeropuerto(codigoAeropuertoOrigen, ""));

        if (origen == null) {
            return Retorno.error3();
        }

        Aeropuerto destino = aeropuertos.buscar(new Aeropuerto(codigoAeropuertoDestino, ""));

        if (destino == null) {
            return Retorno.error4();
        }

        Vuelo nuevo = new Vuelo(codigoAeropuertoOrigen, codigoAeropuertoDestino, codigoDeVuelo, capacidad, costoEnDolares);

        if (vuelos.pertenece(nuevo)) {
            return Retorno.error5();
        }

        vuelos.agregarOrd(nuevo);
        return Retorno.ok();
    }

    @Override
    public Retorno obtenerInformacionDeVuelo(String codigoDeVuelo) {
        if (codigoDeVuelo == null || codigoDeVuelo.isBlank()) {
            return Retorno.error1();
        }

        Vuelo vueloBuscado = vuelos.buscar(new Vuelo("", "", codigoDeVuelo, 0, 0));

        if (vueloBuscado == null) {
            return Retorno.error2();
        }

        return Retorno.ok(vueloBuscado.toString());
    }

    @Override
    public Retorno abrirVuelo(String codigoDeVuelo) {
        if (codigoDeVuelo == null || codigoDeVuelo.isBlank()) {
            return Retorno.error1();
        }

        Vuelo aBuscar = new Vuelo("", "", codigoDeVuelo, 0, 0);
        Vuelo vuelo = vuelos.buscar(aBuscar);

        if (vuelo == null) {
            return Retorno.error2();
        }

        if (vuelo.getEstado() != Estado.PROGRAMADO) {
            return Retorno.error3();
        }

        vuelo.setEstado(Estado.ABIERTO);

        return Retorno.ok();
    }

    @Override
    public Retorno cerrarVuelo(String codigoDeVuelo) {
        if (codigoDeVuelo == null || codigoDeVuelo.isBlank()) {
            return Retorno.error1();
        }

        Vuelo vuelo = vuelos.buscar(new Vuelo("", "", codigoDeVuelo, 0, 0));

        if (vuelo == null) {
            return Retorno.error2();
        }

        if (vuelo.getEstado() != Estado.ABIERTO) {
            return Retorno.error3();
        }

        vuelo.setEstado(Estado.CERRADO);

        Aeropuerto aBuscar = new Aeropuerto(vuelo.getCodigoAeropuertoOrigen(), "");
        Aeropuerto origen = aeropuertos.buscar(aBuscar);
        origen.getVuelosCola().encolar(vuelo);

        String confirmados = vuelo.listarPasajerosConfirmados();
        int sinCheckIn = vuelo.cantidadReservasSinCheckIn();

        return Retorno.ok(confirmados, sinCheckIn);
    }

    @Override
    public Retorno realizarReserva(String codigoDeVuelo, String cedula) {
        if (codigoDeVuelo == null || codigoDeVuelo.isBlank() || cedula == null || cedula.isBlank()) {
            return Retorno.error1();
        }

        if (!validarCedula(cedula)) {
            return Retorno.error2();
        }

        Vuelo vuelo = vuelos.buscar(new Vuelo("", "", codigoDeVuelo, 0, 0));

        if (vuelo == null) {
            return Retorno.error3();
        }

        Pasajero pasajero = pasajeros.buscar(new Pasajero(cedula, "", 0, null));

        if (pasajero == null) {
            return Retorno.error4();
        }

        if (vuelo.getEstado() != Estado.PROGRAMADO && vuelo.getEstado() != Estado.ABIERTO) {
            return Retorno.error5();
        }

        Reserva reserva = new Reserva(pasajero);

        if (vuelo.getReservas().pertenece(reserva)) {
            return Retorno.error6();
        }

        int maxReservas = (int) Math.ceil(vuelo.getCapacidad() * 1.10);

        if (vuelo.getReservas().cantElementos() >= maxReservas) {
            return Retorno.error7();
        }

        vuelo.getReservas().agregarOrd(reserva);
        return Retorno.ok();
    }

    @Override
    public Retorno realizarCheckIn(String codigoDeVuelo, String cedula) {
        if (codigoDeVuelo == null || codigoDeVuelo.isBlank() || cedula == null || cedula.isBlank()) {
            return Retorno.error1();
        }

        if (!validarCedula(cedula)) {
            return Retorno.error2();
        }

        Vuelo vuelo = vuelos.buscar(new Vuelo("", "", codigoDeVuelo, 0, 0));

        if (vuelo == null) {
            return Retorno.error3();
        }

        Pasajero pasajero = pasajeros.buscar(new Pasajero(cedula, "", 0, null));

        if (pasajero == null) {
            return Retorno.error4();
        }

        if (vuelo.getEstado() != Estado.ABIERTO) {
            return Retorno.error5();
        }

        Reserva r = new Reserva(pasajero);
        Reserva reserva = vuelo.getReservas().buscar(r);

        if (reserva == null) {
            return Retorno.error6();
        }

        if (reserva.isCheckInRealizado()) {
            return Retorno.error7();
        }

        if (vuelo.getConfirmados() >= vuelo.getCapacidad()) {
            return Retorno.error8();
        }

        reserva.realizarCheckIn();
        vuelo.confirmarPasajero();

        return Retorno.ok();
    }

    @Override
    public Retorno embarqueYDespegueDeVuelo(String codigoAeropuerto) {
        if (codigoAeropuerto == null || codigoAeropuerto.isBlank()) {
            return Retorno.error1();
        }

        Aeropuerto aBuscar = new Aeropuerto(codigoAeropuerto, "");
        Aeropuerto aeropuerto = aeropuertos.buscar(aBuscar);

        if (aeropuerto == null) {
            return Retorno.error2();
        }

        if (aeropuerto.getVuelosCola().esVacia()) {
            return Retorno.error3();
        }

        Vuelo vuelo = aeropuerto.getVuelosCola().front();

        vuelo.setEstado(Estado.FINALIZADO);

        String codVuelo = vuelo.getCodigoDeVuelo();

        aeropuerto.getVuelosCola().desencolar();

        int cantVuelosCola = aeropuerto.getVuelosCola().cantElementos();

        return Retorno.ok(codVuelo, cantVuelosCola);
    }

    @Override
    public Retorno consultaDisponibilidad(int[][] matriz, int cantidad, Clase unaClase) {
        if (cantidad <= 0) {
            return Retorno.error1();
        }

        int inicio = 0;
        int fin = 0;

        if (null != unaClase) {
            switch (unaClase) {
                case PRIMERA:
                    inicio = 0;
                    fin = 2;
                    break;
                case EJECUTIVA:
                    inicio = 3;
                    fin = 7;
                    break;
                case TURISTA:
                    inicio = 8;
                    fin = 25;
                    break;
                default:
                    break;
            }
        }

        String disponibles = "";
        int cantidadOpciones = 0;

        for (int columna = inicio; columna <= fin; columna++) {

            for (int fila = 0; fila <= matriz.length - cantidad; fila++) {

                boolean libre = true;

                for (int i = 0; i < cantidad; i++) {
                    if (matriz[fila + i][columna] != 0) {
                        libre = false;
                        break;
                    }
                }

                if (libre) {
                    String opcion = armarOpcion(fila, columna, cantidad);

                    if (!disponibles.equals("")) {
                        disponibles += "|";
                    }

                    disponibles += opcion;
                    cantidadOpciones++;
                }
            }

        }

        return Retorno.ok(disponibles, cantidadOpciones);
    }

    //Métodos auxiliares
    private boolean validarCedula(String cedula) {
        String regex = "^([1-9]\\.\\d{3}\\.\\d{3}-\\d|[1-9]\\d{2}\\.\\d{3}-\\d)$";
        return cedula.matches(regex);
    }

    private String armarOpcion(int inicio, int columna, int cantidad) {
        String opcion = "";

        for (int i = 0; i < cantidad; i++) {
            char letra = (char) ('A' + inicio + i);
            int numeroColumna = columna + 1;

            if (!opcion.equals("")) {
                opcion += "-";
            }

            opcion += letra + "" + numeroColumna;
        }

        return opcion;
    }

}
