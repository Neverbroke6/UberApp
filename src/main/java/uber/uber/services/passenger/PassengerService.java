package uber.uber.services.passenger;

import uber.uber.dtos.request.ForgotPasswordRequest;
import uber.uber.dtos.request.LoginRequest;
import uber.uber.dtos.response.ForgotPasswordResponse;
import uber.uber.models.Passenger;

import java.util.Optional;

public interface PassengerService {
    Optional<Passenger> findByEmailIgnoreCase(String email);
    void savePassenger(Passenger passenger);

    void enablePassenger(String email);

    String login(LoginRequest loginRequest);

    Optional<Passenger> findByPhoneNumber(String phoneNumber);

    ForgotPasswordResponse forgotPassword(ForgotPasswordRequest forgotPasswordRequest);
}
