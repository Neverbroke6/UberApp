package uber.uber.dtos.request;

import lombok.Data;

@Data
public class AddCardRequest {
    private String cardName;
    private String cardNumber;
    private String cvv;
    private String expiryDate;

}
