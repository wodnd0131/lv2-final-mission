package finalmission.reservation.intrastructure.client.exception;

public class MailException extends RuntimeException {

    public MailException(String state, String message) {
        super(state + " " + message);
    }
}
