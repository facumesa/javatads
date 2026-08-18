package dominio;

public class Reserva implements Comparable<Reserva> {

    private Pasajero pasajero;
    private boolean checkInRealizado;

    public Reserva(Pasajero pasajero) {
        this.pasajero = pasajero;
        this.checkInRealizado = false;
    }

    public Pasajero getPasajero() {
        return pasajero;
    }

    public boolean isCheckInRealizado() {
        return checkInRealizado;
    }

    public void realizarCheckIn() {
        this.checkInRealizado = true;
    }

    @Override
    public int compareTo(Reserva otra) {
        return this.pasajero.compareTo(otra.pasajero);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Reserva)) {
            return false;
        }

        Reserva otra = (Reserva) obj;

        return pasajero.equals(otra.pasajero);
    }
}
