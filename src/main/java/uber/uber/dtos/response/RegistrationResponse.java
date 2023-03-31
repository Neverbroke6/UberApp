package uber.uber.dtos.response;

import lombok.Data;

@Data
public class RegistrationResponse {
    private Long userId;
    private String message;
    private int statusCode;
}
