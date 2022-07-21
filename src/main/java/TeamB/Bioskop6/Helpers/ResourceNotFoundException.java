package TeamB.Bioskop6.Helpers;

import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

public class ResourceNotFoundException extends Throwable {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
