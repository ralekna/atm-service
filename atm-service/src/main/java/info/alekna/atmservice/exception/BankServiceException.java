package info.alekna.atmservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Server error")
public class BankServiceException extends Exception {

    @ResponseStatus(value = HttpStatus.GATEWAY_TIMEOUT, reason = "Gateway error")
    public static class ServiceUnreachable extends BankServiceException {
        public ServiceUnreachable(String message) {
            super(message);
        }

        public ServiceUnreachable(String message, Throwable cause) {
            super(message, cause);
        }
    }

    @ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE, reason = "Can't issue cash")
    public static class InsufficientCash extends BankServiceException {
        public InsufficientCash(String message) {
            super(message);
        }

        public InsufficientCash(String message, Throwable cause) {
            super(message, cause);
        }
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Unrecognized bank card")
    public static class AccountNotFound extends BankServiceException {
        public AccountNotFound(String message) {
            super(message);
        }

        public AccountNotFound(String message, Throwable cause) {
            super(message, cause);
        }
    }

    @ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Balance too low")
    public static class BalanceTooLow extends BankServiceException {
        public BalanceTooLow(String message) {
            super(message);
        }

        public BalanceTooLow(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public BankServiceException(String message) {
        super(message);
    }

    public BankServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
