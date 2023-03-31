package uber.uber.services.registration;


import lombok.AllArgsConstructor;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uber.uber.dtos.request.CompleteRegistrationRequest;
import uber.uber.dtos.request.RegistrationRequest;
import uber.uber.dtos.request.TokenConfirmationRequest;
import uber.uber.dtos.response.CompleteRegistrationResponse;
import uber.uber.dtos.response.RegistrationResponse;
import uber.uber.dtos.response.TokenConfirmationResponse;
import uber.uber.exceptions.RegistrationException;
import uber.uber.models.Passenger;
import uber.uber.models.Token;
import uber.uber.services.email.EmailSender;
import uber.uber.services.passenger.PassengerService;
import uber.uber.services.token.TokenService;
import uber.uber.utils.ValidateDetails;

import java.security.SecureRandom;
import java.time.LocalDateTime;


@AllArgsConstructor
@Service
public class RegistrationServiceImpl implements RegistrationService{

    private EmailSender emailSender;
    private TokenService tokenService;
    private PassengerService passengerService;

    @Override
    public RegistrationResponse registration(RegistrationRequest registrationRequest){
        boolean regexEmail = ValidateDetails.isValidEmail(registrationRequest.getEmail());
        if (!regexEmail) throw new RegistrationException("EMAIL INVALID");
        boolean regexPassword = ValidateDetails.isValidPassword(registrationRequest.getPassword());
        if (!regexPassword) throw new RegistrationException("INVALID PASSWORD FORMAT," +
                " PASSWORD MUST BE 8 CHARACTERS AND CONTAIN CAPITAL LETTERS, SMALL LETTERS, SYMBOLS AND NUMBERS");
        boolean regexPhoneNumber = ValidateDetails.isValidPhoneNumber(registrationRequest.getPhoneNumber());
        if (!regexPhoneNumber) throw new RegistrationException("PHONE NUMBER MUST BE 11 NUMBERS AND MUST NOT CONTAIN LETTERS");

        boolean emailExist = passengerService.findByEmailIgnoreCase(registrationRequest.getEmail()).isPresent();
        boolean phoneNumberExist = passengerService.findByPhoneNumber(registrationRequest.getPhoneNumber()).isPresent();
        if(phoneNumberExist) throw new RegistrationException("PHONE NUMBER IS ALREADY REGISTERED");
        if (emailExist) throw new RegistrationException("EMAIL IS ALREADY REGISTERED");

        Passenger passenger = new Passenger(
                registrationRequest.getFirstName(),
                registrationRequest.getLastName(),
                registrationRequest.getEmail(),
                registrationRequest.getPhoneNumber(),
                hashPassword(registrationRequest.getPassword())
        );

//        Proceed to save passenger
        passengerService.savePassenger(passenger);
        String token = generateToken(passenger);
        emailSender.send(registrationRequest.getEmail(), buildEmail(registrationRequest.getFirstName(), token));

        RegistrationResponse response = new RegistrationResponse();
        response.setUserId(passenger.getId());
        response.setStatusCode(HttpStatus.CREATED.value());
        response.setMessage("Registration successful");

        return response;
    }

    private String buildEmail(String firstName, String token) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your email</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + firstName + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for registering. Please click on the below link to activate your account: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">" + token + "</p></blockquote>\n Link will expire in 10 minutes. <p>See you soon</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }

    private String hashPassword (String password){
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }


    @Override
    public String generateToken(Passenger passenger){
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

    @Override
    public TokenConfirmationResponse verifyToken(TokenConfirmationRequest tokenConfirmationRequest) {
        Token token  = tokenService.getConfirmationToken(tokenConfirmationRequest.getToken())
                .orElseThrow(()-> new RegistrationException("TOKEN IS INVALID"));
        if (token.getExpiredAt().isBefore(LocalDateTime.now()))
            throw new IllegalStateException("Token has expired");
        tokenService.setConfirmationToken(token.getToken());
        passengerService.enablePassenger(tokenConfirmationRequest.getEmail());
        TokenConfirmationResponse response = new TokenConfirmationResponse();
        response.setMessage("VERIFICATION SUCCESSFUL");

        return response;
    }

    @Override
    public CompleteRegistrationResponse updateProfile(CompleteRegistrationRequest completeRegistrationRequest) {
        Passenger passenger = new Passenger();
        passenger.setAddress(completeRegistrationRequest.getAddress());
        passenger.setCard(completeRegistrationRequest.getCard());
        passenger.setGender(completeRegistrationRequest.getGender());

        CompleteRegistrationResponse response = new CompleteRegistrationResponse();
        response.setMessage("PROFILE SUCCESSFULLY UPDATED");
        response.setStatusCode(HttpStatus.OK.value());
        response.setUserId(passenger.getId());
        return response;
    }


}
