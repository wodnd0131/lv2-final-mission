package finalmission.reservation.application;

import finalmission.member.domain.Member;
import finalmission.member.domain.MemberRepository;
import finalmission.reservation.domain.Reservation;
import finalmission.reservation.domain.ReservationRepository;
import finalmission.reservation.domain.vo.ReservationApproval;
import finalmission.reservation.ui.dto.ReservationRequest;
import finalmission.reservation.ui.dto.ReservationUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public void update(ReservationUpdateRequest request) {
        Reservation reservation = reservationRepository.getById(request.id());
        Member coach = memberRepository.getById(request.coachId());
        reservation.updateByCrew(request.date(), request.time(), coach);
    }

    @Transactional
    public void cancel(Long id) {
        Reservation reservation = reservationRepository.getById(id);
        reservation.cancel();
    }

    @Transactional
    public ReservationApproval approval(Long id) {
        Reservation reservation = reservationRepository.getById(id);
        reservation.approval();
        return ReservationApproval.of(reservation);
    }
}
