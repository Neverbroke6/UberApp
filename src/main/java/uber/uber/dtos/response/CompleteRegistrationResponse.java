package uber.uber.dtos.response;

import lombok.Data;

@Data
public class CompleteRegistrationResponse {
    private Long userId;
    private String message;
    private int statusCode;
}
