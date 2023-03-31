package uber.uber.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Data
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    private LocalDateTime createdAt;
    private LocalDateTime expiredAt;
    private LocalDateTime confirmedAt;
    @ManyToOne
    @JoinColumn(name = "passenger_id",
            referencedColumnName = "id")
    private Passenger passenger;
    @ManyToOne
    @JoinColumn(name = "driver_id",
            referencedColumnName = "id")
    private Driver driver;

    public Token(String token, LocalDateTime createdAt, LocalDateTime expiredAt, Passenger passenger){
        this.token = token;
        this.createdAt = createdAt;
        this.expiredAt = expiredAt;
        this.passenger = passenger;
    }


}
