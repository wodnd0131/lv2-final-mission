package finalmission.reservation.ui;

import finalmission.reservation.domain.ReservationRepository;
import finalmission.reservation.ui.dto.ReservationResponse;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class ReservationController {

    private final ReservationRepository reservationRepository;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "모든 예약 조회")
    public List<ReservationResponse> getAll() {
        return reservationRepository.getAll();
    }
}
