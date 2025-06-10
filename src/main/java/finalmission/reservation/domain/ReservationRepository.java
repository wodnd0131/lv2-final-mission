package finalmission.reservation.domain;

import finalmission.reservation.ui.dto.ReservationResponse;
import java.util.List;

public interface ReservationRepository {

    Reservation getById(Long id);

    List<ReservationResponse> getAll();

    Reservation save(Reservation reservation);
}
