package uber.uber.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Card {
  @Id
  private Long id;
  private String cardName;
  private String cardNumber;
  private String cvv;
  private String expiryDate;

  public Card(String cardName, String cardNumber, String cvv, String expiryDate) {
    this.cardName = cardName;
    this.cardNumber = cardNumber;
    this.cvv = cvv;
    this.expiryDate = expiryDate;
  }
}
