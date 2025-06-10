package finalmission.reservation.intrastructure.repository;

import finalmission.reservation.domain.Reservation;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findAllByCrew_Id(Long id);

    List<Reservation> findAllByCoach_Id(Long id);
}
