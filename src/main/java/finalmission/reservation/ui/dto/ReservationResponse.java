package finalmission.reservation.ui.dto;

import finalmission.reservation.domain.Reservation;
import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationResponse(Long id, LocalDate date, LocalTime time, String coach, String crew) {

    public static ReservationResponse of(Reservation reservation) {
        return new ReservationResponse(
                reservation.getId(),
                reservation.getDate(),
                reservation.getTime(),
                reservation.getCoach().getName(),
                reservation.getCrew().getName()
        );
    }
}
