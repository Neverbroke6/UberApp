package uber.uber.services.registration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import uber.uber.dtos.request.RegistrationRequest;
import uber.uber.dtos.request.TokenConfirmationRequest;
import uber.uber.dtos.response.RegistrationResponse;
import uber.uber.services.passenger.PassengerService;
import uber.uber.services.token.TokenService;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RegistrationServiceImplTest {

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private PassengerService passengerService;
    @Autowired
    private TokenService tokenService;


    @Test
    void testThatPassengerCanRegister() {
        RegistrationRequest request = new RegistrationRequest();
        request.setEmail("isra7@gmail.com");
        request.setFirstName("godmn");
        request.setLastName("daniels");
        request.setPassword("AIGbadu1##");
        request.setPhoneNumber("08021355048");
        RegistrationResponse response = registrationService.registration(request);
        assertEquals("Registration successful", response.getMessage());
    }

    @Test
    void testThatUserCanConfirmWithToken(){
        RegistrationRequest request = new RegistrationRequest();
        request.setEmail("teyjsash@gmail.com");
        request.setFirstName("gbudu");
        request.setLastName("gbudu");
        request.setPassword("AIGbadu1##");
        request.setPhoneNumber("0802135048");
        RegistrationResponse response = registrationService.registration(request);
        assertEquals("Registration successful", response.getMessage());

        TokenConfirmationRequest requestToken = new TokenConfirmationRequest();
        requestToken.setEmail("teyjsash@gmail.com");
        requestToken.setToken("5264");
        String tokenResponse = registrationService.verifyToken(requestToken);
        assertEquals("Successfully Verified", tokenResponse);
    }




}