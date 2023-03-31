package uber.uber.services.login;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import uber.uber.dtos.request.LoginRequest;
import uber.uber.services.passenger.PassengerService;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class PassengerServiceImplTest {
    @Autowired
    private PassengerService passengerService;


    @Test
    void testThatPassengerCanLogin(){
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setPhoneNumber("08021355748");
        loginRequest.setPassword("AIGbu$@1");

        String loggedPassenger = passengerService.login(loginRequest);
        assertEquals("LOGIN SUCCESSFUL", loggedPassenger);
    }
}
