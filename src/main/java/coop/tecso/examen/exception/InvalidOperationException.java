package coop.tecso.examen.exception;

public class InvalidOperationException extends RuntimeException {
    public InvalidOperationException() {
        super("Movimiento en descubierto no permitido.");
    }
}
