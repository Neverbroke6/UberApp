package uber.uber.services.passenger;

import lombok.AllArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uber.uber.dtos.request.ForgotPasswordRequest;
import uber.uber.dtos.request.LoginRequest;
import uber.uber.dtos.response.ForgotPasswordResponse;
import uber.uber.exceptions.RegistrationException;
import uber.uber.models.Passenger;
import uber.uber.models.Token;
import uber.uber.repository.PassengerRepository;
import uber.uber.services.email.EmailSender;
import uber.uber.services.token.TokenService;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PassengerServiceImpl implements PassengerService {


//    private RegistrationService registrationService;
    private PassengerRepository passengerRepository;

    private TokenService tokenService;

    private EmailSender emailSender;


    @Override
    public Optional<Passenger> findByEmailIgnoreCase(String email) {
      return passengerRepository.findByEmailIgnoreCase(email);
    }

    @Override
    public Optional<Passenger> findByPhoneNumber(String phoneNumber) {
        return passengerRepository.findByPhoneNumber(phoneNumber);
    }
    @Override
    public void savePassenger(Passenger passenger) {
         passengerRepository.save(passenger);
    }

    @Override
    public void enablePassenger(String email) {
        Passenger foundEmailOfPassenger = passengerRepository.findByEmailIgnoreCase(email)
                .orElseThrow(()-> new RegistrationException("EMAIL IS INVALID"));
        foundEmailOfPassenger.setVerified(true);
        passengerRepository.save(foundEmailOfPassenger);
    }

    @Override
    public String login(LoginRequest loginRequest) {
        Passenger foundPassenger = findByPhoneNumber(loginRequest.getPhoneNumber())
                .orElseThrow(()-> new RegistrationException("INVALID PHONE NUMBER"));
        if (!foundPassenger.isVerified()) throw new RegistrationException("PLEASE VERIFY OTP SENT TO YOUR MAIL");
        if(!BCrypt.checkpw(loginRequest.getPassword(), foundPassenger.getPassword())){
            throw new RegistrationException("PASSWORD DOES NOT MATCH");
        }
        return "LOGIN SUCCESSFUL";
    }

    @Override
    public ForgotPasswordResponse forgotPassword(ForgotPasswordRequest forgotPasswordRequest){
        Passenger foundPassenger = findByEmailIgnoreCase(forgotPasswordRequest.getEmail())
                .orElseThrow(()-> new RegistrationException("INVALID EMAIL ADDRESS"));
        if (!foundPassenger.isVerified())  throw new RegistrationException("USER DOES NOT EXIST");
        String token = generateToken(foundPassenger);
        emailSender.send(forgotPasswordRequest.getEmail(), buildForgotPasswordEmail(foundPassenger.getFirstName(), token));
        ForgotPasswordResponse response = new ForgotPasswordResponse();
        response.setUserId(foundPassenger.getId());
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("TOKEN SENT SUCCESSFULLY");
        return response ;
    }

    private String generateToken(Passenger passenger){
        SecureRandom random = new SecureRandom();

        String token = String.valueOf(1000 + random.nextInt(9999));
        Token confirmationToken = new Token(token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(10),
                passenger
        );
        tokenService.saveToken(confirmationToken);
        return confirmationToken.getToken();
    }


    public String buildForgotPasswordEmail(String firstName, String token){
        return "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "\t<title>Token Verification Email</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "\t<h2>Token Verification Email</h2>\n" +
                "<p>Hello, </p>\n"+firstName  +
                "\t<p>You recently requested to reset your password. To complete this process, please verify your email address by entering the following token:</p>\n" +
                "<strong><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"></strong>Token: "+token +
                "\t<p>If you did not request to reset your password, please ignore this email.</p>\n" +
                "\t<p>Thank you for using our service.</p>\n" +
                "\t<p>Best regards,</p>\n" +
                "\t<p>The Spring Boot Team</p>\n" +
                "</body>\n" +
                "</html>\n";
    }

}
