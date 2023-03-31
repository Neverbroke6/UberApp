package uber.uber.services.card;

import uber.uber.dtos.request.AddCardRequest;
import uber.uber.models.Card;

import java.io.IOException;

public interface CardService {
    Card addPassengerCard(AddCardRequest addCardRequest) throws IOException;
}
