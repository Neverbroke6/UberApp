package uber.uber.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uber.uber.dtos.request.ForgotPasswordRequest;
import uber.uber.dtos.request.LoginRequest;
import uber.uber.dtos.request.TokenConfirmationRequest;
import uber.uber.exceptions.ApiResponse;
import uber.uber.services.passenger.PassengerService;
import uber.uber.services.registration.RegistrationService;

import java.time.ZonedDateTime;

@RestController
@RequestMapping("api/v1/login")
public class LoginController {

    @Autowired
    PassengerService passengerService;

    @Autowired
    RegistrationService registrationService;

    @PostMapping("")
    public ResponseEntity<?> registration(@RequestBody LoginRequest loginRequest, HttpServletRequest httpServletRequest) {
        ApiResponse response = ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .data(passengerService.login(loginRequest))
                .timeStamp(ZonedDateTime.now())
                .path(httpServletRequest.getRequestURI())
                .isSuccessful(true)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("forgotPassword")
    public ResponseEntity<?> forgotPassword(@RequestBody  ForgotPasswordRequest forgotPasswordRequest, HttpServletRequest httpServletRequest){
        ApiResponse response = ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .data(passengerService.forgotPassword(forgotPasswordRequest))
                .timeStamp(ZonedDateTime.now())
                .path(httpServletRequest.getRequestURI())
                .isSuccessful(true)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("confirm_Forgot_Password")
    public ResponseEntity<?> confirmForgotPassword(@RequestBody TokenConfirmationRequest tokenConfirmationRequest, HttpServletRequest httpServletRequest){
        ApiResponse response = ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .data(registrationService.verifyToken(tokenConfirmationRequest))
                .timeStamp(ZonedDateTime.now())
                .path(httpServletRequest.getRequestURI())
                .isSuccessful(true)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
