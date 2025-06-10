package finalmission.reservation.ui;

import finalmission.reservation.ui.dto.ReservationRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ReservationControllerTest {

    @Test
    @DisplayName("예약 생성")
    void test_1() {
        ReservationRequest given
                = new ReservationRequest(LocalDate.now().plusDays(1), LocalTime.MAX, 1L, 1L);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(given)
                .when().post(ReservationController.BASE_PATH)
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());

    }

    @Test
    @DisplayName("예약 생성 실패 - 잘못된 Member Id")
    void test_2() {
        ReservationRequest given
                = new ReservationRequest(LocalDate.now().plusDays(1), LocalTime.MAX, Long.MAX_VALUE, 1L);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(given)
                .when().post(ReservationController.BASE_PATH)
                .then().log().all()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());

    }
}
