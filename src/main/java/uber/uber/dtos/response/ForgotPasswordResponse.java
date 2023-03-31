package uber.uber.dtos.response;

import lombok.Data;

@Data
public class ForgotPasswordResponse {
    private Long userId;
    private String message;
    private int statusCode;
}
