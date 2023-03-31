package uber.uber.services.registration;

import uber.uber.dtos.request.CompleteRegistrationRequest;
import uber.uber.dtos.request.RegistrationRequest;
import uber.uber.dtos.request.TokenConfirmationRequest;
import uber.uber.dtos.response.CompleteRegistrationResponse;
import uber.uber.dtos.response.RegistrationResponse;
import uber.uber.dtos.response.TokenConfirmationResponse;
import uber.uber.models.Passenger;


public interface RegistrationService {
    RegistrationResponse registration(RegistrationRequest registrationRequest);
    String generateToken(Passenger passenger);

    TokenConfirmationResponse verifyToken(TokenConfirmationRequest tokenConfirmationRequest);

    CompleteRegistrationResponse updateProfile(CompleteRegistrationRequest completeRegistrationRequest);



}
