package uber.uber.services.token;

import uber.uber.models.Token;

import java.util.Optional;

public interface TokenService {
    Optional<Token> getConfirmationToken(String token);
    void setConfirmationToken(String token);
    void saveToken(Token token);
    void deleteExpiredToken();
}
