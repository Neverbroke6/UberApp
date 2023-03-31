package uber.uber.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uber.uber.models.Passenger;

@Repository
public interface CarRepository extends JpaRepository<Passenger, Long> {

}
