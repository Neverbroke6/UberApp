package uber.uber.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZonedDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<?> registrationException(RegistrationException registrationException, HttpServletRequest httpServletRequest){
        ApiResponse apiResponse = ApiResponse.builder()
                .timeStamp(ZonedDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .isSuccessful(false)
                .path(httpServletRequest.getRequestURI())
                .data(registrationException.getMessage())
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.CONFLICT);
    }


    @ExceptionHandler
    public ResponseEntity<?> generalException(GeneralException generalException, HttpServletRequest httpServletRequest){
        ApiResponse apiResponse = ApiResponse.builder()
                .timeStamp(ZonedDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .isSuccessful(false)
                .path(httpServletRequest.getRequestURI())
                .data(generalException.getMessage())
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    public  ResponseEntity<ApiResponse>GenericHandler(Exception exception, HttpServletRequest httpServletRequest){
        ApiResponse apiResponse = ApiResponse.builder()
                .timeStamp(ZonedDateTime.now())
                .data(exception.getMessage())
                .path(httpServletRequest.getRequestURI())
                .status(HttpStatus.BAD_REQUEST.value())
                .isSuccessful(false)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }
}
