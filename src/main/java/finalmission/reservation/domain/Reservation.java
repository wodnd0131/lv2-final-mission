package finalmission.reservation.domain;

import finalmission.member.domain.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private LocalTime time;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ReservationState state;

    @ManyToOne
    @JoinColumn(name = "coach_id")
    private Member coach;

    @ManyToOne
    @JoinColumn(name = "crew_id")
    private Member crew;

    private Reservation(LocalDate date,
                        LocalTime time,
                        ReservationState state,
                        Member coach,
                        Member crew) {
        this.date = date;
        this.time = time;
        this.state = state;
        this.coach = coach;
        this.crew = crew;
    }

    public static Reservation from(LocalDate date,
                                   LocalTime time,
                                   Member coach,
                                   Member crew) {
        return new Reservation(date, time, ReservationState.WAITING, coach, crew);
    }

    public void cancel() {
        this.state = ReservationState.CANCEL;
    }

    public void updateByCrew(LocalDate date, LocalTime time, Member coach) {
        this.date = date;
        this.time = time;
        this.coach = coach;
    }
}
