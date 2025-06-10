package finalmission.reservation.ui;

import finalmission.reservation.domain.Reservation;
import finalmission.reservation.domain.ReservationState;
import finalmission.reservation.intrastructure.repository.JpaReservationRepository;
import finalmission.reservation.ui.dto.ReservationRequest;
import finalmission.reservation.ui.dto.ReservationUpdateRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.time.LocalDate;
import java.time.LocalTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ReservationControllerTest {

    @Autowired
    private JpaReservationRepository reservationRepository;

    @Test
    @DisplayName("예약 생성")
    void test_add() {
        ReservationRequest given
                = new ReservationRequest(1L, LocalDate.now().plusDays(1), LocalTime.MAX, 1L, 1L);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(given)
                .when().post(ReservationController.BASE_PATH)
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());

    }

    @Test
    @DisplayName("예약 생성 실패 - 잘못된 Member Id")
    void test_add_NotFound_Id() {
        ReservationRequest given
                = new ReservationRequest(1L, LocalDate.now().plusDays(1), LocalTime.MAX, Long.MAX_VALUE, 1L);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(given)
                .when().post(ReservationController.BASE_PATH)
                .then().log().all()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());

    }

    @Test
    @DisplayName("예약 수정")
    void test_update() {
        long id = 1L;
        LocalTime givenTime = LocalTime.MIN;
        ReservationUpdateRequest given
                = new ReservationUpdateRequest(id, LocalDate.now().plusDays(1), givenTime, 1L);
        Reservation past = reservationRepository.findById(id).get();

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(given)
                .when().put(ReservationController.BASE_PATH)
                .then().log().all()
                .statusCode(HttpStatus.OK.value());

        Reservation now = reservationRepository.findById(id).get();

        Assertions.assertThat(now.getTime()).isEqualTo(givenTime);
        Assertions.assertThat(now.getTime()).isNotEqualTo(past.getTime());

    }

    @Test
    @DisplayName("예약 삭제")
    void test_cancel() {
        long id = 1L;
        Reservation past = reservationRepository.findById(id).get();
        Assertions.assertThat(past.getState()).isEqualTo(ReservationState.WAITING);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .when().delete(ReservationController.BASE_PATH + "/1")
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());

        Reservation now = reservationRepository.findById(id).get();
        Assertions.assertThat(now.getState()).isEqualTo(ReservationState.CANCEL);

    }
}
