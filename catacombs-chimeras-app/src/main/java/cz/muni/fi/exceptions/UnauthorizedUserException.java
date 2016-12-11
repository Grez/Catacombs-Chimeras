package cz.muni.fi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class UnauthorizedUserException extends RuntimeException {
    public UnauthorizedUserException(final String message) {
        super(message);
    }

    public UnauthorizedUserException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public UnauthorizedUserException(final Throwable cause) {
        super(cause);
    }
}
