package info.alekna.atmservice.exception;

public class BankServiceException extends Exception {

    public static class ServiceUnreachable extends BankServiceException {
        public ServiceUnreachable(String message) {
            super(message);
        }

        public ServiceUnreachable(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static class InsuficientBanknotes extends BankServiceException {
        public InsuficientBanknotes(String message) {
            super(message);
        }

        public InsuficientBanknotes(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static class AccountNotFound extends BankServiceException {
        public AccountNotFound(String message) {
            super(message);
        }

        public AccountNotFound(String message, Throwable cause) {
            super(message, cause);
        }
    }

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
