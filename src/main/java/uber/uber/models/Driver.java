package uber.uber.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;
    private int age;
    private String phoneNumber;
    @Enumerated(value = EnumType.STRING)
    private Gender gender;
    private Boolean isVerified;
    private String address;
    @OneToOne(optional = false)
    private Car car;
}

