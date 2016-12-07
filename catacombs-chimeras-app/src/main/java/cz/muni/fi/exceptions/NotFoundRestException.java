package cz.muni.fi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundRestException extends RuntimeException {

    public NotFoundRestException(final Throwable cause) {
        super(cause);
    }

    public NotFoundRestException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public NotFoundRestException(final String message) {
        super(message);
    }
}
