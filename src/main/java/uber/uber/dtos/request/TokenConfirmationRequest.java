package uber.uber.dtos.request;

import lombok.Data;


@Data
public class TokenConfirmationRequest {
    private String email;
    private String token;
}
