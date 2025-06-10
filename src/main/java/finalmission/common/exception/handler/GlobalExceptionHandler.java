package finalmission.common.exception.handler;

import finalmission.common.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ProblemDetail handleNotFoundException(final NotFoundException ex, final WebRequest request) {
        log.warn("BusinessException ({}): {} at {}", ex.getClass().getSimpleName(), ex.getMessage(), getPath(request));
        return createProblemDetail(
                HttpStatus.INTERNAL_SERVER_ERROR,
                ex.getClass().getSimpleName(),
                ex.getMessage());
    }

    private ProblemDetail createProblemDetail(final HttpStatus status,
                                              final String title,
                                              final String message) {
        final ProblemDetail problemDetail = ProblemDetail.forStatus(status);
        problemDetail.setTitle(title);
        problemDetail.setDetail(message);
        return problemDetail;
    }

    private String getPath(final WebRequest request) {
        return request.getDescription(false).replace("uri=", "");
    }
}
