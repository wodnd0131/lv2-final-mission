package finalmission.reservation.ui;

import finalmission.member.domain.Member;
import finalmission.member.domain.MemberRepository;
import finalmission.reservation.domain.Reservation;
import finalmission.reservation.domain.ReservationRepository;
import finalmission.reservation.ui.dto.ReservationRequest;
import finalmission.reservation.ui.dto.ReservationResponse;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
    private final MemberRepository memberRepository;

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
        Member coach = memberRepository.getById(request.coachId());
        Member crew = memberRepository.getById(request.crewId());
        Reservation reservation = Reservation.from(request.date(), request.time(), coach, crew);
        return reservationRepository.save(reservation);
    }
}
