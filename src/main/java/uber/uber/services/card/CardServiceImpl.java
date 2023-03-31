package uber.uber.services.card;

import lombok.AllArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;
import uber.uber.dtos.request.AddCardRequest;
import uber.uber.exceptions.RegistrationException;
import uber.uber.models.Card;
import uber.uber.repository.CardRepository;

import java.io.IOException;
import java.util.Optional;

@AllArgsConstructor
@Service
public class CardServiceImpl implements CardService {

    private CardRepository cardRepository;

    private final String SECRET_KEY = System.getenv("PAYSTACK_SECRET_KEY");

    @Override
    public Card addPassengerCard(AddCardRequest addCardRequest) throws IOException {
        Optional<Card> foundCard = cardRepository.findByCardNumber(addCardRequest.getCardNumber());
        if (foundCard.isEmpty()) throw new RegistrationException("CARD IS NOT RECOGNIZED");

        validateAccount(addCardRequest);
        Card card = new Card(
                addCardRequest.getCardName(),
                addCardRequest.getCardNumber(),
                addCardRequest.getExpiryDate(),
                addCardRequest.getCvv()
        );

       return cardRepository.save(card);
    }

    public String validateAccount(AddCardRequest addCardRequest) throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(" https://api.paystack.co/decision/bin/539983" + addCardRequest.getCardNumber().substring(0, 6))
                .get()
                .addHeader("Authorization", "Bearer " + SECRET_KEY)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }



}
