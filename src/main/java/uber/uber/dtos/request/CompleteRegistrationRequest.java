package uber.uber.dtos.request;
import lombok.Data;
import uber.uber.models.Address;
import uber.uber.models.Card;
import uber.uber.models.Gender;

@Data
public class CompleteRegistrationRequest {
    private Address address;
    private Gender gender;
    private Card card;
}
