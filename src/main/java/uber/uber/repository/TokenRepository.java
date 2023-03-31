package uber.uber.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uber.uber.models.Token;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token,Long> {
    Optional<Token> findByToken(String token);

    @Transactional
    @Modifying
    @Query("UPDATE Token token" +
            " SET token.confirmedAt = ?1" +
            " WHERE token.token = ?2")
    void confirmedAt(LocalDateTime now, String token);

    void deleteTokenByExpiredAtBefore(LocalDateTime now);

}
