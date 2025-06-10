package finalmission.reservation.application;

import finalmission.member.domain.Member;
import finalmission.member.domain.MemberRepository;
import finalmission.reservation.domain.Reservation;
import finalmission.reservation.domain.ReservationRepository;
import finalmission.reservation.ui.dto.ReservationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationCommandService {

    private final ReservationRepository reservationRepository;
    private final MemberRepository memberRepository;

    public Reservation add(ReservationRequest request) {
        Member coach = memberRepository.getById(request.coachId());
        Member crew = memberRepository.getById(request.crewId());
        Reservation reservation = Reservation.from(request.date(), request.time(), coach, crew);
        return reservationRepository.save(reservation);
    }
}
