package uber.uber.models;
import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Entity
@Data
@RequiredArgsConstructor
public class Passenger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Gender gender;
    private String phoneNumber;

    @OneToOne
    private Address address;
    private boolean isVerified = false;
    @OneToMany
    private List<Card> card;

    public Passenger(String firstName,String lastName, String email, String phoneNumber, String password ){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }


}
