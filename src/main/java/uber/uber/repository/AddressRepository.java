package uber.uber.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uber.uber.models.Address;

@Repository
public interface AddressRepository extends JpaRepository<Long, Address> {

}
