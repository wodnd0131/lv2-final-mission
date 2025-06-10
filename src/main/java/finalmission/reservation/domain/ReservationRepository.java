package finalmission.reservation.domain;

import finalmission.reservation.ui.dto.ReservationResponse;
import java.util.List;

public interface ReservationRepository {

    List<ReservationResponse> getAll();
}
