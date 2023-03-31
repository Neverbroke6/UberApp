package uber.uber.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uber.uber.dtos.request.AddCardRequest;
import uber.uber.models.Card;
import uber.uber.models.Passenger;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
   Optional<Card> findByCardNumber(String cardNumber);
}
