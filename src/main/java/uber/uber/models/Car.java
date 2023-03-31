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
public class Car {
  @Id
  private Long id;
  private String carColor;
  private String carModel;
  private String noOfSeats;


}
