package uber.uber.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uber.uber.models.Passenger;

@Repository
public interface DriverRepository extends JpaRepository<Passenger, Long>{

}
