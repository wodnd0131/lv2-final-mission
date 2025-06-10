package finalmission.reservation.intrastructure.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import finalmission.reservation.domain.MailClient;
import finalmission.reservation.domain.vo.ReservationApproval;
import finalmission.reservation.intrastructure.client.exception.MailException;
import finalmission.reservation.intrastructure.client.exception.MailInternalServerException;
import finalmission.reservation.intrastructure.client.exception.MailNetworkException;
import java.io.IOException;
import java.io.InputStream;
import java.util.function.Supplier;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

@Component
public class SendGridMailClient implements MailClient {

    private final RestClient restClient;
    private final ObjectMapper mapper;
    private final String sendPath;
    private final String from;

    public SendGridMailClient(@Qualifier("sendgridMailClient") RestClient restClient,
                              @Value("${sendgrid.url.send}") String sendPath,
                              @Value("${sendgrid.render.from}") String from,
                              ObjectMapper mapper) {
        this.restClient = restClient;
        this.mapper = mapper;
        this.sendPath = sendPath;
        this.from = from;
    }

    @Override
    public void send(ReservationApproval reservationApproval) {
        //todo reservationApproval 파싱
        executeWithExceptionHandling(
                () -> restClient.post()
                        .uri(sendPath)
                        .body(reservationApproval)
                        .retrieve()
                        .onStatus(HttpStatusCode::isError, this::handleResponseError)
                        .toBodilessEntity()
        );
    }

    private <T> void executeWithExceptionHandling(Supplier<T> operation) {
        try {
            operation.get();
        } catch (ResourceAccessException e) {
            throw new MailNetworkException(e.getMessage());
        } catch (HttpMessageNotReadableException e) {
            throw new MailInternalServerException("메일 API 응답을 처리할 수 없습니다.");
        } catch (IllegalArgumentException e) {
            throw new MailInternalServerException("메일 API 요청이 올바르지 않습니다.");
        } catch (RestClientException e) {
            throw new MailInternalServerException(e.getMessage());
        }
    }

    private void handleResponseError(final HttpRequest request, final ClientHttpResponse response) throws IOException {
        InputStream inputStream = response.getBody();
        MailErrorResponse errorResponse = mapper.readValue(inputStream, MailErrorResponse.class);

        throw new MailException(response.getStatusCode().toString(), errorResponse.message);
    }

    private record MailErrorResponse(
            String message,
            String field
    ) {

    }
}
