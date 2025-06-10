package finalmission.reservation.ui;

import finalmission.reservation.application.ReservationCommandService;
import finalmission.reservation.domain.Reservation;
import finalmission.reservation.domain.ReservationRepository;
import finalmission.reservation.ui.dto.ReservationRequest;
import finalmission.reservation.ui.dto.ReservationResponse;
import finalmission.reservation.ui.dto.ReservationUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(ReservationController.BASE_PATH)
public class ReservationController {

    public static final String BASE_PATH = "/reservation";

    private final ReservationRepository reservationRepository;

    private final ReservationCommandService reservationCommandService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "모든 예약 조회")
    public List<ReservationResponse> getAll() {
        return reservationRepository.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "예약 신청")
    public Reservation add(@RequestBody ReservationRequest request) {
        return reservationCommandService.add(request);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "예약 수정")
    public void update(@RequestBody ReservationUpdateRequest request) {
        reservationCommandService.update(request);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "예약 삭제")
    public void delete(@PathVariable Long id) {
        reservationCommandService.cancel(id);
    }

    @GetMapping("/crews/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "크루의 모든 예약 조회")
    public List<ReservationResponse> getAllByCrew(@PathVariable Long id) {
        return reservationRepository.getAllByCrew(id);
    }

    @GetMapping("/coach/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "코치의 모든 예약 조회")
    public List<ReservationResponse> getAllByCoach(@PathVariable Long id) {
        return reservationRepository.getAllByCoach(id);
    }
}
