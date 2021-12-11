package org.grigorovich.test_web_app.exception;

    public class ApplicationException extends RuntimeException {
        public ApplicationException(String message) {
            super(message);
        }

        public ApplicationException(String message, Throwable cause) {
            super(message, cause);
        }

        public ApplicationException(Throwable cause) {
            super(cause);
        }
    }

