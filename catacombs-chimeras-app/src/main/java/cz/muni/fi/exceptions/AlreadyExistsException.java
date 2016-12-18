package cz.muni.fi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class AlreadyExistsException extends RuntimeException {
    public AlreadyExistsException(final String message) {
        super(message);
    }

    public AlreadyExistsException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public AlreadyExistsException(final Throwable cause) {
        super(cause);
    }
}
