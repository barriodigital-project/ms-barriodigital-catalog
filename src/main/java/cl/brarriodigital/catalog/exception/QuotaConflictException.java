package cl.brarriodigital.catalog.exception;

public class QuotaConflictException extends RuntimeException {

    public QuotaConflictException(String message) {
        super(message);
    }
}
