package uber.uber.controller;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uber.uber.dtos.request.RegistrationRequest;
import uber.uber.dtos.request.TokenConfirmationRequest;
import uber.uber.exceptions.ApiResponse;
import uber.uber.services.registration.RegistrationService;

import java.time.ZonedDateTime;

@RestController
@RequestMapping("api/v1/register")
public class RegistrationController {

    @Autowired
    RegistrationService registrationService;

    @PostMapping("/signUp")
    public ResponseEntity<?> registration(@RequestBody RegistrationRequest registrationRequest, HttpServletRequest httpServletRequest) {
        ApiResponse response = ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .data(registrationService.registration(registrationRequest))
                .timeStamp(ZonedDateTime.now())
                .path(httpServletRequest.getRequestURI())
                .isSuccessful(true)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/tokenConfirmation")
    public ResponseEntity<?> tokenConfirmation(@RequestBody TokenConfirmationRequest tokenConfirmationRequest, HttpServletRequest httpServletRequest){
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
