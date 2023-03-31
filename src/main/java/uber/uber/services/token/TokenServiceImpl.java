package uber.uber.services.token;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import uber.uber.models.Token;
import uber.uber.repository.TokenRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TokenServiceImpl implements TokenService{
    private TokenRepository tokenRepository;

    @Override
    public Optional<Token> getConfirmationToken(String token) {
        return tokenRepository.findByToken(token);
    }

    @Override
    public void setConfirmationToken(String token) {
        tokenRepository.confirmedAt(LocalDateTime.now(), token);
    }

    @Override
    public void saveToken(Token token) {
        tokenRepository.save(token);
    }

    @Override
    public void deleteExpiredToken() {
        tokenRepository.deleteTokenByExpiredAtBefore(LocalDateTime.now());
    }
}
