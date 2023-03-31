package uber.uber.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uber.uber.models.Passenger;

import java.util.Optional;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Long> {
    Optional<Passenger> findByEmailIgnoreCase(String email);
    Optional<Passenger> findByPhoneNumber(String phoneNumber);

}
